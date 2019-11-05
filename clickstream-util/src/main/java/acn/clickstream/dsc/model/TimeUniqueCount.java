package acn.clickstream.dsc.model;

import lombok.ToString;

import java.util.Map;
import java.util.Objects;

@ToString
public class TimeUniqueCount {
    private Long timestamp;
    private Long count;

    public TimeUniqueCount(Map.Entry<Long, Long> mapEntry) {
        this.timestamp = mapEntry.getKey();
        this.count = mapEntry.getValue();
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
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        TimeUniqueCount timeUserCountUnit = (TimeUniqueCount) obj;
        return timeUserCountUnit.getTimestamp().equals(this.timestamp);
    }
}
