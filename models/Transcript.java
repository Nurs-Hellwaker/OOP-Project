package Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Transcript implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<Course, Mark> records;

    private HashMap<Course, Integer> failCounts;

    public Transcript() {
        this.records    = new HashMap<>();
        this.failCounts = new HashMap<>();
    }



    public void addRecord(Course course, Mark mark) {
        records.put(course, mark);
        if (!mark.isPassed()) {
            failCounts.put(course, failCounts.getOrDefault(course, 0) + 1);
        }
    }


    public Mark getMark(Course course) {
        return records.get(course);
    }


    public int getFailCount(Course course) {
        return failCounts.getOrDefault(course, 0);
    }


    public double calculateGPA() {
        double totalQualityPoints = 0;
        int    totalCredits       = 0;

        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            int    credits     = entry.getKey().getCredits();
            double gradePoints = entry.getValue().getGradePoints();
            totalQualityPoints += credits * gradePoints;
            totalCredits       += credits;
        }

        return totalCredits == 0 ? 0.0 : totalQualityPoints / totalCredits;
    }


    public int getTotalCreditsEarned() {
        int total = 0;
        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            if (entry.getValue().isPassed()) {
                total += entry.getKey().getCredits();
            }
        }
        return total;
    }


    public void printTranscript(String studentName) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.printf ("║  TRANSCRIPT — %-47s║%n", studentName);
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf ("║  %-10s %-22s %-8s %-10s  ║%n",
                "Code", "Course", "Credits", "Grade");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");

        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            Course course = entry.getKey();
            Mark   mark   = entry.getValue();
            System.out.printf("║  %-10s %-22s %-8d %-10s  ║%n",
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getCredits(),
                    mark.getLetterGrade() + " (" + String.format("%.1f", mark.getTotalScore()) + ")");
        }

        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf ("║  GPA: %-3.2f         Credits Earned: %-3d                      ║%n",
                calculateGPA(), getTotalCreditsEarned());
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    public HashMap<Course, Mark> getRecords()  { return records; }
}
