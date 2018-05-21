package acn.clickstream.dsc.model;

public class FaqId{

    private String faqId;

    private String userType;

    public FaqId(){}

    public FaqId(String faqId, String userType) {
        this.faqId = faqId;
        this.userType = userType;
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
}
