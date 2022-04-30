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
import kr.co.seoulit.system.basicInfo.to.CustomerTO;
@RestController
@RequestMapping("/basicInfo/*")
public class CustomerController{

	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade cooperatorSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchCustomer", method=RequestMethod.GET)
	public ModelAndView searchCustomerList(HttpServletRequest request) {
		String searchCondition = request.getParameter("searchCondition");

		String companyCode = request.getParameter("companyCode");

		String workplaceCode = request.getParameter("workplaceCode");

		ArrayList<CustomerTO> customerList = null;

		HashMap<String, Object> map = new HashMap<>();
		customerList = cooperatorSF.getCustomerList(searchCondition, companyCode, workplaceCode);

		map.put("gridRowJson", customerList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchCustomerListProcess", method=RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<CustomerTO> customerList = gson.fromJson(batchList, new TypeToken<ArrayList<CustomerTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = cooperatorSF.batchCustomerListProcess(customerList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

}
