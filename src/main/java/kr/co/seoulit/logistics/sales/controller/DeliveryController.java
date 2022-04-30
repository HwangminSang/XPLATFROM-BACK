package kr.co.seoulit.logistics.sales.controller;

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
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.DeliveryInfoTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/sales/*")
public class DeliveryController{
	// serviceFacade 참조변수 선언

	private final  SalesServiceFacade salesServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;

	//납품현황조회
	@RequestMapping(value="/searchDeliveryInfoList")
	public void searchDeliveryInfoList(@RequestAttribute("resData")PlatformData resData,
			                            @RequestAttribute("reqData")PlatformData reqData) throws Exception {

		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", reqData.getVariable("searchCondition").getString());
		map.put("startDate",  reqData.getVariable("startDate").getString());
		map.put("endDate", reqData.getVariable("endDate").getString());
		map.put("customerCode", reqData.getVariable("customerCode").getString());
		
			
		ArrayList<DeliveryInfoTO> deliveryInfoList = salesServiceFacade.getDeliveryInfoList(map);
		datasetBeanMapper.beansToDataset(resData, deliveryInfoList, DeliveryInfoTO.class);
	}





	//납품가능 수주조회
	@RequestMapping(value="/searchDeliverableContractList")
	public void searchDeliverableContractList(@RequestAttribute("reqData")PlatformData reqData,
			                                 @RequestAttribute("resData")PlatformData resData) throws Exception {

	
		// jpa 미구현 - join구문
		HashMap<String, String> map = new HashMap<>();
			map.put("searchCondition", reqData.getVariable("searchCondition").getString());
			map.put("startDate",  reqData.getVariable("startDate").getString());
			map.put("endDate", reqData.getVariable("endDate").getString());
			map.put("customerCode", reqData.getVariable("customerCode").getString());
		
		ArrayList<ContractInfoTO> deliverableContractList = salesServiceFacade.getDeliverableContractList(map);
				
		
		
		
		ArrayList<ContractDetailTO> deliverableContractDetailList = new ArrayList<>();
		for(ContractInfoTO contract : deliverableContractList) {
			for(ContractDetailTO contractDetailTO : contract.getContractDetailTOList()) {
				deliverableContractDetailList.add(contractDetailTO);
			}
		}
		
		datasetBeanMapper.beansToDataset(resData, deliverableContractList, ContractInfoTO.class);
		datasetBeanMapper.beansToDataset(resData, deliverableContractDetailList, ContractDetailTO.class);

	}

	@RequestMapping(value="/deliver") //납품
	public void deliver(@RequestAttribute("reqData")PlatformData reqData,@RequestAttribute("resData")PlatformData resData) throws Exception {

		
		String contractDetailNo = reqData.getVariable("contractDetailNo").getString();
		
		//jpa 미구현 - procedure 호출
		HashMap<String,Object> resultMap = salesServiceFacade.deliver(contractDetailNo);
		
		resData.getVariableList().add("g_procedureMsg",resultMap.get("errorMsg"));
			
	}
	
	

}
