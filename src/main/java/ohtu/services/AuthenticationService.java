package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.hasCredentials(username, password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }
    private boolean invalidateUsername(String username){
        if(username.length() < 3){
            return true;
        }
        boolean usernameHasNumbers = Pattern.matches(".*\\d+.*",username);
        return usernameHasNumbers;
    }
    private boolean invalidatePassowrd(String password){

        if(password.length() < 8){
            return true;
        }
        boolean passwordHasDigit = Pattern.matches(".*\\d+.*",password);
        boolean passwordHasSpecialChar = Pattern.matches("[$-/:-?{-~!\"^_`\\[\\]]",password);

        return ! (passwordHasDigit || passwordHasSpecialChar);
    }
    private boolean invalid(String username, String password) {
        return invalidateUsername(username) || invalidatePassowrd(password);
    }
}
