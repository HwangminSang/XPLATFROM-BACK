package kr.co.seoulit.hr.affair.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.hr.affair.applicationService.EmpApplicationService;
import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.hr.affair.to.EmployeeBasicTO;
import kr.co.seoulit.hr.affair.to.EmployeeDetailTO;
import kr.co.seoulit.hr.affair.to.EmployeeSecretTO;

@Service
public class AffairServiceFacadeImpl implements AffairServiceFacade {
	
	@Autowired
	private EmpApplicationService empAS;

	@Override
	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) {

		return empAS.getAllEmpList(searchCondition, paramArray);
	}

	@Override
	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {

		return empAS.getEmpInfo(companyCode, empCode);
	}

	@Override
	public String getNewEmpCode(String companyCode) {

		return empAS.getNewEmpCode(companyCode);
	}

	@Override
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {

		return empAS.batchEmpBasicListProcess(empBasicList);
	}

	@Override
	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {

		return empAS.batchEmpDetailListProcess(empDetailList);
	}

	@Override
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {

		return empAS.batchEmpSecretListProcess(empSecretList);
	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {

		Boolean duplicated = false;
			duplicated = empAS.checkUserIdDuplication(companyCode, newUserId);

		return duplicated;
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {

		Boolean duplicated = false;

			duplicated = empAS.checkEmpCodeDuplication(companyCode, newEmpCode);

		return duplicated;
	}

	@Override
	public void updateEmpImg(EmployeeDetailTO employeeDetailTO) {
		
		empAS.updateEmpImg(employeeDetailTO);
		
	}

	
}
