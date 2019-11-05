package acn.clickstream.dsc.model;

import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ToString
public class TimeUserCount {
    private Long timestamp;
    private Collection<UserCount> userCount;

    public TimeUserCount(Map.Entry<Long, List<UserCount>> mapEntry) {
        this.timestamp = mapEntry.getKey();
        this.userCount = mapEntry.getValue();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Collection<UserCount> getUserCount() {
        return userCount;
    }

    public void setUserCount(Collection<UserCount> userCount) {
        this.userCount = userCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        TimeUserCount timeUserCountUnit = (TimeUserCount) obj;
        return timeUserCountUnit.getTimestamp().equals(this.timestamp);
    }
}



