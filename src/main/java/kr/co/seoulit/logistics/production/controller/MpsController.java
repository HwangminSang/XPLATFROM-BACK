package kr.co.seoulit.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import kr.co.seoulit.logistics.production.mapper.WorkOrderDAO;
import kr.co.seoulit.logistics.production.repository.WorkOrderInfoRepository;
import kr.co.seoulit.logistics.production.serviceFacade.ProductionServiceFacade;
import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/production/*")
public class MpsController{
	// serviceFacade 참조변수 선언
	
	private final ProductionServiceFacade productionServiceFacade;
	
	private final DatasetBeanMapper datasetBeanMapper;
	


	
	//mps조회
	@RequestMapping(value="/searchMpsInfo")
	public void searchMpsInfo(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData )throws Exception {


		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
		String includeMrpApply = reqData.getVariable("includeMrpApply").getString();
		// 포함 = includeMrpApply, 미포함 = excludeMrpApply;
		
		ArrayList<MpsTO> mpsTOList = productionServiceFacade.getMpsList(startDate, endDate, includeMrpApply);
		
		datasetBeanMapper.beansToDataset(resData, mpsTOList, MpsTO.class);

	}
	
	
  //mps가능 수주리스트조회
	@RequestMapping(value="/searchContractDetailListInMpsAvailable")
	public void searchContractDetailListInMpsAvailable(@RequestAttribute("reqData") PlatformData reqData,
                                                       @RequestAttribute("resData") PlatformData resData) throws Exception {
		
	
		String searchCondition = reqData.getVariable("searchCondition").getString();
		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
				
		// join 구문으로  JPA 미적용
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionServiceFacade
				.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
												   //contractDate, 2019-07-01, 2019-07-31
				
		datasetBeanMapper.beansToDataset(resData, contractDetailInMpsAvailableList, ContractDetailInMpsAvailableTO.class);
		
		}



	
	//주생산계획등록
	@RequestMapping(value="/convertContractDetailToMps")
	public void convertContractDetailToMps(@RequestAttribute("reqData") PlatformData reqData,
			                               @RequestAttribute("resData") PlatformData resData) throws Exception {

		// jpa 구현
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList 
			= (ArrayList<ContractDetailInMpsAvailableTO>)datasetBeanMapper.datasetToBeans(reqData, ContractDetailInMpsAvailableTO.class);		
		
		List<MpsTO> mpsNoList=productionServiceFacade.convertContractDetailToMps(contractDetailInMpsAvailableList);
     
		datasetBeanMapper.beansToDataset(resData, mpsNoList, MpsTO.class);
		
		
	}
	
}
