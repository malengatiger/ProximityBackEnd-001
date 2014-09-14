/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.util;

/**
 *
 * @author aubreymalabie
 */
import com.boha.proximity.dto.PhotoUploadDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class FileUtility {

    private static final Logger logger = Logger.getLogger(FileUtility.class.getName());

    public static void deleteBranchImageDirectory(
            int companyID, int branchID) throws IOException {
        File rootDir = ProximityProperties.getImageDir();
        File companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + companyID);
        File branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        if (branchDir.exists()) {
            FileUtils.deleteDirectory(branchDir);
        }
    }

    public static void deleteBeaconImageDirectory(
            int companyID, int branchID, int beaconID) {
        File rootDir = ProximityProperties.getImageDir();
        File companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + companyID);
        File branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        File beaconDir = new File(branchDir, PhotoUploadDTO.BEACON_PREFIX + beaconID);

        if (beaconDir.exists()) {
            deleteBeaconImageFiles(companyID, branchID, beaconID);
            beaconDir.delete();
            logger.log(Level.INFO, "Beacon directory deleted");

        }
    }

    public static void deleteBeaconImageFiles(
            int companyID, int branchID, int beaconID) {
        File rootDir = ProximityProperties.getImageDir();
        File companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + companyID);
        File branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        File beaconDir = new File(branchDir, PhotoUploadDTO.BEACON_PREFIX + beaconID);
        if (beaconDir.exists()) {
            File[] files = beaconDir.listFiles();
            for (File file : files) {
                logger.log(Level.INFO, "File to be deleted: {0}", file.getAbsolutePath());
                file.delete();
            }
        }
    }

    public static void deleteBeaconImageFile(
            int companyID, int branchID, int beaconID, String fileName) {
        File rootDir = ProximityProperties.getImageDir();
        File companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + companyID);
        File branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        File beaconDir = new File(branchDir, PhotoUploadDTO.BEACON_PREFIX + beaconID);
        if (beaconDir.exists()) {
            File f = new File(beaconDir, fileName);
            if (f.exists()) {
                logger.log(Level.INFO, "File to be deleted: {0}", f.getAbsolutePath());
                f.delete();
            }
        }
    }

    public static List<String> getBeaconImageFiles(
            int companyID, int branchID, int beaconID) throws Exception {
        List<String> list = new ArrayList<>();
        File rootDir = ProximityProperties.getImageDir();
        File companyDir = new File(rootDir, PhotoUploadDTO.COMPANY_PREFIX + companyID);
        File branchDir = new File(companyDir, PhotoUploadDTO.BRANCH_PREFIX + branchID);
        File beaconDir = new File(branchDir, PhotoUploadDTO.BEACON_PREFIX + beaconID);
        if (!beaconDir.exists()) {
            return list;
        }

        File[] files = beaconDir.listFiles();
        

        Arrays.sort(files, new Comparator() {
            public int compare(Object o1, Object o2) {

                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return +1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return -1;
                } else {
                    return 0;
                }
            }

        });
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            if (file.getName().contains(".jpg") || file.getName().contains(".png")
                    || file.getName().contains(".jpeg")) {
                list.add(file.getName());
            }
        }
        logger.log(Level.OFF, "full size dir: {0}", beaconDir.getAbsolutePath());
        logger.log(Level.OFF, "######## Image files found: {0}", list.size());
        return list;
    }

}
