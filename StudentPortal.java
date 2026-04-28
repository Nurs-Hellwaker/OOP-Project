import Enums.DayOfWeek;
import Enums.LessonType;
import Enums.Schools;
import Enums.SemesterType;
import Exceptions.AcademicDismissalException;
import Exceptions.CreditOverflowException;
import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based Student Portal.
 *
 * Provides the full student menu flow as required by the task:
 *   - View / register for courses (with credit-limit enforcement)
 *   - View marks & transcript
 *   - View schedule (lessons)
 *   - Rate teachers
 *   - View registration ticket status
 *   - Change password / logout
 */
public class StudentPortal {

    private static final String SEPARATOR =
            "════════════════════════════════════════════════════════════";

    private final Scanner scanner;
    private Student       currentStudent;

    /** Available courses for registration (injected / set by the system). */
    private List<Course> availableCourses;

    public StudentPortal() {
        this.scanner          = new Scanner(System.in);
        this.availableCourses = new ArrayList<>();
    }

    // ─── Entry point ─────────────────────────────────────────────────────────

    /**
     * Main loop — show menu, handle selection.
     * Call this after authenticating a student.
     */
    public void run(Student student) {
        this.currentStudent = student;
        System.out.println("\n" + SEPARATOR);
        System.out.println("  Welcome, " + student.getFirstName() + " " + student.getLastName() + "!");
        System.out.println("  Student Portal — " + student.getSchool() + " | Year " + student.getYear());
        System.out.println(SEPARATOR);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1  -> viewAvailableCourses();
                case 2  -> registerForCourse();
                case 3  -> viewRegistrationStatus();
                case 4  -> dropCourse();
                case 5  -> viewSchedule();
                case 6  -> viewMarks();
                case 7  -> viewTranscript();
                case 8  -> rateTeacher();
                case 9  -> changePassword();
                case 0  -> running = false;
                default -> System.out.println("  ⚠  Invalid option. Please try again.");
            }
        }

        System.out.println("\n  Goodbye, " + student.getFirstName() + "! Logged out.");
    }

    // ─── Menu ────────────────────────────────────────────────────────────────

    private void printMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("  STUDENT PORTAL — Main Menu");
        System.out.println(SEPARATOR);
        System.out.println("  [1] View available courses");
        System.out.println("  [2] Register for a course");
        System.out.println("  [3] View my registration tickets");
        System.out.println("  [4] Drop a course");
        System.out.println("  [5] View my schedule");
        System.out.println("  [6] View my marks");
        System.out.println("  [7] View transcript & GPA");
        System.out.println("  [8] Rate a teacher");
        System.out.println("  [9] Change password");
        System.out.println("  [0] Logout");
        System.out.println(SEPARATOR);
        System.out.print("  Enter option: ");
    }

    // ─── Feature: View available courses ─────────────────────────────────────

    private void viewAvailableCourses() {
        System.out.println("\n── Available Courses ──────────────────────────────────────");
        if (availableCourses.isEmpty()) {
            System.out.println("  (no courses available for registration at the moment)");
            return;
        }
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, availableCourses.get(i));
        }
        System.out.printf("%n  Your current credits: %d / 21%n",
                currentStudent.getCurrentCredits());
    }

    // ─── Feature: Register for course ────────────────────────────────────────

    private void registerForCourse() {
        viewAvailableCourses();
        if (availableCourses.isEmpty()) return;

        System.out.print("\n  Select course number (0 to cancel): ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= availableCourses.size()) {
            System.out.println("  Cancelled.");
            return;
        }

        Course selected = availableCourses.get(idx);
        try {
            RegistrationTicket ticket = currentStudent.requestRegistration(selected);
            System.out.println("\n  ✔  Registration request submitted!");
            System.out.println("  " + ticket);
            System.out.println("  Awaiting manager approval.");
        } catch (CreditOverflowException e) {
            System.out.println("\n  ✘  Credit limit exceeded!");
            System.out.println("  " + e.getMessage());
        }
    }

    // ─── Feature: View ticket status ─────────────────────────────────────────

    private void viewRegistrationStatus() {
        System.out.println("\n── My Registration Tickets ────────────────────────────────");
        List<RegistrationTicket> tickets = currentStudent.getTickets();
        if (tickets.isEmpty()) {
            System.out.println("  (no tickets submitted yet)");
            return;
        }
        for (RegistrationTicket t : tickets) {
            System.out.println("  " + t);
            if (!t.getManagerComment().isBlank()) {
                System.out.println("    Comment: " + t.getManagerComment());
            }
        }
    }

    // ─── Feature: Drop a course ───────────────────────────────────────────────

    private void dropCourse() {
        List<Course> courses = currentStudent.getRegisteredCourses();
        System.out.println("\n── Drop a Course ──────────────────────────────────────────");
        if (courses.isEmpty()) {
            System.out.println("  (you have no registered courses to drop)");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, courses.get(i));
        }
        System.out.print("\n  Select course to drop (0 to cancel): ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) {
            System.out.println("  Cancelled.");
            return;
        }
        Course toDrop = courses.get(idx);
        currentStudent.dropCourse(toDrop);
        System.out.println("  ✔  Dropped: " + toDrop.getCourseName());
    }

    // ─── Feature: View schedule ───────────────────────────────────────────────

    private void viewSchedule() {
        System.out.println("\n── My Weekly Schedule ─────────────────────────────────────");
        List<Course> courses = currentStudent.getRegisteredCourses();
        if (courses.isEmpty()) {
            System.out.println("  (no courses registered — schedule is empty)");
            return;
        }
        boolean found = false;
        for (Course c : courses) {
            for (Lesson l : c.getLessons()) {
                System.out.printf("  %-10s %-12s %s%n", l.getDay(), l.getType(), l);
                found = true;
            }
        }
        if (!found) System.out.println("  (no lessons scheduled yet)");
    }

    // ─── Feature: View marks ──────────────────────────────────────────────────

    private void viewMarks() {
        System.out.println("\n── My Marks ───────────────────────────────────────────────");
        var records = currentStudent.getTranscript().getRecords();
        if (records.isEmpty()) {
            System.out.println("  (no marks recorded yet)");
            return;
        }
        System.out.printf("  %-22s  %-8s  %s%n", "Course", "Credits", "Mark Detail");
        System.out.println("  " + "─".repeat(58));
        for (var entry : records.entrySet()) {
            System.out.printf("  %-22s  %-8d  %s%n",
                    entry.getKey().getCourseName(),
                    entry.getKey().getCredits(),
                    entry.getValue());
        }
        System.out.printf("%n  Current GPA: %.2f%n", currentStudent.getGPA());
    }

    // ─── Feature: View transcript ─────────────────────────────────────────────

    private void viewTranscript() {
        System.out.println();
        currentStudent.viewTranscript();
    }

    // ─── Feature: Rate teacher ────────────────────────────────────────────────

    private void rateTeacher() {
        List<Course> courses = currentStudent.getRegisteredCourses();
        System.out.println("\n── Rate a Teacher ─────────────────────────────────────────");
        if (courses.isEmpty()) {
            System.out.println("  (you must be registered for courses to rate teachers)");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("  [%d] %s — Instructors: %s%n",
                    i + 1,
                    courses.get(i).getCourseName(),
                    courses.get(i).getInstructorNames());
        }
        System.out.print("\n  Select course number (0 to cancel): ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) {
            System.out.println("  Cancelled.");
            return;
        }
        Course chosen = courses.get(idx);
        List<String> instructors = chosen.getInstructorNames();
        if (instructors.isEmpty()) {
            System.out.println("  No instructor assigned to this course yet.");
            return;
        }
        System.out.print("  Enter teacher name to rate: ");
        String teacherName = scanner.nextLine().trim();
        System.out.print("  Rating (1–5): ");
        int rating = readInt();
        currentStudent.rateTeacher(chosen.getCourseName(), teacherName, rating);
    }

    // ─── Feature: Change password ─────────────────────────────────────────────

    private void changePassword() {
        System.out.println("\n── Change Password ────────────────────────────────────────");
        System.out.print("  Current password: ");
        String current = scanner.nextLine().trim();

        if (!current.equals(currentStudent.getPassword())) {
            System.out.println("  ✘  Incorrect current password.");
            return;
        }
        System.out.print("  New password: ");
        String newPass = scanner.nextLine().trim();
        System.out.print("  Confirm new password: ");
        String confirm = scanner.nextLine().trim();

        if (!newPass.equals(confirm)) {
            System.out.println("  ✘  Passwords do not match.");
            return;
        }
        currentStudent.setPassword(newPass);
        System.out.println("  ✔  Password changed successfully.");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private int readInt() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Allows the system to inject the list of courses open for registration. */
    public void setAvailableCourses(List<Course> courses) {
        this.availableCourses = courses;
    }

    // ─── Demo main (for standalone testing) ──────────────────────────────────

    /**
     * Quick demo — run this class directly to test StudentPortal in isolation.
     */
    public static void main(String[] args) {
        // Build sample data
        Course oop  = new Course("CS2103", "OOP", 5, Schools.SITE, SemesterType.FALL);
        Course algo = new Course("CS2105", "Algorithms", 5, Schools.SITE, SemesterType.FALL);
        Course db   = new Course("CS3101", "Databases", 5, Schools.SITE, SemesterType.FALL);
        Course math = new Course("MATH201","Calculus II", 3, Schools.SITE, SemesterType.FALL);
        Course eng  = new Course("ENG101", "English", 3, Schools.ISE, SemesterType.FALL);

        oop.addInstructor("Dr. Smith");
        algo.addInstructor("Prof. Johnson");

        Lesson l1 = new Lesson(oop,  LessonType.LECTURE,  DayOfWeek.MONDAY,    "09:00", "10:30", "Room 301");
        Lesson l2 = new Lesson(oop,  LessonType.PRACTICE, DayOfWeek.WEDNESDAY, "11:00", "12:30", "Lab 102");
        Lesson l3 = new Lesson(algo, LessonType.LECTURE,  DayOfWeek.TUESDAY,   "13:00", "14:30", "Room 205");
        oop.addLesson(l1);
        oop.addLesson(l2);
        algo.addLesson(l3);

        Student student = new Student("S001", "Amir", "Bekzhan",
                "a_bekzhan", "pass123", Schools.SITE, 2);

        // Pre-load some marks
        Mark m1 = new Mark(28, 27, 38);
        Mark m2 = new Mark(25, 24, 35);
        try {
            student.receiveMark(oop,  m1);
            student.receiveMark(algo, m2);
        } catch (AcademicDismissalException e) {
            System.out.println("DISMISSAL: " + e.getMessage());
        }

        // Enrol in courses directly (simulating manager approval)
        student.enrollInCourse(oop);
        student.enrollInCourse(algo);

        List<Course> available = List.of(db, math, eng);

        StudentPortal portal = new StudentPortal();
        portal.setAvailableCourses(available);
        portal.run(student);
    }
}
