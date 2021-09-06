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
import kr.co.seoulit.logistics.material.to.OrderInfoTO;

@RestController
@RequestMapping("/material/*")
public class OrderController{
	// serviceFacade 참조변수 선언
	@Autowired
	private MaterialServiceFacade MaterialSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	//UPDATE
	@RequestMapping(value="/checkOrderInfo.do", method=RequestMethod.POST)
	public ModelAndView checkOrderInfo(HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<>();
		String orderNoListStr = request.getParameter("orderNoList");
		
		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());
		resultMap=MaterialSF.checkOrderInfo(orderNoArr);

		return new ModelAndView("jsonView",resultMap);
		
	}
	
	@RequestMapping(value="/getOrderList.do" , method=RequestMethod.GET)
	public ModelAndView getOrderList(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap = MaterialSF.getOrderList(startDate, endDate);
		return new ModelAndView("jsonView",resultMap);
		
	}

	@RequestMapping(value="/showOrderDialog.do" , method= RequestMethod.POST)
	public ModelAndView openOrderDialog(HttpServletRequest request) {

		String mrpNoListStr = request.getParameter("mrpGatheringNoList");
	/*	ArrayList<String> mrpNoArr = gson.fromJson(mrpNoListStr, new TypeToken<ArrayList<String>>() {
		}.getType()); //제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
	*/
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap = MaterialSF.getOrderDialogInfo(mrpNoListStr);
		//	resultMap = MaterialSF.getOrderDialogInfo(mrpNoArr);
		return new ModelAndView("jsonView",resultMap);
		
	}

	@RequestMapping(value= "/showOrderInfo.do" ,method=RequestMethod.POST)
	public ModelAndView showOrderInfo(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		ArrayList<OrderInfoTO> orderInfoList = MaterialSF.getOrderInfoList(startDate,endDate);
		map.put("gridRowJson", orderInfoList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}
	
	@RequestMapping(value="/searchOrderInfoListOnDelivery.do", method=RequestMethod.GET)
	public ModelAndView searchOrderInfoListOnDelivery() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<OrderInfoTO> orderInfoListOnDelivery = MaterialSF.getOrderInfoListOnDelivery();
		map.put("gridRowJson", orderInfoListOnDelivery);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/order.do", method=RequestMethod.POST)
	public ModelAndView order(HttpServletRequest request) {

		String mrpGatheringNoListStr = request.getParameter("mrpGatheringNoList");
		HashMap<String, Object> resultMap = new HashMap<>();
		 ArrayList<String> mrpGaNoArr = gson.fromJson(mrpGatheringNoListStr , new TypeToken<ArrayList<String>>() {
	      }.getType());
		 	//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		resultMap = MaterialSF.order(mrpGaNoArr);
		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/optionOrder.do", method=RequestMethod.POST)
	public ModelAndView optionOrder(HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<>();
		String itemCode = request.getParameter("itemCode");
		String itemAmount = request.getParameter("itemAmount");

		resultMap = MaterialSF.optionOrder(itemCode, itemAmount);
		return new ModelAndView("jsonView",resultMap);
	}


}
