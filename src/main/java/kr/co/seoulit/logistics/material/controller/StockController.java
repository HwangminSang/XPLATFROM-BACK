package kr.co.seoulit.logistics.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.material.serviceFacade.MaterialServiceFacade;
import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/material/*")
public class StockController{
	// serviceFacade 참조변수 선언

	private final MaterialServiceFacade materialServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;

	
	//재고관리 재고 클릭시 자동으로 받아옴
	@RequestMapping(value="/searchStockList")
	public void searchStockList(@RequestAttribute("resData")PlatformData resData) throws Exception {

		
		// jpa 구현
		ArrayList<StockTO> stockList = materialServiceFacade.getStockList();
		
		datasetBeanMapper.beansToDataset(resData, stockList, StockTO.class);
	
	}

	//재고로그리스트
	@RequestMapping(value="/searchStockLogList")
	public void searchStockLogList(@RequestAttribute("resData")PlatformData resData,
			                       @RequestAttribute("reqData")PlatformData reqData) throws Exception {

		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();

		// jpa 구현 고려
		ArrayList<StockLogTO> stockLogList = materialServiceFacade.getStockLogList(startDate,endDate);
		
		datasetBeanMapper.beansToDataset(resData, stockLogList, StockLogTO.class);
		
	}

	//입고
	@RequestMapping(value="/warehousing")
	public void warehousing(@RequestAttribute("reqData") PlatformData reqData,
			                @RequestAttribute("resData") PlatformData resData) throws Exception {
		
	
		String orderNoList = reqData.getVariableList().getString("orderNoList"); //pk
		
		ArrayList<String> orderNoArr = new ArrayList<>();
		orderNoArr.add(orderNoList);
		
		//jpa 미구현 - procedure 호출
		HashMap<String, Object> resultMap=materialServiceFacade.warehousing(orderNoArr);
		resData.getVariableList().add("g_procedureMsg", resultMap.get("errorMsg"));
		resData.getVariableList().add("g_procedureCode", resultMap.get("errorCode"));
		
	}
	
}
