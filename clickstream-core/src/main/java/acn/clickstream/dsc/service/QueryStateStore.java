package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.TimeUserCount;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QueryStateStore {

    Set<TimeUserCount> getTimeUserCounts();


}
