package domain;

import com.google.gson.annotations.Expose;

@lombok.Setter
@lombok.Getter
public class ResourceUsersRoles {
    @Expose
    private Long id;
    @Expose
    private Long userId;
    @Expose
    private Roles role;
    @Expose
    private String stringRole;
    @Expose
    private String path;
    private User user;

    public ResourceUsersRoles(long id, long userId, String stringRole, String path) {
        this.id = id;
        this.userId = userId;
        this.stringRole = stringRole;
        this.path = path;
    }
}
