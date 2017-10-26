import java.time.LocalDate;

import static java.lang.Integer.parseInt;

class Validation {

    static boolean isValidVolume(String v) {
        try {
            parseInt(v);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static boolean isValidDate(String d) {
        try {
            LocalDate.parse(d);
        } catch (Exception e) {
            return false;
        }
        return true;
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
        }
        return true;
    }
}
