package kr.co.seoulit.hr.affair.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.hr.affair.to.EmployeeBasicTO;

@Mapper
public interface EmployeeBasicDAO {

	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode);
	
	public EmployeeBasicTO selectEmployeeBasicTO(HashMap<String, String> param);
	
	public void insertEmployeeBasic(EmployeeBasicTO TO);
	
	public void changeUserAccountStatus(HashMap<String, String> param);
	
}
