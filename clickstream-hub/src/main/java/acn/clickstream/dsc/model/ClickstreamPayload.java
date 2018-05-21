package acn.clickstream.dsc.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cs.payload")
public class ClickstreamPayload {

    @Id
    private String eventId;
    private Long date;
    private String userId;
    private String rawQuestion;
    private String feedback;
    private String faqId;
    private String userType;
    private String action;
    private String parameter;
    private String ip;
    private String browser;



    public ClickstreamPayload(){}

    public ClickstreamPayload(String eventId, Long date, String userId, String rawQuestion, String feedback, String faqId, String userType, String action, String parameter, String ip, String browser) {
        this.eventId = eventId;
        this.date = date;
        this.userId = userId;
        this.rawQuestion = rawQuestion;
        this.feedback = feedback;
        this.faqId = faqId;
        this.userType = userType;
        this.action = action;
        this.parameter = parameter;
        this.ip = ip;
        this.browser = browser;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRawQuestion() {
        return rawQuestion;
    }

    public void setRawQuestion(String rawQuestion) {
        this.rawQuestion = rawQuestion;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFaqId() {
        return faqId;
    }

    public void setFaqId(String faqId) {
        this.faqId = faqId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
