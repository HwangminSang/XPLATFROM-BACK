package kr.co.seoulit.system.authorityManager.applicationService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.system.authorityManager.dao.AuthorityGroupDAO;
import kr.co.seoulit.system.authorityManager.to.AuthorityGroupTO;
import kr.co.seoulit.system.authorityManager.to.EmployeeAuthorityTO;

@Component
public class AuthorityGroupApplicationServiceImpl implements AuthorityGroupApplicationService {
	
	
	@Autowired
	private AuthorityGroupDAO authorityGroupDAO;
	
	
	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {  
		
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;
	      authorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);
	      return authorityGroupTOList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {

	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;

	    	  authorityGroupTOList = authorityGroupDAO.selectAuthorityGroupList();

	       return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(
		String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {

    	  // 해당 사원의 기존 그룹권한 정보 삭제
    	  authorityGroupDAO.deleteEmployeeAuthorityGroup(empCode);
    	  
    	  // 새로운 그룹권한 정보 insert 
    	  for(EmployeeAuthorityTO bean : employeeAuthorityTOList) {
    		  
    		  authorityGroupDAO.insertEmployeeAuthorityGroup(bean);
    		  
    	  }
	}
}
