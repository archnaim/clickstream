package acn.clickstream.dsc.jpa_model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "bounce_visit",
        indexes = {@Index(name = "timestamp_index", columnList = "timestamp", unique = true)})
public class BounceVisit extends TimeCountBase {

    public BounceVisit() {
        super();
    }

    public BounceVisit(Long timestamp, Long count) {
        super(timestamp, count);
    }
}
