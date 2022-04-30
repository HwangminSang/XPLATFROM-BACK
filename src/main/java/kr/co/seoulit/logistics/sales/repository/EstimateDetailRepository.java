package kr.co.seoulit.logistics.sales.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;


@Repository
public interface EstimateDetailRepository extends CrudRepository<EstimateDetailTO, String> {
	
	List<EstimateDetailTO> findByEstimateNo(String estimateNo);	


}
