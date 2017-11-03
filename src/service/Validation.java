package service;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

class Validation {

    static boolean isValidVolume(String v) {
        try {
            //noinspection ResultOfMethodCallIgnored
            parseInt(v);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isValidDate(String d) {
        try {
            LocalDate.parse(d);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isCorrectPath(String inputPath, String path) {
        String[] inputArg = inputPath.split("\\.");
        String[] pth = path.split("\\.");

        if (inputArg.length < pth.length) {
            return false;
        } else {
            for (int i = 0; i < pth.length; i++) {
                if (!inputArg[i].equals(pth[i])) {
                    return false;
                }
            }
            return true;
        }

    }

}
