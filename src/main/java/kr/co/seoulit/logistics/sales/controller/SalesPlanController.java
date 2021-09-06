package kr.co.seoulit.logistics.sales.controller;

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

import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.SalesPlanTO;

@RestController
@RequestMapping("/sales/*")
public class SalesPlanController{

	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesSF;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchSalesPlan.do", method=RequestMethod.GET)
	public ModelAndView searchSalesPlanInfo(HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateSearchCondition = request.getParameter("dateSearchCondition");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<SalesPlanTO> salesPlanTOList = salesSF.getSalesPlanList(dateSearchCondition, startDate, endDate);

		map.put("gridRowJson", salesPlanTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchSalesPlanListProcess.do", method=RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<SalesPlanTO> salesPlanTOList = gson.fromJson(batchList, new TypeToken<ArrayList<SalesPlanTO>>() {
		}.getType());

		HashMap<String, Object> resultMap = salesSF.batchSalesPlanListProcess(salesPlanTOList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

}
