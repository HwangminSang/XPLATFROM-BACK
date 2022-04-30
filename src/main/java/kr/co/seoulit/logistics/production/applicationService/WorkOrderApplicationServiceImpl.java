package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.production.mapper.MrpDAO;
import kr.co.seoulit.logistics.production.mapper.MrpGatheringDAO;
import kr.co.seoulit.logistics.production.mapper.WorkOrderDAO;
import kr.co.seoulit.logistics.production.repository.MpsRepository;
import kr.co.seoulit.logistics.production.repository.MrpGatheringRepository;
import kr.co.seoulit.logistics.production.repository.MrpRepository;
import kr.co.seoulit.logistics.production.repository.WorkOrderInfoRepository;
import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderSimulationTO;
import kr.co.seoulit.logistics.production.to.WorkSiteLog;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {

	
	
	private final WorkOrderDAO workOrderDAO;

	private final WorkOrderInfoRepository workOrderInfoRepository;

	@Override
	public HashMap<String, Object> getWorkOrderableMrpList() {
		
		// jpa 미구현 - procedure 호출
		HashMap<String,Object> resultMap = new HashMap<>();
		HashMap<String, String> map = new HashMap<>();
		
		workOrderDAO.getWorkOrderableMrpList(map);
		resultMap.put("gridRowJson", map.get("RESULT"));
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<WorkOrderSimulationTO> getWorkOrderSimulationList( ArrayList<String> mrpNo) {

	
		String mrpNoList = mrpNo.toString().replace("[", "").replace("]", "");
		
		// jpa 미구현 - procedure 호출
		HashMap<String, Object> map = new HashMap<>();	
			map.put("mrpNo", mrpNoList);
		
		workOrderDAO.getWorkOrderSimulationList(map);
		
		ArrayList<WorkOrderSimulationTO> workSiteSimulationTOList 
				= (ArrayList<WorkOrderSimulationTO>)map.get("RESULT");
	    
		return workSiteSimulationTOList;

	}

	@Override //현재 모의전개된 결과 작업지시
	public HashMap<String, Object> workOrder(ArrayList<String> mrpGatheringNo, String workPlaceCode, String productionProcess,
			ArrayList<String> mrpNo) {
		
		String mrpGatheringNoList = mrpGatheringNo.toString().replace("[", "").replace("]", "");
		String mrpNoList = mrpNo.toString().replace("[", "").replace("]", "");
		
		// jpa 미구현 - procedure 호출
		HashMap<String, Object> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNoList);
		map.put("workPlaceCode", workPlaceCode);
		map.put("productionProcess", productionProcess);
		map.put("mrpNo", mrpNoList);	
		
		workOrderDAO.workOrder(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();			
		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
	    
		return resultMap;

	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
		
		// jpa 구현
	
		//WORK_ORDER_INFO 작업지시후 만들어지는 테이블 < 작업완료 되지 않은 행만 가져온다 > 
		List<WorkOrderInfoTO> workOrderInfoList = workOrderInfoRepository.findByOperationCompletedIsNull();
		return new ArrayList<>(workOrderInfoList);
	}

	@Override
	public HashMap<String, Object> workOrderCompletion(String workOrderNo, String actualCompletionAmount) {
		
		// jpa 미구현 - procedure 호출
		HashMap<String, String> map = new HashMap<>();
		map.put("workOrderNo", workOrderNo);
		map.put("actualCompletionAmount", actualCompletionAmount);

		workOrderDAO.workOrderCompletion(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList(String startDate,String endDate) {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		HashMap<String,String> map=new HashMap<>();
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		productionPerformanceInfoList = workOrderDAO.selectProductionPerformanceInfoList(map);
		return productionPerformanceInfoList;

	}

	@Override //작업장에서 제품제작 클릭시 
	public HashMap<String, Object> showWorkSiteSituation(String workSiteCourse, String workOrderNo) {

		// jpa 미구현 - procedure 호출
		HashMap<String,Object> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);
	  	map.put("workSiteCourse", workSiteCourse);
		workOrderDAO.selectWorkSiteSituation(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();
	  	resultMap.put("gridRowJson",map.get("RESULT"));
	  	resultMap.put("errorCode",map.get("ERROR_CODE"));
	  	resultMap.put("errorMsg",map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override// 제품제작
	public void workCompletion(String workOrderNo) {

		// jpa 미구현 - procedure 호출
	
		HashMap<String,String> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);

		
		workOrderDAO.updateWorkCompletionStatus(map);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		
		//jpa 구현할것
		ArrayList<WorkSiteLog> list = workOrderDAO.workSiteLogList(workSiteLogDate);
		HashMap<String,Object> resultMap = new HashMap<>();
   	  	resultMap.put("gridRowJson",list);
		return resultMap;
	}

}
