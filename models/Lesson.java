package Models;

import Enums.DayOfWeek;
import Enums.LessonType;

import java.io.Serializable;
import java.util.Objects;


public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    private Course    course;
    private LessonType type;
    private DayOfWeek day;
    private String    startTime;
    private String    endTime;
    private String    room;
    private String    instructorName;

    public Lesson(Course course, LessonType type, DayOfWeek day,
                  String startTime, String endTime, String room) {
        this.course         = course;
        this.type           = type;
        this.day            = day;
        this.startTime      = startTime;
        this.endTime        = endTime;
        this.room           = room;
        this.instructorName = "";
    }


    public Course     getCourse()                       { return course; }
    public void       setCourse(Course course)          { this.course = course; }

    public LessonType getType()                         { return type; }
    public void       setType(LessonType type)          { this.type = type; }

    public DayOfWeek  getDay()                          { return day; }
    public void       setDay(DayOfWeek day)             { this.day = day; }

    public String     getStartTime()                    { return startTime; }
    public void       setStartTime(String startTime)    { this.startTime = startTime; }

    public String     getEndTime()                      { return endTime; }
    public void       setEndTime(String endTime)        { this.endTime = endTime; }

    public String     getRoom()                         { return room; }
    public void       setRoom(String room)              { this.room = room; }

    public String     getInstructorName()                           { return instructorName; }
    public void       setInstructorName(String instructorName)      { this.instructorName = instructorName; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson l = (Lesson) o;
        return Objects.equals(course, l.course)
                && type == l.type
                && day  == l.day
                && Objects.equals(startTime, l.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, type, day, startTime);
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-8s | %s %s-%s | Room: %s | %s",
                (course != null ? course.getCourseName() : "N/A"),
                type, day, startTime, endTime, room, instructorName);
    }
}
