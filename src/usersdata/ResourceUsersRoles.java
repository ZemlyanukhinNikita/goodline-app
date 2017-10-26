package usersdata;

public class ResourceUsersRoles {
    private Long id;
    private Long userId;
    private Roles role;
    private String path;

    public ResourceUsersRoles(Long id, Long userId, Roles role, String path) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRole() {
        return role.toString();
    }

    public String getPath() {
        return path;
    }

}
