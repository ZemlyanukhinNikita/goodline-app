import org.apache.commons.cli.*;

public class UserData {
    private String login;
    private String password;
    private String role;
    private String path;
    private String ds;
    private String de;
    private String volume;

    public static Options options = new Options();
    private static CommandLine cmd;
    private static CommandLineParser parser= new DefaultParser();

    public UserData(){
        options.addOption("l",true,"User login");
        options.addOption("p",true,"USer password");
        options.addOption("r",true,"User role");
        options.addOption("pt",true,"User resource");
        options.addOption("ds",true,"Data start");
        options.addOption("de",true,"Data end");
        options.addOption("v",true,"User volume");
        options.addOption("h",false,"Help information");
    }


    public boolean isAuthentication(){
        return (cmd.hasOption("l") && cmd.hasOption("p"));
    }

    public boolean isAuthorization(){
        return (isAuthentication() && cmd.hasOption("r") && cmd.hasOption("pt"));
    }

    public boolean isAccounts(){
        return (isAuthorization() && cmd.hasOption("ds") && cmd.hasOption("de") && cmd.hasOption("v"));
    }

    public boolean isHelp() {
        return (cmd.hasOption("") || cmd.hasOption("h"));
    }

    public static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
    }

    public  void cliParser(String[] args) {
        try {
            cmd = parser.parse(options,args);
            if(isAuthentication()){
                setLogin(cmd.getOptionValue("l"));
                setPassword(cmd.getOptionValue("p"));
            }

            if (isAuthorization()){
                setRole(cmd.getOptionValue("r"));
                setPath(cmd.getOptionValue("pt"));
            }

            if (isAccounts()){
                setDs(cmd.getOptionValue("ds"));
                setDe(cmd.getOptionValue("de"));
                setVolume(cmd.getOptionValue("v"));
            }
            if (isHelp()){
                help();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


}
