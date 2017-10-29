package service;

public class UserData {
    private String login;
    private String password;
    private String role;
    private String path;
    private String dateStart;
    private String dateEnd;
    private String volume;

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

    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    void setRole(String role) {
        this.role = role;
    }

    public String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }

    public String getDateStart() {
        return dateStart;
    }

    void setDateStart(String ds) {
        this.dateStart = ds;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    void setDateEnd(String de) {
        this.dateEnd = de;
    }

    public String getVolume() {
        return volume;
    }

    void setVolume(String volume) {
        this.volume = volume;
    }

}
