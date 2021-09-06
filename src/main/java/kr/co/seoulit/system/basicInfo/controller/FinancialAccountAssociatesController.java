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
import kr.co.seoulit.system.basicInfo.to.FinancialAccountAssociatesTO;

@RestController
@RequestMapping("/basicInfo/*")
public class FinancialAccountAssociatesController{
	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade cooperatorSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchFinancialAccountAssociatesList.do", method = RequestMethod.GET)
	public ModelAndView searchFinancialAccountAssociatesList(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");

		String workplaceCode = request.getParameter("workplaceCode");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

		HashMap<String, Object> map = new HashMap<>();
		financialAccountAssociatesList = cooperatorSF.getFinancialAccountAssociatesList(searchCondition,
				workplaceCode);

		map.put("gridRowJson", financialAccountAssociatesList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchFinancialAccountAssociatesListProcess.do", method = RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = gson.fromJson(batchList,
				new TypeToken<ArrayList<FinancialAccountAssociatesTO>>() {
				}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = cooperatorSF
				.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

}
