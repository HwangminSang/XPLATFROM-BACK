package kr.co.seoulit.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.production.serviceFacade.ProductionServiceFacade;
import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderSimulationTO;
import kr.co.seoulit.logistics.production.to.WorkOrderableMrpListTO;
import kr.co.seoulit.logistics.production.to.WorkSiteLog;
import kr.co.seoulit.logistics.production.to.WorkSiteSimulationTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/production/*")
public class WorkOrderController{
	// serviceFacade 참조변수 선언

	private final  ProductionServiceFacade productionServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;


	//작업지시 필요목록 조회
	@RequestMapping(value="/getWorkOrderableMrpList")
	public void getWorkOrderableMrpList(@RequestAttribute("resData")PlatformData resData) throws Exception {
		
	
		
		// jpa 미구현 - procedure 호출
		HashMap<String, Object> resultMap = productionServiceFacade.getWorkOrderableMrpList();
		
		@SuppressWarnings("unchecked")
		ArrayList<WorkOrderableMrpListTO> workOderableMrpList = (ArrayList<WorkOrderableMrpListTO>)resultMap.get("gridRowJson");
				
		datasetBeanMapper.beansToDataset(resData, workOderableMrpList, WorkOrderableMrpListTO.class);	
	}
	
	
     //작업지시 시뮬레이션 실행과 동시에 받아오는 값.
	@RequestMapping(value="/showWorkOrderDialog")
	public void showWorkOrderDialog(@RequestAttribute("reqData") PlatformData reqData,@RequestAttribute("resData") PlatformData resData) throws Exception {


	
		String mrpNo = reqData.getVariableList().getString("mrpNo");

	
		ArrayList<String> mrpNoList = new ArrayList<>();
			mrpNoList.add(mrpNo);
	
		// jpa 미구현 - procedure 호출
		ArrayList<WorkOrderSimulationTO> workOrderSimulationList 
					= productionServiceFacade.getWorkOrderSimulationList(mrpNoList);
		
		datasetBeanMapper.beansToDataset(resData, workOrderSimulationList, WorkOrderSimulationTO.class);
		
	}
   
	//전개된 결과 작업지시!!
	@RequestMapping(value="/workOrder")
	public void workOrder(@RequestAttribute("reqData")PlatformData reqData  ) throws Exception {
		
		String workPlaceCode = reqData.getVariable("workPlaceCode").getString();
		String productionProcess = reqData.getVariable("productionProcessCode").getString();	
		String mrpGatheringNo = reqData.getVariableList().getString("mrpGatheringNo");
		String mrpNo = reqData.getVariableList().getString("mrpNo");

		ArrayList<String> mrpGatheringNoList = new ArrayList<>();
			mrpGatheringNoList.add(mrpGatheringNo);
		ArrayList<String> mrpNoList = new ArrayList<>();
			mrpNoList.add(mrpNo);	
		
		// jpa 미구현 - procedure 호출
		HashMap<String,Object> resultMap = productionServiceFacade.workOrder(mrpGatheringNoList, workPlaceCode, productionProcess, mrpNoList);
		
 
	}

	//작업지시 현황조회  , 작업장 현황 조회
	@RequestMapping(value="/showWorkOrderInfoList")
	public void showWorkOrderInfoList(@RequestAttribute("resData")PlatformData resData) throws Exception {

		// jpa 구현 < 작업완료 되지 않은 행만 가져온다 > 
		ArrayList<WorkOrderInfoTO> workOrderInfoList = productionServiceFacade.getWorkOrderInfoList();
		
		datasetBeanMapper.beansToDataset(resData, workOrderInfoList, WorkOrderInfoTO.class);
	}

	
	//작업완료등록 버튼
	@RequestMapping(value="/workOrderCompletion")
	public void workOrderCompletion(@RequestAttribute("reqData")PlatformData reqData,
			                        @RequestAttribute("resData")PlatformData resData) throws Exception {

		
		String workOrderNo = reqData.getVariable("workOrderNo").getString(); //pk
		String actualCompletionAmount = reqData.getVariable("actualCompletionAmount").getString(); //실제완료된수량
				
	    	// jpa 미구현 - procedure 호출
  HashMap<String,Object> map=productionServiceFacade.workOrderCompletion(workOrderNo, actualCompletionAmount);
               //프로시저에서 에러가 났지만 오류문이 생기지않아 프로시저 전용변수를 만들어 view단에 알려줌
                resData.getVariableList().add("ProcedureErrorCode", map.get("errorCode"));
                resData.getVariableList().add("ProcedureErrorMsg", map.get("errorMsg"));
              
	}

	//생산실적조회 
	@RequestMapping(value="/getProductionPerformanceInfoList")
	public void getProductionPerformanceInfoList(@RequestAttribute("reqData") PlatformData reqData,@RequestAttribute("resData") PlatformData resData ) throws Exception {

		String startDate=reqData.getVariable("startDate").getString();
		String endDate=reqData.getVariable("endDate").getString();
		
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = productionServiceFacade.getProductionPerformanceInfoList(startDate,endDate);
		datasetBeanMapper.beansToDataset(resData, productionPerformanceInfoList, ProductionPerformanceInfoTO.class);
	}

	 //작업시뮬레이션시작
	@RequestMapping(value="/showWorkSiteSituation")
	public void showWorkSiteSituation(@RequestAttribute("reqData") PlatformData reqData
			                          ,@RequestAttribute("resData") PlatformData resData ) throws Exception {

		//원재료검사:RawMaterials,제품제작:Production,판매제품검사:SiteExamine
		String workSiteCourse = reqData.getVariable("workSiteCourse").getString();
		//작업지시일련번호
		String workOrderNo = reqData.getVariable("workOrderNo").getString();
	

		// jpa 미구현 - procedure 호출
		HashMap<String, Object> resultMap = productionServiceFacade.showWorkSiteSituation(workSiteCourse,workOrderNo);
		@SuppressWarnings("unchecked")
		ArrayList<WorkSiteSimulationTO> workSiteSimulationTO = (ArrayList<WorkSiteSimulationTO>)resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(resData, workSiteSimulationTO, WorkSiteSimulationTO.class);
		
	}

	//작업장에서 제품제작 
	@RequestMapping(value="/workCompletion")
	public void workCompletion(@RequestAttribute("reqData")PlatformData reqData) throws Exception {

	
		String workOrderNo = reqData.getVariable("workOrderNo").getString();  //pk
		

		// jpa 미구현 - procedure 호출
		productionServiceFacade.workCompletion(workOrderNo);

	}

	
	//작업장로그조회
	@RequestMapping(value="/workSiteLog")
	public void workSiteLogList(HttpServletRequest request) throws Exception {

		PlatformData reqData = (PlatformData)request.getAttribute("reqData");
		PlatformData resData = (PlatformData)request.getAttribute("resData");
		
		String workSiteLogDate = reqData.getVariable("workSiteDate").getString();

		//jpa 미구현 --> 구현 할것
		HashMap<String, Object> resultMap = productionServiceFacade.workSiteLogList(workSiteLogDate);		
		@SuppressWarnings("unchecked")
		ArrayList<WorkSiteLog> WorkSiteLogList = (ArrayList<WorkSiteLog>)resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(resData, WorkSiteLogList, WorkSiteLog.class);
		
	}
	
}
