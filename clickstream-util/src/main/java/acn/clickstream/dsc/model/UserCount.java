package acn.clickstream.dsc.model;

import lombok.ToString;

@ToString
public class UserCount {
    private String user;
    private Long counter;

    public UserCount(String user, Long counter) {
        this.user = user;
        this.counter = counter;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
