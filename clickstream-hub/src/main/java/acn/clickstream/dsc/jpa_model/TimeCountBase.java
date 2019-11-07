package acn.clickstream.dsc.jpa_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class TimeCountBase {
    @Id
    private Long timestamp;
    private Long count;

    public TimeCountBase() {
    }

    public TimeCountBase(Long timestamp, Long count) {
        this.timestamp = timestamp;
        this.count = count;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TimeCountBase{" +
                "timestamp=" + timestamp +
                ", count=" + count +
                '}';
    }
}
