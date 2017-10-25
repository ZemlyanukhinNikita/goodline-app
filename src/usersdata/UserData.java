package usersdata;

import org.apache.commons.cli.*;

public class UserData {
    public static Options options = new Options();
    private String login;
    private String password;
    private String role;
    private String path;
    private String dataStart;
    private String dataEnd;
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

    public boolean isAuthenticated() {
        return (cmd.hasOption("l") && cmd.hasOption("p"));
    }

    public boolean isAuthorized() {
        return (isAuthenticated() && cmd.hasOption("r") && cmd.hasOption("pt"));
    }

    public boolean isAccounted() {
        return (isAuthorized() && cmd.hasOption("ds")
                && cmd.hasOption("de") && cmd.hasOption("v"));
    }

    public boolean isHelp() {
        return (!isAuthenticated() || cmd.hasOption("h"));
    }

    public static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
    }

    public void cliParse(String[] args) throws ParseException {
        cmd = parser.parse(options, args);

        if (isAuthenticated()) {
            setLogin(cmd.getOptionValue("l"));
            setPassword(cmd.getOptionValue("p"));
        }

        if (isAuthorized()) {
            setRole(cmd.getOptionValue("r"));
            setPath(cmd.getOptionValue("pt"));
        }

        if (isAccounted()) {
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
        return dataStart;
    }

    private void setDs(String ds) {
        this.dataStart = ds;
    }

    public String getDe() {
        return dataEnd;
    }

    private void setDe(String de) {
        this.dataEnd = de;
    }

    public String getVolume() {
        return volume;
    }

    private void setVolume(String volume) {
        this.volume = volume;
    }


}
