package interfaces;

public interface CanViewSystem {
    void ViewMenu();
    void processInput(String choice);
}

// Example for student class :
// public void viewMenu() {
//         System.out.println("\n--- STUDENT MENU ---");
//         System.out.println("1. View Courses");
//         System.out.println("2. Register for Course");
//         System.out.println("3. View Marks");
//         System.out.println("4. View Transcript");
//         System.out.println("0. Logout");
//         System.out.print("Choose an option: ");
//     }

// Example for teacher class:
// public void viewMenu() {
//         System.out.println("\n--- TEACHER MENU ---");
//         System.out.println("1. My Courses");
//         System.out.println("2. Manage Course (Put Marks)");
//         System.out.println("3. View Students");
//         System.out.println("4. Send Complaint");
//         System.out.println("0. Logout");
//         System.out.print("Choose an option: ");
//     }
