package kr.co.seoulit.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.DeliveryInfoTO;

@RestController
@RequestMapping("/sales/*")
public class DeliveryController{
	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesSF;
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	@RequestMapping(value="/searchDeliveryInfoList.do", method=RequestMethod.GET)
	public ModelAndView searchDeliveryInfoList() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<DeliveryInfoTO> deliveryInfoList = salesSF.getDeliveryInfoList();

		map.put("gridRowJson", deliveryInfoList);
		map.put("errorCode", 0);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	// batchListProcess

	@RequestMapping(value="/batchListProcess.do", method=RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		HashMap<String, Object> map = new HashMap<>();
		List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
		}.getType());

		System.out.println(deliveryTOList);

		HashMap<String, Object> resultMap = salesSF.batchDeliveryListProcess(deliveryTOList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/searchDeliverableContractList.do", method=RequestMethod.GET)
	public ModelAndView searchDeliverableContractList(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();

		String searchCondition = request.getParameter("searchCondition");	// 기간 or 거래처
		String startDate = request.getParameter("startDate");				// 시작날
		String endDate = request.getParameter("endDate");					// 종료날
		String customerCode = request.getParameter("customerCode");			// 거래처코드 

		ArrayList<ContractInfoTO> deliverableContractList = null;

		if (searchCondition.equals("searchByDate")) {//기간검색

			String[] paramArray = { startDate, endDate };
			deliverableContractList = salesSF.getDeliverableContractList("searchByDate", paramArray);

		} else if (searchCondition.equals("searchByCustomer")) {//거래처검색

			String[] paramArray = { customerCode };
			deliverableContractList = salesSF.getDeliverableContractList("searchByCustomer", paramArray);

		}

		map.put("gridRowJson", deliverableContractList);
		map.put("errorCode", 0);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/deliver.do", method=RequestMethod.POST)
	public ModelAndView deliver(HttpServletRequest request) {

		HashMap<String,Object> resultMap = new HashMap<>();
		String contractDetailNo = request.getParameter("contractDetailNo");

		resultMap = salesSF.deliver(contractDetailNo);
			
		return new ModelAndView("jsonView",resultMap);
	}

}
