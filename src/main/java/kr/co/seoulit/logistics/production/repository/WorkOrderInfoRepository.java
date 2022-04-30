package kr.co.seoulit.logistics.production.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;

public interface WorkOrderInfoRepository extends CrudRepository<WorkOrderInfoTO, String> {

	List<WorkOrderInfoTO> findByOperationCompletedIsNull();
	
}
