package acn.clickstream.dsc.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cs.faqsummary")
public class FaqSummary {

    @EmbeddedId
    private FaqId faqId;
    private Long counter;
    private Long updatedAt;


    public FaqSummary() {
    }

    public FaqSummary(String faqId, String userType, Long counter, Long updatedAt) {
        this.faqId = new FaqId(faqId,userType);
        this.counter = counter;
        this.updatedAt = updatedAt;
    }

    public FaqId getFaqId() {
        return faqId;
    }

    public void setFaqId(FaqId faqId) {
        this.faqId = faqId;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FaqSummary{" +
                "faqId=" + faqId +
                ", counter=" + counter +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
