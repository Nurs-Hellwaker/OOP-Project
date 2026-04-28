package Models;

import Enums.LessonType;
import Enums.Schools;
import Enums.SemesterType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseName;
    private int credits;
    private Schools school;
    private SemesterType semester;
    private int maxStudents;
    private List<String> instructorNames; // Teacher references — kept as names to avoid circular deps
    private List<Lesson> lessons;

    public Course(String courseCode, String courseName, int credits, Schools school, SemesterType semester) {
        this.courseCode   = courseCode;
        this.courseName   = courseName;
        this.credits      = credits;
        this.school       = school;
        this.semester     = semester;
        this.maxStudents  = 30;
        this.instructorNames = new ArrayList<>();
        this.lessons      = new ArrayList<>();
    }


    public String getCourseCode()                   { return courseCode; }
    public void   setCourseCode(String courseCode)  { this.courseCode = courseCode; }

    public String getCourseName()                   { return courseName; }
    public void   setCourseName(String courseName)  { this.courseName = courseName; }

    public int    getCredits()                      { return credits; }
    public void   setCredits(int credits)           { this.credits = credits; }

    public Schools     getSchool()                  { return school; }
    public void        setSchool(Schools school)    { this.school = school; }

    public SemesterType getSemester()                       { return semester; }
    public void         setSemester(SemesterType semester)  { this.semester = semester; }

    public int  getMaxStudents()                    { return maxStudents; }
    public void setMaxStudents(int maxStudents)     { this.maxStudents = maxStudents; }

    public List<String> getInstructorNames()        { return instructorNames; }

    public List<Lesson> getLessons()                { return lessons; }



    public void addInstructor(String instructorName) {
        if (!instructorNames.contains(instructorName)) {
            instructorNames.add(instructorName);
        }
    }


    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }


    public List<Lesson> getLessonsByType(LessonType type) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getType() == type) result.add(l);
        }
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return Objects.equals(courseCode, c.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %d credits | %s | %s",
                courseCode, courseName, credits, school, semester);
    }
}
