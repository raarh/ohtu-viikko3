package ohtu.domain;

import java.io.Serializable;

public class User implements Serializable{

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasCredentials(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }
}
