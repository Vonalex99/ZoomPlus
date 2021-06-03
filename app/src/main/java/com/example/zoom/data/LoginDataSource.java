package com.example.zoom.data;

import com.example.zoom.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser realUser = new LoggedInUser(username, password);
            if(username.equals("user@gmail.com") && password.equals("password"))
                return new Result.Success<>(realUser);
            return new Result.Error(new IOException("Wrong credentials", new Exception()));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
