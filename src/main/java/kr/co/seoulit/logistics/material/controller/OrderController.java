package kr.co.seoulit.logistics.material.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.material.serviceFacade.MaterialServiceFacade;
import kr.co.seoulit.logistics.material.to.OrderDialogTempTO;
import kr.co.seoulit.logistics.material.to.OrderInfoTO;
import kr.co.seoulit.logistics.material.to.OrderTempTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/material/*")
public class OrderController{
	// serviceFacade 참조변수 선언

	private final MaterialServiceFacade materialServiceFacade;
	
	private final DatasetBeanMapper datasetBeanMapper;

	
	//UPDATE  제품검사.
	@RequestMapping(value="/inspection") 									// checkOrderInfo
	public void inspection(@RequestAttribute("reqData")PlatformData reqData,
			@RequestAttribute("resData")PlatformData resData) throws Exception {
		
	
		String orderNoList = reqData.getVariableList().getString("orderNoList");  // pk번호  발주시 생성되는 발주번호
		
		ArrayList<String> orderNoArr = new ArrayList<>();
		orderNoArr.add(orderNoList);
		
		// jpa 미구현 - procedure 호출
		HashMap<String,Object> map=materialServiceFacade.checkOrderInfo(orderNoArr);
	   
		resData.getVariableList().add("g_procedureMsg", map.get("errorMsg"));
		resData.getVariableList().add("g_procedureCode", map.get("errorCode"));
		
	}
	
   //재고처리 및 발주 필요 조회
	@RequestMapping(value="/getOrderList")
	public void getOrderList(@RequestAttribute("reqData")PlatformData reqData,
			@RequestAttribute("resData")PlatformData resData) throws Exception {
		
		
		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
		
		//jpa 미구현 - procedure 호출
		HashMap<String, Object> resultMap = materialServiceFacade.getOrderList(startDate, endDate);
		
		@SuppressWarnings("unchecked")
		ArrayList<OrderTempTO> OrderList = (ArrayList<OrderTempTO>)resultMap.get("gridRowJson");
		
		
		datasetBeanMapper.beansToDataset(resData, OrderList, OrderTempTO.class);
	}

	
	//모의재고 처리및 취합발주
	@RequestMapping(value="/openOrderDialog")
	public void openOrderDialog(@RequestAttribute("reqData")PlatformData reqData,
		                     	@RequestAttribute("resData")PlatformData resData) throws Exception {
		
		String mrpGatheringNoListStr = reqData.getVariable("mrpGatheringNoList").getString(); //핵심  pk
		
		//jpa 미구현 - procedure 호출
		HashMap<String,Object> resultMap = materialServiceFacade.getOrderDialogInfo( mrpGatheringNoListStr);
		@SuppressWarnings("unchecked")
		//발주필요목록조회 취합 발주 
		ArrayList<OrderDialogTempTO> orderDialogList = (ArrayList<OrderDialogTempTO>)resultMap.get("orderDialogInfoList");
						
		datasetBeanMapper.beansToDataset(resData, orderDialogList, OrderDialogTempTO.class);
		
	}

	//발주현황조회!
	@RequestMapping(value= "/showOrderInfo")
	public void showOrderInfo(@RequestAttribute("reqData")PlatformData reqData,
			                  @RequestAttribute("resData")PlatformData resData) throws Exception {
		
	
		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
		
		System.out.println("@@@@@@@@@@@@@@@ startDate : " + startDate);
		
		ArrayList<OrderInfoTO> orderInfoList = materialServiceFacade.getOrderInfoList(startDate,endDate);
		
		datasetBeanMapper.beansToDataset(resData, orderInfoList, OrderInfoTO.class);
		
	}
	
	
	//입고 누르고 새창뜰때 바로 실행되어 함수호출
	@RequestMapping(value="/searchOrderInfoListOnDelivery")
	public void searchOrderInfoListOnDelivery(@RequestAttribute("resData")PlatformData resData) throws Exception {
		

		
		// jpa 구현
		ArrayList<OrderInfoTO> orderInfoListOnDelivery = materialServiceFacade.getOrderInfoListOnDelivery();
		
		datasetBeanMapper.beansToDataset(resData, orderInfoListOnDelivery, OrderInfoTO.class);
	}

	
	  //전개된 결과 발주 및 재고처리
	@RequestMapping(value="/order")
	public void order(@RequestAttribute("reqData")PlatformData reqData) throws Exception {
		
		
		String mrpGatheringNoList = reqData.getVariableList().getString("mrpGatheringNoList");  //소요량취합번호 
 
		ArrayList<String> mrpGaNoArr = new ArrayList<>();
		mrpGaNoArr.add(mrpGatheringNoList);
		
		//jpa 미구현 - procedure 호출	 
		materialServiceFacade.order(mrpGaNoArr);
		
	}

	


}
