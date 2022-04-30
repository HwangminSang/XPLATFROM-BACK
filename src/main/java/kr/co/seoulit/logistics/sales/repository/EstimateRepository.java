package kr.co.seoulit.logistics.sales.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import kr.co.seoulit.logistics.sales.to.EstimateTO;

@Repository
public interface EstimateRepository extends CrudRepository<EstimateTO, String> {
	
	int countByEstimateDate(String estimateDate);
	//select count(estimateto0_.estimate_no) as col_0_0_ 
    //from estimate estimateto0_ 
    //where estimateto0_.estimate_date=?
	
	Optional<EstimateTO> findByEstimateNo(String estimateNo);
 
}
