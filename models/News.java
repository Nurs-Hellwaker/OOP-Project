package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private Date publishedDate;
    private String topic;

    public News(String title, String content, String topic) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.publishedDate = new Date();
    }

    public String getTitle(){ 
        return title; 
    }
    public String getContent() { 
        return content; 
    }
    public Date getPublishedDate() { 
        return publishedDate; 
    }
    public String getTopic() { 
        return topic; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }
    public void setTopic(String topic) { 
        this.topic = topic; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return Objects.equals(title, news.title) && 
               Objects.equals(publishedDate, news.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publishedDate);
    }

    @Override
    public String toString() {
        return "-----" + getTitle() + "----- \n" + "-----" + getTopic() + "----- \n" + "[ " + getContent() +  " ]\n" + "Published: " + getPublishedDate();
    }
}