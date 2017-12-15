package domain;

@lombok.Setter
@lombok.Getter
public class ResourceUsersRoles {
    private Long id;
    private Long userId;
    private Roles role;
    private String stringRole;
    private String path;

    public ResourceUsersRoles(Long id, Long userId, Roles role, String path) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.path = path;
    }

    public ResourceUsersRoles(long id, long userId, String stringRole, String path) {
        this.id = id;
        this.userId = userId;
        this.stringRole = stringRole;
        this.path = path;
    }

}
