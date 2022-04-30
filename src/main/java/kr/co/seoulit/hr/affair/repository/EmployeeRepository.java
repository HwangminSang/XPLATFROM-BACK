package kr.co.seoulit.hr.affair.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import kr.co.seoulit.hr.affair.to.EmployeeTO;

public interface EmployeeRepository extends JpaRepository<EmployeeTO, String> {

	List<EmployeeTO> findAll();
		
}
