package kr.co.seoulit.system.authorityManager.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import kr.co.seoulit.system.authorityManager.to.AuthorityGroupTO;
import kr.co.seoulit.system.authorityManager.to.EmployeeAuthorityTO;

@RestController
@RequestMapping("/authorityManager/*")
public class AuthorityGroupController{

	@Autowired
	private AuthorityManagerServiceFacade authorityManagerSF;
	private Gson gson = new GsonBuilder().serializeNulls().create();
	private ModelMap modelMap = new ModelMap();
	
	@RequestMapping(value="/getUserAuthorityGroup.do", method=RequestMethod.GET)
	public ModelMap getUserAuthorityGroup(HttpServletRequest request) {
	
		String empCode = request.getParameter("empCode");
		
		try {
		
			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerSF.getUserAuthorityGroup(empCode);
			
			modelMap.put("gridRowJson", authorityGroupTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		
		return modelMap;
	}
	
	@RequestMapping(value="/getAuthorityGroup.do", method=RequestMethod.GET)
	public ModelMap getAuthorityGroup(HttpServletRequest request) {
		
		try {
			
			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerSF.getAuthorityGroup();
			
			modelMap.put("gridRowJson", authorityGroupTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());
			
		} 
		
		return modelMap;
	}

	@RequestMapping(value="/insertEmployeeAuthorityGroup.do", method=RequestMethod.POST)
	public ModelMap insertEmployeeAuthorityGroup(HttpServletRequest request) {
		
		String empCode = request.getParameter("empCode");
		String insertDataList = request.getParameter("insertData");
		System.out.println(insertDataList); 
		ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<EmployeeAuthorityTO>>() {}.getType());
		
		try {
	
			authorityManagerSF.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);

			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());
			
		}
		
		return modelMap;
	}
	
}
