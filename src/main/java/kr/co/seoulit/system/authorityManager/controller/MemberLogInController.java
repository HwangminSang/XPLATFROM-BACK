package kr.co.seoulit.system.authorityManager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;

import kr.co.seoulit.system.authorityManager.to.MenuAuthorityTO;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;
import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
public class MemberLogInController  {
    

	private final AuthorityManagerServiceFacade authorityManagerServiceFacade;
	
	private final DatasetBeanMapper datasetBeanMapper;
	
	
	
	@RequestMapping(value="/login")
    public void LogInCheck(HttpServletRequest request) throws Exception{
  
		PlatformData reqData = (PlatformData) request.getAttribute("reqData");
		PlatformData resData = (PlatformData) request.getAttribute("resData");
		
		String companyCode = datasetBeanMapper.datasetToBean(reqData, CompanyTO.class).getCompanyCode();
		String workplaceCode = datasetBeanMapper.datasetToBean(reqData, WorkplaceTO.class).getWorkplaceCode();
		                 //view단에서 지정한 변수로 값을 구해온다.
		String userId = reqData.getVariable("userId").getString();
		String userPassword = reqData.getVariable("userPassWord").getString();
		
        EmpInfoTO TO = authorityManagerServiceFacade.accessToAuthority(companyCode, workplaceCode, userId, userPassword);
            
            if (TO != null) {
    			datasetBeanMapper.beanToDataset(resData, TO, EmpInfoTO.class);
    			
    			List<MenuAuthorityTO> getMenuAuthorityList = authorityManagerServiceFacade.getMenuAuthority(TO.getEmpCode());
    			datasetBeanMapper.beansToDataset(resData, getMenuAuthorityList, MenuAuthorityTO.class);     
			}
    }
}
