package kr.co.seoulit.logistics.logisticsInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemInfoTO;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemTO;
import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/logisticsInfo/*")
public class ItemController{
	// serviceFacade 참조변수 선언
	
	private final LogisticsInfoServiceFacade logisticsInfoServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;

//	// GSON 라이브러리
//	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환



	@RequestMapping(value="/getStandardUnitPrice")
	public void getStandardUnitPrice(HttpServletRequest request) throws Exception {
		
		PlatformData reqData = (PlatformData)request.getAttribute("reqData");
		PlatformData resData = (PlatformData)request.getAttribute("resData");
				
		String itemCode = reqData.getVariable("itemCode").getString();		
		ItemTO itemTo = logisticsInfoServiceFacade.getStandardUnitPrice(itemCode);
				
		datasetBeanMapper.beanToDataset(resData, itemTo, ItemTO.class);
		
	}
	
//	
//	@RequestMapping(value="/searchItem" , method=RequestMethod.GET)
//	public ModelAndView searchItem(HttpServletRequest request) {
//
//		String searchCondition = request.getParameter("searchCondition");
//		String itemClassification = request.getParameter("itemClassification");
//		String itemGroupCode = request.getParameter("itemGroupCode");
//		String minPrice = request.getParameter("minPrice");
//		String maxPrice = request.getParameter("maxPrice");
//
//		ArrayList<ItemInfoTO> itemInfoList = null;
//		String[] paramArray = null;
//
//		HashMap<String, Object> map = new HashMap<>();
//		switch (searchCondition) {
//
//		case "ALL":
//
//			paramArray = null;
//			break;
//
//		case "ITEM_CLASSIFICATION":
//
//			paramArray = new String[] { itemClassification };
//			break;
//
//		case "ITEM_GROUP_CODE":
//
//			paramArray = new String[] { itemGroupCode };
//			break;
//
//		case "STANDARD_UNIT_PRICE":
//
//			paramArray = new String[] { minPrice, maxPrice };
//			break;
//
//		}
//
//		itemInfoList = logisticsInfoServiceFacade.getItemInfoList(searchCondition, paramArray);
//
//		map.put("gridRowJson", itemInfoList);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//		return new ModelAndView("jsonView",map);
//	}
//
//	@RequestMapping(value="/getStandardUnitPriceBox", method=RequestMethod.GET)
//	public ModelAndView getStandardUnitPriceBox(HttpServletRequest request) {
//
//		String itemCode = request.getParameter("itemCode");
//		int price = 0;
//		
//		HashMap<String, Object> map = new HashMap<>();
//		price = logisticsInfoServiceFacade.getStandardUnitPriceBox(itemCode);
//
//		map.put("gridRowJson", price);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//		return new ModelAndView("jsonView",map);
//	}
//
//	@RequestMapping(value="/batchListProcess", method=RequestMethod.POST)
//	public ModelAndView batchListProcess(HttpServletRequest request) {
//
//		String batchList = request.getParameter("batchList");
//
//		ArrayList<ItemTO> itemTOList = gson.fromJson(batchList, new TypeToken<ArrayList<ItemTO>>() {
//		}.getType());
//
//		HashMap<String, Object> map = new HashMap<>();
//		HashMap<String, Object> resultMap = logisticsInfoServiceFacade.batchItemListProcess(itemTOList);
//
//		map.put("result", resultMap);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//
//		return new ModelAndView("jsonView",map);
//	}

}
