/**
 * class User
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 * Copyright (c). All rights reserved.
 */
public class User {
    private Long id;
    private String login;
    private String password;
    private String salt;


    public User(Long id, String login, String password, String salt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    String getSalt() {
        return salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    String getPassword() {
        return password;
    }


}
