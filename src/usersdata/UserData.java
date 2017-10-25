package usersdata;

import org.apache.commons.cli.*;

public class UserData {
    public static Options options = new Options();
    private String login;
    private String password;
    private String role;
    private String path;
    private String ds;
    private String de;
    private String volume;
    private static CommandLine cmd;
    private static CommandLineParser parser = new DefaultParser();

    public UserData() {
        options.addOption("l", true, "User login");
        options.addOption("p", true, "USer password");
        options.addOption("r", true, "User role");
        options.addOption("pt", true, "User resource");
        options.addOption("ds", true, "Data start");
        options.addOption("de", true, "Data end");
        options.addOption("v", true, "User volume");
        options.addOption("h", false, "Help information");
    }

    public boolean isAuthentication() {
        return cmd.hasOption("l") && cmd.hasOption("p");
    }

    public boolean isAuthorization() {
        return isAuthentication() && cmd.hasOption("r") && cmd.hasOption("pt");
    }

    public boolean isAccounts() {
        return (isAuthorization() && cmd.hasOption("ds") && cmd.hasOption("de") && cmd.hasOption("v"));
    }

    public boolean isHelp() {
        return !isAuthentication() || cmd.hasOption("h");
    }

    public static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
    }

    public void cliParser(String[] args) throws ParseException {
        cmd = parser.parse(options, args);

        if (isAuthentication()) {
            setLogin(cmd.getOptionValue("l"));
            setPassword(cmd.getOptionValue("p"));
        }

        if (isAuthorization()) {
            setRole(cmd.getOptionValue("r"));
            setPath(cmd.getOptionValue("pt"));
        }

        if (isAccounts()) {
            setDs(cmd.getOptionValue("ds"));
            setDe(cmd.getOptionValue("de"));
            setVolume(cmd.getOptionValue("v"));
        }

    }

    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    private void setRole(String role) {
        this.role = role;
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getDs() {
        return ds;
    }

    private void setDs(String ds) {
        this.ds = ds;
    }

    public String getDe() {
        return de;
    }

    private void setDe(String de) {
        this.de = de;
    }

    public String getVolume() {
        return volume;
    }

    private void setVolume(String volume) {
        this.volume = volume;
    }


}
