package acn.clickstream.dsc.repository;

import acn.clickstream.dsc.jpa_model.UniqueVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniqueVisitRepo extends JpaRepository<UniqueVisit, Long> {
    List<UniqueVisit> findByTimestampBetween(long dateFrom, long dateTo);
}
