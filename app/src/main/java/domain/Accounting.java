package domain;

import com.google.gson.annotations.Expose;

@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Accounting {
    @Expose
    private Long id;
    @Expose
    private String login;
    @Expose
    private String role;
    @Expose
    private String path;
    @Expose
    private String dateStart;
    @Expose
    private String dateEnd;
    @Expose
    private String volume;
    private ResourceUsersRoles resourceUsersRoles;

    public Accounting(String login, String role, String path,
                      String dateStart, String dateEnd, String volume) {
        this.login = login;
        this.role = role;
        this.path = path;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.volume = volume;
    }

    public Accounting(String dateStart, String dateEnd, String volume) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.volume = volume;
    }

    public Accounting() {

    }

    public Accounting(Long id, String login, String role,
                      String path, String dateStart, String dateEnd, String volume) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.path = path;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.volume = volume;
    }
}
