package acn.clickstream.dsc.jpa_model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "unique_visit",
        indexes = {@Index(name = "timestamp_index", columnList = "timestamp", unique = true)})
public class UniqueVisit extends TimeCountBase {

    public UniqueVisit() {
    }

    public UniqueVisit(Long timestamp, Long count) {
        super(timestamp, count);
    }
}
