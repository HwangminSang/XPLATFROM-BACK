package kr.co.seoulit.hr.affair.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.hr.affair.to.EmployeeDetailTO;

@Mapper
public interface EmployeeDetailDAO {

	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(HashMap<String, String> param);
	
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode);
	
	public void insertEmployeeDetail(EmployeeDetailTO TO);

	public void updateEmployeeImg(EmployeeDetailTO employeeDetailTO);
	

}
