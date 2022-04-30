package kr.co.seoulit.system.basicInfo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;

public interface WorkplaceRepository extends CrudRepository<WorkplaceTO, String> {

	List<WorkplaceTO> findByCompanyCode(String companyCode);

}
