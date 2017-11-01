package service;

public class UserData {
    private String login;
    private String password;
    private String role;
    private String path;
    private String dateStart;
    private String dateEnd;
    private String volume;

    public boolean isAuthenticated() {
        return ((this.login != null) && (this.password != null));
    }

    public boolean isAuthorized() {
        return (isAuthenticated()
                && (this.role != null) && (this.path != null));
    }

    public boolean isAccounted() {
        return (isAuthorized() && (this.dateStart != null)
                && (this.dateEnd != null) && (this.volume != null));
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
