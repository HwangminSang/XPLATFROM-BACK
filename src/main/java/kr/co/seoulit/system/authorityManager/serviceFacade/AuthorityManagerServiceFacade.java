package kr.co.seoulit.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.system.authorityManager.exception.IdNotFoundException;
import kr.co.seoulit.system.authorityManager.exception.PwMissMatchException;
import kr.co.seoulit.system.authorityManager.exception.PwNotFoundException;
import kr.co.seoulit.system.authorityManager.to.AuthorityGroupTO;
import kr.co.seoulit.system.authorityManager.to.EmployeeAuthorityTO;
import kr.co.seoulit.system.authorityManager.to.MenuAuthorityTO;

public interface AuthorityManagerServiceFacade {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application);
	public String[] getAllMenuList();
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();

	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);

	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);

}
