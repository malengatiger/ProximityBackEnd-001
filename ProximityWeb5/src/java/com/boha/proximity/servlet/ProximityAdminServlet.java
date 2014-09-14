/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.servlet;

import com.boha.proximity.dto.RequestDTO;
import com.boha.proximity.dto.ResponseDTO;
import com.boha.proximity.util.DataException;
import com.boha.proximity.util.DataUtil;
import com.boha.proximity.util.FileUtility;
import com.boha.proximity.util.PlatformUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * All the requests from the Trainee mobile app are handled by this servlet
 *
 * @author aubreyM
 */
@WebServlet(name = "ProximityAdminServlet", urlPatterns = {"/admin"})
public class ProximityAdminServlet extends HttpServlet {

    @EJB
    DataUtil dataUtil;
    @EJB
    PlatformUtil platformUtil;

    static final String SOURCE = "Proximity";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.log(Level.INFO, "ProximityAdminServlet started ...");
        long start = System.currentTimeMillis();
        ResponseDTO resp = new ResponseDTO();
        RequestDTO dto = new RequestDTO();
        try {
            dto = getRequest(request);
            if (dto == null || dto.getRequestType() == 0) {
                resp.setStatusCode(ResponseDTO.SERVER_ERROR);
                resp.setMessage("Request is unknown. Verboten!!!");
            } else {
                switch (dto.getRequestType()) {
                    case RequestDTO.GET_VISITOR_LIST:
                        resp = dataUtil.getVisitors();
                        resp.setVisitorTrackList(dataUtil.getVisitorTracks().getVisitorTrackList());
                        ResponseDTO x = dataUtil.getVisitorTracksSorted();
                        resp.setVisitorTrackListSortedByBeacon(
                                x.getVisitorTrackListSortedByBeacon());
                        resp.setBeaconList(x.getBeaconList());
                        
                        break;
                    case RequestDTO.GET_VISITOR_TRACKS:
                        resp = dataUtil.getVisitorTracks();
                        break;
                    case RequestDTO.REGISTER_VISITOR:
                        resp = dataUtil.registerVisitor(dto.getVisitor());
                        platformUtil.addErrorStore(0, "Visitor registered\n" + dto.getVisitor().getFirstName()
                                + dto.getVisitor().getLastName(), SOURCE);
                        break;
                    case RequestDTO.REGISTER_VISIT:
                        resp = dataUtil.registerVisit(dto.getVisitorID(), dto.getBeaconID());
                        platformUtil.addErrorStore(0, resp.getMessage(), SOURCE);
                        break;
                    case RequestDTO.REGISTER_COMPANY:
                        resp = dataUtil.addCompany(dto.getCompany());
                        break;
                    case RequestDTO.REGISTER_BRANCH:
                        resp = dataUtil.registerBranch(dto.getBranch());
                        break;
                    case RequestDTO.REGISTER_BEACON:
                        resp = dataUtil.registerBeacon(dto.getBeacon());
                        platformUtil.addErrorStore(0, "Beacon registered on web application\n"+dto.getBeacon().getBeaconName()
                                + " - " + resp.getBeacon().getBranchName(), SOURCE);
                        break;

                    case RequestDTO.REGISTER_DATA_ITEM:
                        resp = dataUtil.addBeaconDataItem(dto.getBeaconDataItem());
                        break;
                    case RequestDTO.UPDATE_BEACON:

                        break;
                    case RequestDTO.UPDATE_DATA_ITEM:
                        break;
                    case RequestDTO.GET_BRANCH_BEACONS:
                        resp = dataUtil.getBranchBeacons(
                                dto.getBranchID());
                        break;
                    case RequestDTO.GET_COMPANY_BEACONS:
                        resp = dataUtil.getCompanyBeacons(
                                dto.getCompanyID());
                        break;
                    case RequestDTO.GET_COMPANIES:
                        resp = dataUtil.getCompanyList();
                        break;
                    case RequestDTO.GET_BEACONS_BY_MAC_ADDRESS:
                        resp = dataUtil.getBeaconsByMacAddress(
                                dto.getMacAddress());
                        break;
                    case RequestDTO.GET_ERROR_REPORTS:
                        resp = dataUtil.getServerEvents(0, 0);
                        break;
                    case RequestDTO.DELETE_ALL_BEACON_IMAGES:
                        FileUtility.deleteBeaconImageFiles(dto.getCompanyID(), dto.getBranchID(), dto.getBeaconID());
                        resp.setMessage("All beacon image files deleted");
                        break;
                    case RequestDTO.DELETE_BEACON_IMAGE:
                        FileUtility.deleteBeaconImageFile(
                                dto.getCompanyID(), dto.getBranchID(),
                                dto.getBeaconID(), dto.getFileName());
                        resp.setMessage(dto.getFileName() + " deleted");
                        break;

                    case RequestDTO.DELETE_ALL_BEACONS:
                        resp = dataUtil.deleteBranchBeacons(dto.getBranchID());
                        platformUtil.addErrorStore(303, "Branch beacons deleted", SOURCE);
                        break;
                    case RequestDTO.DELETE_BEACON:
                        resp = dataUtil.deleteBeacon(dto.getBeaconID());
                        platformUtil.addErrorStore(303, "Beacon deleted", SOURCE);
                        break;
                    default:
                        break;
                }
            }
        } catch (DataException e) {
            log.log(Level.SEVERE, "Database Error", e);
            resp.setStatusCode(ResponseDTO.DATABASE_ERROR);
            resp.setMessage("Server Data Error, contact Blossum support");
        
        } finally {
            addCorsHeaders(response);
            Gson gson = new Gson();
            String json = gson.toJson(resp);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(json);

            long end = System.currentTimeMillis();
            log.log(Level.INFO, "ProximityAdminServlet done. Elapsed: {0} seconds, requestType: {1} - {2}",
                    new Object[]{getElapsed(start, end), dto.getRequestType(),
                        dto.getRequestType()});
        }
    }

    private RequestDTO getRequest(HttpServletRequest req) {
        String json = req.getParameter("JSON");
        Gson gson = new Gson();
        RequestDTO dto;
        try {
            dto = gson.fromJson(json, RequestDTO.class);
            if (dto == null) {
                dto = new RequestDTO();
            }
        } catch (JsonSyntaxException e) {
            dto = new RequestDTO();
            dto.setRequestType(0);
        }
        log.log(Level.OFF, json);
        return dto;
    }

    /**
     * Add Cross-site headers to enable accessing this server from pages not
     * served by this server
     *
     * See: http://www.html5rocks.com/en/tutorials/cors/ and
     * http://enable-cors.org/server.html
     */
    public static void addCorsHeaders(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*, ");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    public static double getElapsed(long start, long end) {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return m.doubleValue();
    }
    private static final Logger log = Logger.getLogger("ProximityAdminServlet");

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
