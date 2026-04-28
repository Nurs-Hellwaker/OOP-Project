package services;

import models.*;
import java.io.*;
import java.util.*;

public class SystemDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private static SystemDatabase instance;
    private static final String FILE_PATH = "database.ser";

    private List<User> users = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private List<Message> messagesList = new ArrayList<>();
    private List<ResearchPaper> rpaperList = new ArrayList<>();
    private List<ResearchProject> rprojectList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private List<Lesson> lessonList = new ArrayList<>();
    private List<Transcript> transcriptList = new ArrayList<>();
    private List<RegistrationTicket> regTickList = new ArrayList<>();
    private List<Mark> markList = new ArrayList<>();

    private SystemDatabase() {}

    public static SystemDatabase getInstance() {
        if (instance == null) {
            instance = loadDatabase();
        }
        return instance;
    }
    //User
    public void addUser(User user) { 
        users.add(user); 
    }
    public List<User> getUsers() { 
        return users; 
    }
    //News
    public void addNews(News news) { 
        newsList.add(news); 
    }
    public List<News> getNews() { 
        return newsList; 
    }
    //Message
    public void addMessage(Message message) { 
        messagesList.add(message); 
    }
    public List<Message> getMessages() { 
        return messagesList; 
    }
    //ResearchPaper
    public void addResearchPaper(ResearchPaper rpaper) {
        rpaperList.add(rpaper); 
    }
    public List<ResearchPaper> getResearchPapers() { 
        return rpaperList; 
    }
    //ResearchProject
    public void addResearchProject(ResearchProject rproject) { 
        rprojectList.add(rproject); 
    }
    public List<ResearchProject> getResearchProjects() { 
        return rprojectList; 
    }
    //Course
    public void addCourse(Course course) { 
        courseList.add(course); 
    }
    public List<Course> getCourses() { 
        return courseList; 
    }
    //Lesson
    public void addLesson(Lesson lesson) { 
        lessonList.add(lesson); 
    }
    public List<Lesson> getLessons() { 
        return lessonList; 
    }
    //Transcript
    public void addTranscript(Transcript transcript) { 
        transcriptList.add(transcript); 
    }
    public List<Transcript> getTranscripts() { 
        return transcriptList; 
    }
    //RegistrationTicket
    public void addRegTicker(RegistrationTicket regtick) { 
        regTickList.add(regtick); 
    }
    public List<RegistrationTicket> getRegTickets() { 
        return regTickList; 
    }
    //Mark
    public void addMark(Mark mark) { 
        markList.add(mark); 
    }
    public List<Mark> getMarks() { 
        return markList; 
    }
    public void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(this);
            System.out.println("[DB] Database saved.");
        } catch (IOException e) {
            System.err.println("[DB] Error while saving: " + e.getMessage());
        }
    }

    private static SystemDatabase loadDatabase() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                return (SystemDatabase) ois.readObject();
            } catch (Exception e) {
                System.err.println("[DB] Loading error. New database created");
            }
        }
        return new SystemDatabase();
    }
}