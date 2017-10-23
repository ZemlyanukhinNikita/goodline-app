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
    static boolean isCorrectPath(String argPath, String path) {
        String[] Arg = argPath.split("\\.");
        String[] Pth = path.split("\\.");

        if (Arg.length < Pth.length) {
            return false;
        } else {
            for (int i = 0; i < Pth.length; i++) {

                if (!Arg[i].equals(Pth[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
