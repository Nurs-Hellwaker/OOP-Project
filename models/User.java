package models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String password;
    private String name;
    private boolean isLoggedOn;

    
    // Constructors

    
    protected User() {
        this.id = UUID.randomUUID().toString();
        this.isLoggedOn = false;
    }

    
    protected User(String name, String password) {
        this();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be null or blank.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("User password must not be null or empty.");
        }
        this.name = name;
        this.password = password;
    }

    
    protected User(String id, String name, String password) {
        this(name, password);
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User ID must not be null or blank.");
        }
        this.id = id;
    }


    // Authentication Methods
    

   
    public boolean login(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            this.isLoggedOn = true;
            System.out.println("[AUTH] User '" + this.name + "' has logged in successfully.");
            return true;
        }
        System.out.println("[AUTH] Login failed for user '" + this.name + "'. Invalid password.");
        return false;
    }

    
    public void logout() {
        if (!this.isLoggedOn) {
            System.out.println("[AUTH] Warning: User '" + this.name + "' was not logged in.");
            return;
        }
        this.isLoggedOn = false;
        System.out.println("[AUTH] User '" + this.name + "' has been logged out.");
    }

    
    public abstract String getRole();

    // Getters & Setters
    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User ID must not be null or blank.");
        }
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be null or blank.");
        }
        this.name = name;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty.");
        }
        this.password = password;
    }

    
    public boolean isLoggedOn() {
        return isLoggedOn;
    }

    
    public void setLoggedOn(boolean loggedOn) {
        this.isLoggedOn = loggedOn;
    }

    
    // Object Overrides
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(this.id, other.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    
    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s', role='%s', loggedOn=%b}",
            id, name, getRole(), isLoggedOn);
    }
}
