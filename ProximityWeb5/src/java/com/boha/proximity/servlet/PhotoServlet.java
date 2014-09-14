/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.servlet;

import com.boha.proximity.dto.PhotoUploadDTO;
import com.boha.proximity.dto.RequestDTO;
import com.boha.proximity.dto.ResponseDTO;
import com.boha.proximity.util.FileUtility;
import com.boha.proximity.util.ProximityProperties;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.joda.time.DateTime;

/**
 * This servlet accepts image files uploaded from CourseMaker devices and saves
 * them on disk according to the requestor's role.
 *
 * @author aubreyM
 */
@WebServlet(name = "PhotoServlet", urlPatterns = {"/photo"})
public class PhotoServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        long start = System.currentTimeMillis();
        logger.log(Level.INFO, "PhotoServlet for Proximity started");

        ResponseDTO respx = new ResponseDTO();
        String json;
        Gson gson = new Gson();
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                respx = downloadPhotos(request);
            } else {
                RequestDTO dto = getRequest(gson, request);
                switch (dto.getRequestType()) {
                    case RequestDTO.GET_BEACON_IMAGE_FILES:
                        respx.setImageFileNames(FileUtility.getBeaconImageFiles(
                                dto.getCompanyID(), dto.getBranchID(), dto.getBeaconID()));
                        break;

                }

            }

        } catch (FileUploadException ex) {
            Logger.getLogger(PhotoServlet.class.getName()).log(Level.SEVERE, "File upload fucked", ex);
            respx.setStatusCode(ResponseDTO.SERVER_ERROR);
            respx.setMessage("Error. Unable to download file(s) sent. Contact Support");

        } catch (Exception e) {
            Logger.getLogger(PhotoServlet.class.getName()).log(Level.SEVERE, "Servlet file upload fucked", e);
            respx.setStatusCode(ResponseDTO.SERVER_ERROR);
            respx.setMessage("Error. Generic server exception");

        } finally {
            json = gson.toJson(respx);
            out.println(json);
            out.close();
            long end = System.currentTimeMillis();
            logger.log(Level.INFO, "PhotoServlet done, elapsed: {0} seconds", getElapsed(start, end));
        }
    }

    private ResponseDTO downloadPhotos(HttpServletRequest request) throws FileUploadException {
        logger.log(Level.INFO, "######### starting PHOTO DOWNLOAD process\n\n");
        ResponseDTO resp = new ResponseDTO();
        InputStream stream = null;
        File rootDir;
        try {
            rootDir = ProximityProperties.getImageDir();
            logger.log(Level.INFO, "rootDir - {0}", rootDir.getAbsolutePath());
            if (!rootDir.exists()) {
                rootDir.mkdir();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Properties file problem", ex);
            resp.setMessage("Server file unavailable. Please try later");
            resp.setStatusCode(ResponseDTO.SERVER_ERROR);

            return resp;
        }

        PhotoUploadDTO dto = null;
        Gson gson = new Gson();
        File companyDir = null, branchDir = null, beaconDir = null;
                
        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                stream = item.openStream();
                if (item.isFormField()) {
                    if (name.equalsIgnoreCase("JSON")) {
                        String json = Streams.asString(stream);
                        if (json != null) {
                            logger.log(Level.INFO, "picture with associated json: {0}", json);
                            dto = gson.fromJson(json, PhotoUploadDTO.class);
                            if (dto != null) {
                                companyDir = createCompanyDirectory(rootDir, companyDir, dto.getCompanyID());
                                branchDir = createBranchDirectory(companyDir, branchDir, dto.getBranchID());                                
                                beaconDir = createBeaconDirectory(branchDir, beaconDir, dto.getBeaconID());                                                              
                            }
                        } else {
                            logger.log(Level.WARNING, "JSON input seems fucked up! is NULL..");
                        }
                    }
                } else {
                    logger.log(Level.OFF, "name of item to be processed into file: {0}", name);
                    File imageFile = null;
                    if (dto == null) {
                        continue;
                    }
                    DateTime dt = new DateTime();
                    String suffix = "" + dt.getMillis() + ".jpg";
                    imageFile = new File(beaconDir, "f" + suffix);
                    writeFile(stream, imageFile);
                    resp.setMessage(imageFile.getName());
                    //TODO werite to DB?

                }
            }

        } catch (FileUploadException | IOException | JsonSyntaxException ex) {
            logger.log(Level.SEVERE, "Servlet failed on IOException, images NOT uploaded", ex);
            throw new FileUploadException();
        }

        return resp;
    }


    private File createBeaconDirectory(File branchDir, File beaconDir, int beaconID) {
        beaconDir = new File(branchDir, PhotoUploadDTO.BEACON_PREFIX + beaconID);
        if (!beaconDir.exists()) {
            beaconDir.mkdir();
            logger.log(Level.INFO, "beacon  directory created - {0}",
                    beaconDir.getAbsolutePath());

        }
        return beaconDir;
    }

    private File createBranchDirectory(File companyDir, File branchDir, int branchID) {
        branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        if (!branchDir.exists()) {
            branchDir.mkdir();
            logger.log(Level.INFO, "branch  directory created - {0}",
                    branchDir.getAbsolutePath());

        }
        return branchDir;
    }

    private File createCompanyDirectory(File rootDir, File companyDir, int id) {
        companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + id);
        if (!companyDir.exists()) {
            companyDir.mkdir();
            logger.log(Level.INFO, "company directory created - {0}",
                    companyDir.getAbsolutePath());
        }
       return companyDir; 
    }

    private void writeFile(InputStream stream, File imageFile) throws FileNotFoundException, IOException {

        FileOutputStream fos = new FileOutputStream(imageFile);
        int read;
        byte[] bytes = new byte[2048];
        while ((read = stream.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        stream.close();
        fos.flush();
        fos.close();

        logger.log(Level.INFO, "\n### File downloaded: {0} size: {1}",
                new Object[]{imageFile.getAbsolutePath(), imageFile.length()});
    }

    public static double getElapsed(long start, long end) {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return m.doubleValue();
    }
    static final Logger logger = Logger.getLogger("PhotoServlet");
    
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

    private RequestDTO getRequest(Gson gson, HttpServletRequest req) {

        String json = req.getParameter("JSON");
        RequestDTO cr = gson.fromJson(json, RequestDTO.class);

        if (cr == null) {
            cr = new RequestDTO();
        }

        return cr;
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
