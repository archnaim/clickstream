package acn.clickstream.dsc.repository;

import acn.clickstream.dsc.model.ClickstreamPayload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickstreamRepo extends CrudRepository<ClickstreamPayload,String>{
}
