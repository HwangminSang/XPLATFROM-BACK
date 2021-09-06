package kr.co.seoulit.logistics.logisticsInfo.controller;

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

import kr.co.seoulit.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemInfoTO;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemTO;

@RestController
@RequestMapping("/logisticsInfo/*")
public class ItemController{
	// serviceFacade 참조변수 선언
	@Autowired
	private LogisticsInfoServiceFacade logisticsSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchItem.do" , method=RequestMethod.GET)
	public ModelAndView searchItem(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");
		String itemClassification = request.getParameter("itemClassification");
		String itemGroupCode = request.getParameter("itemGroupCode");
		String minPrice = request.getParameter("minPrice");
		String maxPrice = request.getParameter("maxPrice");

		ArrayList<ItemInfoTO> itemInfoList = null;
		String[] paramArray = null;

		HashMap<String, Object> map = new HashMap<>();
		switch (searchCondition) {

		case "ALL":

			paramArray = null;
			break;

		case "ITEM_CLASSIFICATION":

			paramArray = new String[] { itemClassification };
			break;

		case "ITEM_GROUP_CODE":

			paramArray = new String[] { itemGroupCode };
			break;

		case "STANDARD_UNIT_PRICE":

			paramArray = new String[] { minPrice, maxPrice };
			break;

		}

		itemInfoList = logisticsSF.getItemInfoList(searchCondition, paramArray);

		map.put("gridRowJson", itemInfoList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/getStandardUnitPrice.do", method=RequestMethod.GET)
	public ModelAndView getStandardUnitPrice(HttpServletRequest request) {
		String itemCode = request.getParameter("itemCode");
		int price = 0;
		
		HashMap<String, Object> map = new HashMap<>();
		price = logisticsSF.getStandardUnitPrice(itemCode);

		map.put("gridRowJson", price);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/getStandardUnitPriceBox.do", method=RequestMethod.GET)
	public ModelAndView getStandardUnitPriceBox(HttpServletRequest request) {

		String itemCode = request.getParameter("itemCode");
		int price = 0;
		
		HashMap<String, Object> map = new HashMap<>();
		price = logisticsSF.getStandardUnitPriceBox(itemCode);

		map.put("gridRowJson", price);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchListProcess.do", method=RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<ItemTO> itemTOList = gson.fromJson(batchList, new TypeToken<ArrayList<ItemTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = logisticsSF.batchItemListProcess(itemTOList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

}
