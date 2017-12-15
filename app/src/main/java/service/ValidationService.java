package service;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

@Log4j2
public class ValidationService {

    boolean isValidVolume(String v) {
        try {
            //noinspection ResultOfMethodCallIgnored
            parseInt(v);
            log.debug("Volume is valid");
            return true;
        } catch (Exception e) {
            log.error("Volume isn`t check", e);
            return false;
        }
    }

    boolean isValidDate(String d) {
        try {
            LocalDate.parse(d);
            log.debug("Date is valid");
            return true;
        } catch (Exception e) {
            log.error("Date isn`t check", e);
            return false;
        }
    }

    boolean isCorrectPath(String inputPath, String path) {
        String[] inputArg = inputPath.split("\\.");
        String[] pth = path.split("\\.");

        if (inputArg.length < pth.length) {
            log.error("Length resource from cmd < length of user`s resource");
            return false;
        } else {
            for (int i = 0; i < pth.length; i++) {
                if (!inputArg[i].equals(pth[i])) {
                    log.error("Resources did not match");
                    return false;
                }
            }
            log.debug("Path is valid");
            return true;
        }
    }
}
