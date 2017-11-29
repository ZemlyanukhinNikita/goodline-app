package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class ValidationService {
    private static final Logger logger = LogManager.getLogger(ValidationService.class.getName());

    boolean isValidVolume(String v) {
        try {
            //noinspection ResultOfMethodCallIgnored
            parseInt(v);
            logger.debug("Volume is valid");
            return true;
        } catch (Exception e) {
            logger.error("Volume isn`t check", e);
            return false;
        }
    }

    boolean isValidDate(String d) {
        try {
            LocalDate.parse(d);
            logger.debug("Date is valid");
            return true;
        } catch (Exception e) {
            logger.error("Date isn`t check", e);
            return false;
        }
    }

    boolean isCorrectPath(String inputPath, String path) {
        String[] inputArg = inputPath.split("\\.");
        String[] pth = path.split("\\.");

        if (inputArg.length < pth.length) {
            logger.error("Length resource from cmd < length of user`s resource");
            return false;
        } else {
            for (int i = 0; i < pth.length; i++) {
                if (!inputArg[i].equals(pth[i])) {
                    logger.error("Resources did not match");
                    return false;
                }
            }
            logger.debug("Path is valid");
            return true;
        }
    }
}
