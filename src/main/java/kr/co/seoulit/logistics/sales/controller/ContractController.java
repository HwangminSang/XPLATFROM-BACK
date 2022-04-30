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
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/sales/*")
public class ContractController{
	// 생성자 주입 < 순환참조 방지 > 
	
	private final SalesServiceFacade salesServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;

	//수주 검색
	@RequestMapping(value= "/searchContract")
	public void searchContract(@RequestAttribute("reqData")PlatformData reqData,
			                   @RequestAttribute("resData")PlatformData resData)throws Exception {

		String searchCondition = reqData.getVariableList().getString("searchCondition");
		String startDate = reqData.getVariableList().getString("firstDate");
		String endDate = reqData.getVariableList().getString("endDate");
		String customerCode =reqData.getVariableList().getString("customerCode");

		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition",searchCondition);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("customerCode",customerCode);

	
		ArrayList<ContractInfoTO> contractInfoTOList = salesServiceFacade.getContractListByCondition(map);
		
		
		
		List<ContractDetailTO> contractDetailList = new ArrayList<>();
		
		for(ContractInfoTO contractInfoTO : contractInfoTOList) {
			for(ContractDetailTO contractDetailTO : contractInfoTO.getContractDetailTOList()) {
				contractDetailList.add(contractDetailTO);
			}
		}
		
		datasetBeanMapper.beansToDataset(resData, contractInfoTOList, ContractInfoTO.class);
		datasetBeanMapper.beansToDataset(resData, contractDetailList, ContractDetailTO.class);
		
		
	}



	//수주가능견적조회
	@RequestMapping(value= "/searchEstimateInContractAvailable")
	public void searchEstimateInContractAvailable(@RequestAttribute("reqData")PlatformData reqData,
			                                      @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
	
		ArrayList<EstimateTO> estimateListInContractAvailable = 
				salesServiceFacade.getEstimateListInContractAvailable(startDate, endDate);
		
		List<EstimateDetailTO> estimateDetailList = new ArrayList<>();
		
		for(EstimateTO estimateTO : estimateListInContractAvailable) {
			for(EstimateDetailTO estimateDetailTO : estimateTO.getEstimateDetailTOList()) {
				estimateDetailList.add(estimateDetailTO);
			}
		}
		
		datasetBeanMapper.beansToDataset(resData, estimateListInContractAvailable, EstimateTO.class);
		datasetBeanMapper.beansToDataset(resData, estimateDetailList, EstimateDetailTO.class);
		
	}

	 //수주등록
	@RequestMapping(value="/addNewContract")
	public void addNewContract(@RequestAttribute("reqData")PlatformData reqData,
		                    	@RequestAttribute("resData")PlatformData resData) throws Exception {

		String contractDate = reqData.getVariable("contractDate").getString();
		String personCodeInCharge = reqData.getVariable("personCodeInCharge").getString();
		
		ContractTO workingContractTO = datasetBeanMapper.datasetToBean(reqData, ContractTO.class); //수주
		List<ContractDetailTO> contractDetailList = datasetBeanMapper.datasetToBeans(reqData, ContractDetailTO.class); //수주상세
		//일 대 다 관계.
	   workingContractTO.setContractDetailTOList(contractDetailList);
	
		
		HashMap<String,Object> map=salesServiceFacade.addNewContract(contractDate, personCodeInCharge, workingContractTO);
		    
		  resData.getVariableList().add("g_procedureCode", map.get("errorCode"));
		  resData.getVariableList().add("g_procedureMsg", map.get("errorMsg"));
					
	}
	
	//수주취소
	@RequestMapping(value= "/cancelEstimate")
	public void cancleEstimate(@RequestAttribute("reqData")PlatformData reqData) throws Exception {

		
		String estimateNo = reqData.getVariable("estimateNo").getString();
		
		salesServiceFacade.changeContractStatusInEstimate(estimateNo, "N");

		
		
	}
	
	
}
