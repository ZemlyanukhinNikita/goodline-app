public class UserData {
    private String login;
    private String password;
    private String role;
    private String path;
    private String ds;
    private String de;
    private String volume;

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserData(String login, String password, String role, String path) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.path = path;
    }

    public UserData(String login, String password, String role, String path, String ds, String de, String volume) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.path = path;
        this.ds = ds;
        this.de = de;
        this.volume = volume;
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
