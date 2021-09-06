package kr.co.seoulit.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.system.authorityManager.to.AuthorityGroupTO;
import kr.co.seoulit.system.authorityManager.to.EmployeeAuthorityTO;
import kr.co.seoulit.system.authorityManager.to.MenuAuthorityTO;
import kr.co.seoulit.hr.affair.to.EmpInfoTO;

import kr.co.seoulit.system.authorityManager.applicationService.AuthorityGroupApplicationService;
import kr.co.seoulit.system.authorityManager.applicationService.LogInApplicationService;
import kr.co.seoulit.system.authorityManager.applicationService.UserMenuApplicationService;
import kr.co.seoulit.system.authorityManager.applicationService.MenuApplicationService;
import kr.co.seoulit.system.authorityManager.exception.IdNotFoundException;
import kr.co.seoulit.system.authorityManager.exception.PwMissMatchException;
import kr.co.seoulit.system.authorityManager.exception.PwNotFoundException;

@Service
public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	@Autowired
	private  LogInApplicationService logInAS;
	@Autowired
	private  UserMenuApplicationService userMenuAS;
	@Autowired
	private  MenuApplicationService menuAS;
	@Autowired
	private  AuthorityGroupApplicationService authorityGroupAS;
	
	
	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		 return	logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);		//문제가 없을 경우 로그인 한 사람의 정보가 담겨짐
		
	}

	@Override
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {

		return userMenuAS.getUserMenuCode(workplaceCode, deptCode, positionCode, application);
	}
	
	public String[] getAllMenuList() {
		
		return menuAS.getAllMenuList();
	}
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {

		return authorityGroupAS.getAuthorityGroup();
	}
	
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {

		return authorityGroupAS.getUserAuthorityGroup(empCode);
	}
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {

		authorityGroupAS.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);
			
	}
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
	
		menuAS.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);	
	}
	
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {

		return menuAS.getMenuAuthority(authorityGroupCode);
	}

}
