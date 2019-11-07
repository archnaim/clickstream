package acn.clickstream.dsc.service;

import acn.clickstream.dsc.jpa_model.BounceVisit;
import acn.clickstream.dsc.repository.BounceVisitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BounceVisitDao {

    @Autowired
    private BounceVisitRepo bounceVisitRepo;

    private Logger logger = LoggerFactory.getLogger(BounceVisitDao.class);

    public void insert(Long timestamp, Long count) {
        BounceVisit bounceVisit = new BounceVisit(timestamp, count);
        bounceVisitRepo.save(bounceVisit);
        logger.info(String.format("completed inserting bounce visit %s into database", bounceVisit.toString()));
    }

    public List<BounceVisit> get(Long dateFrom, Long dateTo) {
        List<BounceVisit> listBounceVisit = bounceVisitRepo
                .findByTimestampBetween(dateFrom, dateTo);
        logger.info(String.format("completed querying bounce visit from database, %d rows found", listBounceVisit.size()));
        return listBounceVisit;
    }
}
