package kr.co.seoulit.logistics.sales.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;

public interface ContractDetailRepository extends CrudRepository<ContractDetailTO, String> {

	Optional<ContractDetailTO> findByContractDetailNo(String contractDetailNo);
	
}
