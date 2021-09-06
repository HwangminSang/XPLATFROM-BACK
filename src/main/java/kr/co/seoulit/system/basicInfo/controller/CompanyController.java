package kr.co.seoulit.system.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;

@RestController
@RequestMapping("/basicInfo/*")
public class CompanyController{
	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade orgSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchCompany.do",method=RequestMethod.GET)
	public ModelAndView searchCompanyList() {

		ArrayList<CompanyTO> companyList = null;

		HashMap<String, Object> map = new HashMap<>();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		companyList = orgSF.getCompanyList();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    //         companyList=    
		map.put("gridRowJson", companyList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchCompanyListProcess.do", method=RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<CompanyTO> companyList = gson.fromJson(batchList, new TypeToken<ArrayList<CompanyTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = orgSF.batchCompanyListProcess(companyList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

}
