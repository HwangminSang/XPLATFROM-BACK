package kr.co.seoulit.system.basicInfo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;

public interface CompanyRepository extends JpaRepository<CompanyTO, String> {

	List<CompanyTO> findAll(Sort sort);

}
