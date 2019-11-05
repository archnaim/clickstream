package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.TimeUniqueCount;
import acn.clickstream.dsc.model.TimeUserCount;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QueryStateStore {

    Set<TimeUserCount> getTimeUserCounts();

    Set<TimeUniqueCount> getVisitCount();

    Set<TimeUniqueCount> getBounceCount();

    void getFirstLastPage();


}
