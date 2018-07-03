package acn.clickstream.dsc.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class FaqId implements Serializable{

    @NotNull
    private String faqId;

    @NotNull
    private String userType;

    public FaqId(){}

    public FaqId(@NotNull String faqId, @NotNull String userType) {
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

    @Override
    public String toString() {
        return "FaqId{" +
                "faqId='" + faqId + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
