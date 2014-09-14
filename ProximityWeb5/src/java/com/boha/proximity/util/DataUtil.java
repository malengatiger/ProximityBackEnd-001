/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.util;

import com.boha.proximity.data.Beacon;
import com.boha.proximity.data.BeaconDataItem;
import com.boha.proximity.data.Branch;
import com.boha.proximity.data.Company;
import com.boha.proximity.data.ErrorStore;
import com.boha.proximity.data.ErrorStoreAndroid;
import com.boha.proximity.data.Visitor;
import com.boha.proximity.data.VisitorTrack;
import com.boha.proximity.dto.BeaconDTO;
import com.boha.proximity.dto.BeaconDataItemDTO;
import com.boha.proximity.dto.BranchDTO;
import com.boha.proximity.dto.CompanyDTO;
import com.boha.proximity.dto.ErrorStoreAndroidDTO;
import com.boha.proximity.dto.ErrorStoreDTO;
import com.boha.proximity.dto.ResponseDTO;
import com.boha.proximity.dto.VisitorDTO;
import com.boha.proximity.dto.VisitorTrackDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.joda.time.DateTime;

/**
 *
 * @author aubreyM
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DataUtil {

    @PersistenceContext
    EntityManager em;

    public ResponseDTO getVisitors() throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query x = em.createNamedQuery("Visitor.findAll", Visitor.class);
            List<Visitor> list = x.getResultList();
            r.setVisitorList(new ArrayList<VisitorDTO>());
            for (Visitor visitor : list) {
                VisitorDTO dto = new VisitorDTO(visitor);
                dto.setVisitorTrackList(new ArrayList<VisitorTrackDTO>());
                r.getVisitorList().add(dto);
            }
            x = em.createNamedQuery("VisitorTrack.findAll", VisitorTrack.class);
            List<VisitorTrack> vtList = x.getResultList();
            for (VisitorDTO v : r.getVisitorList()) {
                for (VisitorTrack vt : vtList) {
                    if (vt.getVisitor().getVisitorID() == v.getVisitorID()) {
                        v.getVisitorTrackList().add(new VisitorTrackDTO(vt));
                    }
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException("Failed to get Visitor list");
        }
        return r;

    }

    public ResponseDTO getVisitorTracksSorted() throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query x = em.createNamedQuery("VisitorTrack.findVisitorBeaconTrack", VisitorTrack.class);
            List<VisitorTrack> vtList = x.getResultList();
            r.setVisitorTrackListSortedByBeacon(new ArrayList<VisitorTrackDTO>());
            HashMap<Integer, Beacon> map = new HashMap<>();
            for (VisitorTrack vt : vtList) {
                r.getVisitorTrackListSortedByBeacon().add(new VisitorTrackDTO(vt, true));
                if (!map.containsKey(vt.getBeacon().getBeaconID())) {
                    map.put(vt.getBeacon().getBeaconID(), vt.getBeacon());
                }
            }
            Set<Entry<Integer,Beacon>> dd = map.entrySet();
            r.setBeaconList(new ArrayList<BeaconDTO>());
            for (Entry<Integer, Beacon> entry : dd) {
                r.getBeaconList().add(new BeaconDTO(entry.getValue()));
            }
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException("Failed to get Visit list sorted");
        }
        return r;

    }
    public ResponseDTO getVisitorTracks() throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query x = em.createNamedQuery("VisitorTrack.findAll", VisitorTrack.class);
            List<VisitorTrack> vtList = x.getResultList();
            r.setVisitorTrackList(new ArrayList<VisitorTrackDTO>());
            for (VisitorTrack vt : vtList) {
                r.getVisitorTrackList().add(new VisitorTrackDTO(vt, true));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException("Failed to get Visit list");
        }
        return r;

    }

    public ResponseDTO getCompanyList() throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query x = em.createNamedQuery("Company.findAll", Company.class);
            List<Company> companylist = x.getResultList();
            log.log(Level.WARNING, "##### companies found: {0}", companylist.size());
            r.setCompanyList(new ArrayList<CompanyDTO>());
            for (Company c : companylist) {
                r.getCompanyList().add(new CompanyDTO(c));
            }

            Query q = em.createNamedQuery("Beacon.findAll", Beacon.class);
            List<Beacon> list = q.getResultList();

            Query q2 = em.createNamedQuery("Branch.findAll", Branch.class);
            List<Branch> brList = q2.getResultList();
            r.setBranchList(new ArrayList<BranchDTO>());

            q2 = em.createNamedQuery("BeaconDataItem.findAll", BeaconDataItem.class);
            List<BeaconDataItem> dataList = q2.getResultList();

            for (CompanyDTO c : r.getCompanyList()) {
                c.setBranchList(new ArrayList<BranchDTO>());
                for (Branch branch : brList) {
                    if (branch.getCompany().getCompanyID() == c.getCompanyID()) {
                        BranchDTO dto = new BranchDTO(branch);
                        dto.setBeaconList(new ArrayList<BeaconDTO>());
                        for (Beacon beac : list) {
                            if (beac.getBranch().getBranchID() == dto.getBranchID()) {
                                BeaconDTO bcDTO = new BeaconDTO(beac);
                                bcDTO.setBeaconDataItemList(new ArrayList<BeaconDataItemDTO>());
                                for (BeaconDataItem item : dataList) {
                                    if (item.getBeacon().getBeaconID() == item.getBeacon().getBeaconID()) {
                                        bcDTO.getBeaconDataItemList().add(new BeaconDataItemDTO(item));
                                    }

                                }
                                dto.getBeaconList().add(bcDTO);
                            }
                        }
                        c.getBranchList().add(dto);
                    }
                }
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException("Failed to get Company list");
        }
        return r;
    }

    public ResponseDTO getCompanyBeacons(int companyID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Beacon.findByCompany", Beacon.class);
            q.setParameter("id", companyID);
            List<Beacon> list = q.getResultList();
            r.setBeaconList(new ArrayList<BeaconDTO>());
            for (Beacon beacon : list) {
                r.getBeaconList().add(new BeaconDTO(beacon));
            }

            Query q2 = em.createNamedQuery("Branch.findByCompany", Branch.class);
            q2.setParameter("id", companyID);
            List<Branch> brList = q2.getResultList();
            r.setBranchList(new ArrayList<BranchDTO>());

            q2 = em.createNamedQuery("BeaconDataItem.findByCompany", BeaconDataItem.class);
            q2.setParameter("id", companyID);
            List<BeaconDataItem> dataList = q2.getResultList();

            for (Branch branch : brList) {
                BranchDTO dto = new BranchDTO(branch);
                dto.setBeaconList(new ArrayList<BeaconDTO>());
                for (Beacon beac : list) {
                    if (beac.getBranch().getBranchID() == dto.getBranchID()) {
                        BeaconDTO bcDTO = new BeaconDTO(beac);
                        bcDTO.setBeaconDataItemList(new ArrayList<BeaconDataItemDTO>());
                        for (BeaconDataItem item : dataList) {
                            if (item.getBeacon().getBeaconID() == item.getBeacon().getBeaconID()) {
                                bcDTO.getBeaconDataItemList().add(new BeaconDataItemDTO(item));
                            }

                        }
                        dto.getBeaconList().add(bcDTO);
                    }
                }
                r.getBranchList().add(dto);

            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO deleteBranchBeacons(int branchID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Branch branch = em.find(Branch.class, branchID);
            Company co = branch.getCompany();
            FileUtility.deleteBranchImageDirectory(co.getCompanyID(), branch.getBranchID());
            Query q = em.createNamedQuery("Beacon.deleteByBranch", Beacon.class);
            q.setParameter("id", branchID);
            int x = q.executeUpdate();
            log.log(Level.INFO, "Beacons deleted: {0}", x);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO deleteBeacon(int beaconID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Beacon b = em.find(Beacon.class, beaconID);
            Branch branch = b.getBranch();
            Company co = branch.getCompany();
            FileUtility.deleteBeaconImageDirectory(co.getCompanyID(), branch.getBranchID(), beaconID);
            em.remove(b);
            r.setMessage("Beacon deleted");
            log.log(Level.INFO, "Beacons deleted");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO getBranchBeacons(int branchID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Beacon.findByBranch", Beacon.class);
            q.setParameter("id", branchID);
            List<Beacon> list = q.getResultList();
            r.setBeaconList(new ArrayList<BeaconDTO>());
            for (Beacon beacon : list) {
                r.getBeaconList().add(new BeaconDTO(beacon));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO getBeaconsByMacAddress(String mac)
            throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Beacon.findByMacAddress", Beacon.class);
            q.setParameter("macAddress", mac);
            q.setMaxResults(1);
            Beacon beac = (Beacon) q.getSingleResult();
            r = getBranchBeacons(beac.getBranch().getBranchID());

        } catch (javax.persistence.NoResultException e) {
            r.setBeaconList(new ArrayList<BeaconDTO>());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO addCompany(CompanyDTO c)
            throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Company company = new Company();
            company.setCompanyName(c.getCompanyName());
            company.setCellphone(c.getCellphone());
            company.setEmail(c.getEmail());
            em.persist(company);
            Query q = em.createNamedQuery("Company.findByCompanyName", Company.class);
            q.setParameter("companyName", c.getCompanyName());
            q.setMaxResults(1);
            Company x = (Company) q.getSingleResult();
            r.setCompany(new CompanyDTO(x));
        } catch (PersistenceException e) {
            r.setStatusCode(113);
            r.setMessage("Company is already registered");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    private Visitor getVisitorByEmail(String email) {
        try {
            Query q = em.createNamedQuery("Visitor.findByEmail", Visitor.class);
            q.setParameter("email", email);
            q.setMaxResults(1);
            Visitor v = (Visitor) q.getSingleResult();
            return v;

        } catch (NoResultException e) {
            return null;
        }
    }

    public ResponseDTO registerVisit(int visitorID, int beaconID)
            throws DataException {

        ResponseDTO r = new ResponseDTO();

        try {
            Visitor visitor = em.find(Visitor.class, visitorID);
            Beacon beacon = em.find(Beacon.class, beaconID);
            VisitorTrack vt = new VisitorTrack();
            vt.setBeacon(beacon);
            vt.setVisitor(visitor);
            vt.setDateTracked(new Date());
            em.persist(vt);
            r.setMessage("Visit registered\n" + beacon.getBeaconName() + "\n" + visitor.getFirstName()
            + " " + visitor.getLastName());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to add visit", e);
            throw new DataException("Failed to add visit");
        }
        return r;
    }

    public ResponseDTO registerVisitor(VisitorDTO c)
            throws DataException {

        ResponseDTO r = new ResponseDTO();
        Visitor visitor = getVisitorByEmail(c.getEmail());
        try {
            if (visitor == null) {
                visitor = new Visitor();
                visitor.setFirstName(c.getFirstName());
                visitor.setLastName(c.getLastName());
                visitor.setEmail(c.getEmail());
                visitor.setDateRegistered(new Date());
                em.persist(visitor);
                visitor = getVisitorByEmail(c.getEmail());
                r.setVisitor(new VisitorDTO(visitor));
                log.log(Level.OFF, "###### vistor registered {0} {1}", new Object[]{visitor.getFirstName(), visitor.getLastName()});
            } else {
                r.setStatusCode(113);
                r.setMessage("This email is already registered. Please try a different email address");
                return r;
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to register visitor", e);
            throw new DataException("Failed to register visitor");
        }
        return r;
    }

    public ResponseDTO registerBranch(BranchDTO c)
            throws DataException {

        ResponseDTO r = new ResponseDTO();
        try {
            Branch branch = new Branch();
            branch.setCompany(em.find(Company.class, c.getCompanyID()));
            branch.setCellphone(c.getCellphone());
            branch.setEmail(c.getEmail());
            branch.setBranchName(c.getBranchName());

            em.persist(branch);
            Query q = em.createNamedQuery("Branch.findByBranchName", Branch.class);
            q.setParameter("branchName", c.getBranchName());
            q.setParameter("id", c.getCompanyID());
            q.setMaxResults(1);
            Branch br = (Branch) q.getSingleResult();
            r.setBranch(new BranchDTO(br));
        } catch (PersistenceException e) {
            r.setStatusCode(113);
            r.setMessage("Branch is already registered");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO registerBeacon(BeaconDTO c)
            throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Beacon b = new Beacon();
            b.setBranch(em.find(Branch.class, c.getBranchID()));
            b.setProximityUUID(c.getProximityUUID());
            b.setMajor(c.getMajor());
            b.setMinor(c.getMinor());
            b.setBeaconName(c.getBeaconName());
            b.setMacAddress(c.getMacAddress());

            em.persist(b);
            Query q = em.createNamedQuery("Beacon.findByMacAddress", Beacon.class);
            q.setParameter("macAddress", c.getMacAddress());
            q.setMaxResults(1);
            Beacon bc = (Beacon) q.getSingleResult();
            r.setBeacon(new BeaconDTO(bc));
            r.getBeacon().setBeaconDataItemList(new ArrayList<BeaconDataItemDTO>());
            if (c.getBeaconDataItemList() != null) {
                for (BeaconDataItemDTO item : c.getBeaconDataItemList()) {
                    ResponseDTO res = addBeaconDataItem(item);
                    r.getBeacon().getBeaconDataItemList().add(res.getBeaconDataItem());
                }
            }
            q = em.createNamedQuery("Beacon.findByBranch", Beacon.class);
            q.setParameter("id", c.getBranchID());
            List<Beacon> list = q.getResultList();
            r.setBeaconList(new ArrayList<BeaconDTO>());
            for (Beacon beacon : list) {
                r.getBeaconList().add(new BeaconDTO(beacon));
            }
        } catch (PersistenceException e) {
            r.setStatusCode(113);
            r.setMessage("Beacon is already registered");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public ResponseDTO addBeaconDataItem(BeaconDataItemDTO c)
            throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            BeaconDataItem b = new BeaconDataItem();
            b.setBeacon(em.find(Beacon.class, c.getBeaconID()));
            b.setImageUrl(c.getImageUrl());
            b.setHtml(c.getHtml());
            b.setText(c.getText());

            em.persist(b);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return r;
    }

    public Company getCompanyByID(int id) {
        return em.find(Company.class, id);
    }

    public void addAndroidError(ErrorStoreAndroid err) throws DataException {
        try {
            em.persist(err);
            log.log(Level.INFO, "Android error added");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to add Android Error", e);
            throw new DataException("Failed to add Android Error\n"
                    + getErrorString(e));
        }
    }

    public String getErrorString(Exception e) {
        StringBuilder sb = new StringBuilder();
        if (e.getMessage() != null) {
            sb.append(e.getMessage()).append("\n\n");
        }
        if (e.toString() != null) {
            sb.append(e.toString()).append("\n\n");
        }
        StackTraceElement[] s = e.getStackTrace();
        if (s.length > 0) {
            StackTraceElement ss = s[0];
            String method = ss.getMethodName();
            String cls = ss.getClassName();
            int line = ss.getLineNumber();
            sb.append("Class: ").append(cls).append("\n");
            sb.append("Method: ").append(method).append("\n");
            sb.append("Line Number: ").append(line).append("\n");
        }

        return sb.toString();
    }
    static final Logger log = Logger.getLogger(DataUtil.class.getName());

    public ResponseDTO getServerErrors(
            long startDate, long endDate) throws DataException {
        ResponseDTO r = new ResponseDTO();
        if (startDate == 0) {
            DateTime ed = new DateTime();
            DateTime sd = ed.minusMonths(3);
            startDate = sd.getMillis();
            endDate = ed.getMillis();
        }
        try {
            Query q = em.createNamedQuery("ErrorStore.findByPeriod", ErrorStore.class);
            q.setParameter("startDate", new Date(startDate));
            q.setParameter("endDate", new Date(endDate));
            List<ErrorStore> list = q.getResultList();
            List<ErrorStoreDTO> dList = new ArrayList();
            for (ErrorStore e : list) {
                dList.add(new ErrorStoreDTO(e));
            }
            r.setErrorStoreList(dList);
            log.log(Level.OFF, "Errors found {0}", r.getErrorStoreList().size());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to getServerErrors");
            throw new DataException("Failed to getServerErrors\n"
                    + getErrorString(e));
        }
        return r;
    }

    public ResponseDTO getServerEvents(
            long startDate, long endDate) throws DataException {
        ResponseDTO r = new ResponseDTO();
        if (startDate == 0) {
            DateTime ed = new DateTime();
            DateTime sd = ed.minusMonths(3);
            startDate = sd.getMillis();
            endDate = ed.getMillis();
        }
        try {
            Query q = em.createNamedQuery("ErrorStoreAndroid.findByPeriod", ErrorStoreAndroid.class);
            q.setParameter("from", new Date(startDate));
            q.setParameter("to", new Date(endDate));
            List<ErrorStoreAndroid> list = q.getResultList();
            List<ErrorStoreAndroidDTO> dList = new ArrayList();
            for (ErrorStoreAndroid e : list) {
                dList.add(new ErrorStoreAndroidDTO(e));
            }
            r.setErrorStoreAndroidList(dList);
            r.setErrorStoreList(getServerErrors(startDate, endDate).getErrorStoreList());

            String logx = LogfileUtil.getFileString();
            r.setLog(logx);
            log.log(Level.OFF, "Android Errors found {0}", r.getErrorStoreAndroidList().size());
        } catch (DataException | IOException e) {
            log.log(Level.SEVERE, "Failed to findClubsWithinRadius");
            throw new DataException("Failed to findClubsWithinRadius\n"
                    + getErrorString(e));
        }
        return r;
    }
}
