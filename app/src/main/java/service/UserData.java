package service;

@lombok.Setter
@lombok.Getter
@lombok.ToString
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
}
