package kr.co.seoulit.system.authorityManager.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.system.authorityManager.to.AuthorityGroupTO;
import kr.co.seoulit.system.authorityManager.to.EmployeeAuthorityTO;
 
public interface AuthorityGroupApplicationService {
	
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);

}
