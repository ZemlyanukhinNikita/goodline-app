package domain;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Roles getRole() {
        return role;
    }

    public String getPath() {
        return path;
    }
}
