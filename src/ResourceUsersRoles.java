/**
 * class ResourceUsersRoles
 * Created by Nikita Zemlyanukhin on 12.10.2017.
 * Copyright (c). All rights reserved.
 */
public class ResourceUsersRoles {
    private Long id;
    private Long user_id;
    private Roles role;
    private String path;

    public ResourceUsersRoles(Long id, Long user_id, Roles role, String path) {
        this.id = id;
        this.user_id = user_id;
        this.role = role;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setPath(String path) {
        this.path = path;
    }

    Roles getRole() {
        return role;
    }

    String getPath() {
        return path;
    }

}
