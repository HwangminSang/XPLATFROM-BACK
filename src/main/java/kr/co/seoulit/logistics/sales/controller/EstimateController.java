package kr.co.seoulit.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.sales.mapper.SalesPlanDAO;
import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/sales/*")
public class EstimateController{
	
	
	private final SalesServiceFacade salesServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;
	
	//견적조회
	@RequestMapping(value="/searchEstimate")
	public void searchEstimateInfo(@RequestAttribute("reqData") PlatformData reqData ,
			                       @RequestAttribute("resData") PlatformData resData) throws Exception {

		                             //name=dateSearchCondition 변수이름으로 찾는다
		String dateSearchCondition = reqData.getVariable("dateSearchCondition").getString();  //value 값을 찾아옴
		String startDate = reqData.getVariable("startDate").getString();
		String endDate = reqData.getVariable("endDate").getString();
					
		ArrayList<EstimateTO> estimateTOList = salesServiceFacade.getEstimateList(dateSearchCondition, startDate, endDate);
		ArrayList<EstimateDetailTO> estimateDetailTOList = new ArrayList<>();
		
		for(EstimateTO estimate : estimateTOList) {
			for(EstimateDetailTO estimateDetailList : estimate.getEstimateDetailTOList()) {
				estimateDetailTOList.add(estimateDetailList);
			}
		}
		
		datasetBeanMapper.beansToDataset(resData, estimateTOList, EstimateTO.class);
		datasetBeanMapper.beansToDataset(resData, estimateDetailTOList, EstimateDetailTO.class);
	}

	
//견적추가
	@RequestMapping(value="/addNewEstimate")
	public void addNewEstimate(@RequestAttribute("reqData") PlatformData reqData ,
			                   @RequestAttribute("resData") PlatformData resData) throws Exception {

	
		EstimateTO newEstimateTO = datasetBeanMapper.datasetToBean(reqData, EstimateTO.class); //dataset을 to객체로
		List<EstimateDetailTO> newEstimateDeatailTO = datasetBeanMapper.datasetToBeans(reqData, EstimateDetailTO.class);

		String estimateDate = newEstimateTO.getEstimateDate();
	
		newEstimateTO.setEstimateDetailTOList(newEstimateDeatailTO); // 일 대 다 관계

		//견적번호와 견적상세번호를 담은 MAP
		HashMap<String, Object> resultList = salesServiceFacade.addNewEstimate(estimateDate, newEstimateTO);
		
		//객체를 beanToDataset에 넣어줘야하기떄문에 새로 만들어서 처리. <견적번호> 를 set해준다.
		EstimateTO estimateTO = new EstimateTO();
		estimateTO.setEstimateNo(resultList.get("newEstimateNo").toString());
		
		
		resData.getVariableList().add("EstimateDtNo", resultList.get("INSERT").toString());
		datasetBeanMapper.beanToDataset(resData, estimateTO, EstimateTO.class);	
		
	}
   
	//일괄저장
	@RequestMapping(value="/batchEstimateDetailListProcess")
	public void batchEstimateDetailListProcess(@RequestAttribute("reqData")PlatformData reqData) throws Exception {

		
		
		
		ArrayList<EstimateDetailTO> estimateDetailList 
				= (ArrayList<EstimateDetailTO>) datasetBeanMapper.datasetToBeans(reqData, EstimateDetailTO.class);
				
		HashMap<String, Object> resultList = salesServiceFacade.batchEstimateDetailListProcess(estimateDetailList);
	
	}

}
