package kr.co.seoulit.hr.affair.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.hr.affair.to.EmployeeBasicTO;
import kr.co.seoulit.hr.affair.to.EmployeeDetailTO;
import kr.co.seoulit.hr.affair.to.EmployeeSecretTO;

public interface AffairServiceFacade {

	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray);

	public EmpInfoTO getEmpInfo(String companyCode, String empCode);

	public String getNewEmpCode(String companyCode);
	
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode);
	
	public Boolean checkUserIdDuplication(String companyCode, String newUserId);
	
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList);

	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList);
	
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList);

	public void updateEmpImg(EmployeeDetailTO employeeDetailTO);
	
	
}
