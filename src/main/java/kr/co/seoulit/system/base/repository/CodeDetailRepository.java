package kr.co.seoulit.system.base.repository;

import java.util.ArrayList;


import org.springframework.data.repository.CrudRepository;

import kr.co.seoulit.system.base.to.CodeDetailTO;

public interface CodeDetailRepository extends CrudRepository<CodeDetailTO, String>{
	
	ArrayList<CodeDetailTO> findByDivisionCodeNoLike(String divisionCodeNo);

}
