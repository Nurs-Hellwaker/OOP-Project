package Models;

import Enums.RegistrationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class RegistrationTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int counter = 1;

    private String             ticketId;
    private Student            student;
    private Course             course;
    private RegistrationStatus status;
    private LocalDateTime      createdAt;
    private String             managerComment;

    public RegistrationTicket(Student student, Course course) {
        this.ticketId       = "TICKET-" + (counter++);
        this.student        = student;
        this.course         = course;
        this.status         = RegistrationStatus.PENDING;
        this.createdAt      = LocalDateTime.now();
        this.managerComment = "";
    }


    public String             getTicketId()                         { return ticketId; }
    public Student            getStudent()                          { return student; }
    public Course             getCourse()                           { return course; }

    public RegistrationStatus getStatus()                           { return status; }
    public void               setStatus(RegistrationStatus status)  { this.status = status; }

    public LocalDateTime      getCreatedAt()                        { return createdAt; }

    public String             getManagerComment()                           { return managerComment; }
    public void               setManagerComment(String managerComment)      { this.managerComment = managerComment; }


    public void approve(String comment) {
        this.status         = RegistrationStatus.APPROVED;
        this.managerComment = comment;
    }

    public void reject(String comment) {
        this.status         = RegistrationStatus.REJECTED;
        this.managerComment = comment;
    }

    public boolean isPending()  { return status == RegistrationStatus.PENDING; }
    public boolean isApproved() { return status == RegistrationStatus.APPROVED; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationTicket)) return false;
        RegistrationTicket t = (RegistrationTicket) o;
        return Objects.equals(ticketId, t.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s | Student: %s | Course: %s | Status: %-8s | %s",
                ticketId,
                student.getFirstName() + " " + student.getLastName(),
                course.getCourseName(),
                status,
                createdAt.format(fmt));
    }
}
