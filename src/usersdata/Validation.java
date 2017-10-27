package usersdata;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class Validation {

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

    public static boolean isAuthenticated() {
        return (CmdParser.cmd.hasOption("l") && CmdParser.cmd.hasOption("p"));
    }

    public static boolean isAuthorized() {
        return (isAuthenticated() && CmdParser.cmd.hasOption("r") && CmdParser.cmd.hasOption("pt"));
    }

    public static boolean isHelp() {
        return (!isAuthenticated() || CmdParser.cmd.hasOption("h"));
    }

    public static boolean isAccounted() {
        return (isAuthorized() && CmdParser.cmd.hasOption("ds")
                && CmdParser.cmd.hasOption("de") && CmdParser.cmd.hasOption("v"));
    }
}
