package kr.co.seoulit.logistics.material.controller;

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

import kr.co.seoulit.logistics.material.serviceFacade.MaterialServiceFacade;
import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;

@RestController
@RequestMapping("/material/*")
public class StockController{
	// serviceFacade 참조변수 선언
	@Autowired
	private MaterialServiceFacade purchaseSF;
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchStockList.do" , method=RequestMethod.GET)
	public ModelAndView searchStockList() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<StockTO> stockList = purchaseSF.getStockList();

		map.put("gridRowJson", stockList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/searchStockLogList.do" , method=RequestMethod.GET)
	public ModelAndView searchStockLogList(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		ArrayList<StockLogTO> stockLogList = purchaseSF.getStockLogList(startDate,endDate);

		map.put("gridRowJson", stockLogList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/warehousing.do", method=RequestMethod.POST)
	public ModelAndView warehousing(HttpServletRequest request) {
		String orderNoListStr = request.getParameter("orderNoList");

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	

		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap = purchaseSF.warehousing(orderNoArr);
		return new ModelAndView("jsonView",resultMap);
	}
}
