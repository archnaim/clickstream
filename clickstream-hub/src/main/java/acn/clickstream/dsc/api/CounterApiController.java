package acn.clickstream.dsc.api;

import acn.clickstream.dsc.jpa_model.BounceVisit;
import acn.clickstream.dsc.jpa_model.UniqueVisit;
import acn.clickstream.dsc.service.BounceVisitDao;
import acn.clickstream.dsc.service.UniqueVisitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/counter")
public class CounterApiController {

    @Autowired
    private UniqueVisitDao uniqueVisitDao;

    @Autowired
    private BounceVisitDao bounceVisitDao;

    @GetMapping("/visit_per_window_time/{dateFrom}-{dateTo}")
    public List<UniqueVisit> getUniqueVisit(@PathVariable String dateFrom, @PathVariable String dateTo) {
        List<UniqueVisit> uniqueVisits = uniqueVisitDao.get(Long.valueOf(dateFrom), Long.valueOf(dateTo));
        return uniqueVisits;
    }

    @GetMapping("/bounce_per_window_time/{dateFrom}-{dateTo}")
    public List<BounceVisit> getBounceVisit(@PathVariable String dateFrom, @PathVariable String dateTo) {
        List<BounceVisit> bounceVisits = bounceVisitDao.get(Long.valueOf(dateFrom), Long.valueOf(dateTo));
        return bounceVisits;
    }


}
