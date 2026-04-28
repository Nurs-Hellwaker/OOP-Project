package services;

import models.User;

public class AuthService {
    private User currentUser;
    private SystemDatabase db;

    public AuthService() {
        this.db = SystemDatabase.getInstance();
    }

    public boolean login(String name, String password) {
        for (User user : db.getUsers()) {
            if (user.getName().equals(name)) {
                if (user.login(password)) {
                    this.currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}