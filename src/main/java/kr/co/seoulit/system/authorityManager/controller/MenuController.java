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
import kr.co.seoulit.system.authorityManager.to.MenuAuthorityTO;

@RestController
@RequestMapping("/authorityManager/*")
public class MenuController{
	
	@Autowired
	private AuthorityManagerServiceFacade authorityManagerSF;
	private ModelMap modelMap = new ModelMap();
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@RequestMapping(value="/insertMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap insertMenuAuthority(HttpServletRequest request) {

		String authorityGroupCode = request.getParameter("authorityGroupCode");
		String insertDataList = request.getParameter("insertData");
		System.out.println(insertDataList); 
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<MenuAuthorityTO>>() {}.getType());
		
		
		try {
			
			authorityManagerSF.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		}catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());
		}
		return modelMap;
	}
	

	@RequestMapping(value="/getMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap getMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		

		String authorityGroupCode = request.getParameter("authorityGroupCode");
		
		
		try {
			
			ArrayList<MenuAuthorityTO> menuAuthorityTOList = authorityManagerSF.getMenuAuthority(authorityGroupCode);
			
			modelMap.put("gridRowJson", menuAuthorityTOList);
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
