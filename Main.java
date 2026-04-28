import models.*;
import services.SystemDatabase;
import ui.ConsoleEngine;

public class Main {
    public static void main(String[] args) {
        SystemDatabase db = SystemDatabase.getInstance();

        if (db.getUsers().isEmpty()) {
            System.out.println("[System] Initializing database with test users");
            
            // db.addUser(new Admin("admin", "admin123"));
            // db.addUser(new Student("student1", "qwerty"));
            // db.addUser(new Teacher("prof_smith", "pass1"));
            
            db.saveDatabase(); 
        }

        ConsoleEngine engine = new ConsoleEngine();
        
        try {
            engine.run();
        } catch (Exception e) {
            System.err.println("Critical system error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down. Data is saved");
            db.saveDatabase();
        }
    }
}