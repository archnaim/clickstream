package acn.clickstream.dsc.repository;

import acn.clickstream.dsc.model.FaqSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqSummaryRepo extends CrudRepository<FaqSummary,String> {

    Page<FaqSummary> findAllByOrderByCounterDesc(Pageable pageable);

}
