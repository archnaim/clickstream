package acn.clickstream.dsc.service;

import acn.clickstream.dsc.jpa_model.UniqueVisit;
import acn.clickstream.dsc.repository.UniqueVisitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniqueVisitDao {

    @Autowired
    private UniqueVisitRepo uniqueVisitRepo;

    private Logger logger = LoggerFactory.getLogger(BounceVisitDao.class);

    public void insert(Long timestamp, Long count) {
        UniqueVisit uniqueVisit = new UniqueVisit(timestamp, count);
        uniqueVisitRepo.save(uniqueVisit);
        logger.info(String.format("completed inserting unique visit %s into database", uniqueVisit.toString()));
    }

    public List<UniqueVisit> get(Long dateFrom, Long dateTo) {
        List<UniqueVisit> listUniqueVisit = uniqueVisitRepo
                .findByTimestampBetween(dateFrom, dateTo);
        logger.info(String.format("completed querying unique visit from database, %d rows found", listUniqueVisit.size()));
        return listUniqueVisit;
    }
}
