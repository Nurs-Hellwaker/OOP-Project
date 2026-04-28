package Models;

import Enums.Schools;
import Exceptions.AcademicDismissalException;
import Exceptions.CreditOverflowException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CREDITS        = 21;
    private static final int MAX_FAIL_ATTEMPTS  = 3;


    private String  studentId;
    private String  firstName;
    private String  lastName;
    private String  username;
    private String  password;
    private String  email;
    private Schools school;
    private int     year;           // 1–4

    /** Courses the student is currently registered for this semester. */
    private List<Course>             registeredCourses;

    /** All registration requests submitted by this student. */
    private List<RegistrationTicket> tickets;

    /** Academic transcript (marks per course). */
    private Transcript transcript;

    /** Supervisor name (required for year 4 students). */
    private String supervisorName;

    /** Teacher ratings given by student: courseName → rating (1–5). */
    private List<String> ratingHistory;


    public Student(String studentId, String firstName, String lastName,
                   String username, String password, Schools school, int year) {
        this.studentId         = studentId;
        this.firstName         = firstName;
        this.lastName          = lastName;
        this.username          = username;
        this.password          = password;
        this.school            = school;
        this.year              = year;
        this.registeredCourses = new ArrayList<>();
        this.tickets           = new ArrayList<>();
        this.transcript        = new Transcript();
        this.ratingHistory     = new ArrayList<>();
    }


    public String  getStudentId()                           { return studentId; }
    public String  getFirstName()                           { return firstName; }
    public void    setFirstName(String firstName)           { this.firstName = firstName; }
    public String  getLastName()                            { return lastName; }
    public void    setLastName(String lastName)             { this.lastName = lastName; }
    public String  getUsername()                            { return username; }
    public String  getPassword()                            { return password; }
    public void    setPassword(String password)             { this.password = password; }
    public String  getEmail()                               { return email; }
    public void    setEmail(String email)                   { this.email = email; }
    public Schools getSchool()                              { return school; }
    public void    setSchool(Schools school)                { this.school = school; }
    public int     getYear()                                { return year; }
    public void    setYear(int year)                        { this.year = year; }
    public List<Course>             getRegisteredCourses()  { return registeredCourses; }
    public List<RegistrationTicket> getTickets()            { return tickets; }
    public Transcript               getTranscript()         { return transcript; }
    public void                     setTranscript(Transcript t) { this.transcript = t; }
    public String  getSupervisorName()                              { return supervisorName; }
    public void    setSupervisorName(String supervisorName)         { this.supervisorName = supervisorName; }




    public int getCurrentCredits() {
        int total = 0;
        for (Course c : registeredCourses) {
            total += c.getCredits();
        }
        return total;
    }


    public RegistrationTicket requestRegistration(Course course)
            throws CreditOverflowException {

        int newTotal = getCurrentCredits() + course.getCredits();
        if (newTotal > MAX_CREDITS) {
            throw new CreditOverflowException(
                    "Cannot register for '" + course.getCourseName() + "': "
                    + "would exceed the 21-credit limit. "
                    + "Current=" + getCurrentCredits() + ", Requested=" + course.getCredits()
            );
        }

        RegistrationTicket ticket = new RegistrationTicket(this, course);
        tickets.add(ticket);
        return ticket;
    }


    public void enrollInCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
        }
    }


    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }


    public void receiveMark(Course course, Mark mark) throws AcademicDismissalException {
        transcript.addRecord(course, mark);

        int fails = transcript.getFailCount(course);
        if (fails >= MAX_FAIL_ATTEMPTS) {
            throw new AcademicDismissalException(
                    firstName + " " + lastName + " has failed '"
                    + course.getCourseName() + "' " + fails + " times. "
                    + "Academic dismissal triggered."
            );
        }
    }


    public double getGPA() {
        return transcript.calculateGPA();
    }


    public void viewTranscript() {
        transcript.printTranscript(firstName + " " + lastName);
    }


    public void viewCourses() {
        System.out.println("\n── Registered courses for " + firstName + " " + lastName + " ──");
        if (registeredCourses.isEmpty()) {
            System.out.println("  (no courses registered yet)");
            return;
        }
        for (Course c : registeredCourses) {
            System.out.println("  • " + c);
        }
        System.out.println("  Total credits: " + getCurrentCredits() + " / " + MAX_CREDITS);
    }


    public void rateTeacher(String courseName, String teacherName, int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        String entry = "Course: " + courseName + " | Teacher: " + teacherName + " | Rating: " + rating + "/5";
        ratingHistory.add(entry);
        System.out.println("Rating submitted: " + entry);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return Objects.equals(studentId, s.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s | Year %d | %s | GPA: %.2f",
                studentId, firstName, lastName, year, school, getGPA());
    }
}
