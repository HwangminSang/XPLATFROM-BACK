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
import kr.co.seoulit.system.basicInfo.to.DepartmentTO;

@RestController
@RequestMapping("/basicInfo/*")
public class DepartmentController{

	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade orgSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchDepartment.do", method = RequestMethod.GET)
	public ModelAndView searchDepartmentList(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");

		String companyCode = request.getParameter("companyCode");

		String workplaceCode = request.getParameter("workplaceCode");

		
		ArrayList<DepartmentTO> departmentList = null;

		HashMap<String, Object> map = new HashMap<>();
		departmentList = orgSF.getDepartmentList(searchCondition, companyCode, workplaceCode);

		map.put("gridRowJson", departmentList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchDepartmentListProcess.do", method = RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<DepartmentTO> departmentList = gson.fromJson(batchList, new TypeToken<ArrayList<DepartmentTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = orgSF.batchDepartmentListProcess(departmentList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}
}
