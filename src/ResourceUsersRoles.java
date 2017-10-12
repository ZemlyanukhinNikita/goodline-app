/**
 * Created by Nikita Zemlyanukhin on 12.10.2017.
 */
public class ResourceUsersRoles {
    private Long id;
    private Long user_id;
    private Long resources_id;
    private Roles role;

    public ResourceUsersRoles(Long id, Long user_id, Long resources_id, Roles role) {
        this.id = id;
        this.user_id = user_id;
        this.resources_id = resources_id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getResources_id() {
        return resources_id;
    }

    public void setResources_id(Long resources_id) {
        this.resources_id = resources_id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

}
