package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.production.dao.WorkOrderDAO;
import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkSiteLog;

@Component
public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {

	// DAO 참조변수 선언
	// private static MpsDAO mpsDAO = MpsDAOImpl.getInstance();
	// private static MrpDAO mrpDAO = MrpDAOImpl.getInstance();
	@Autowired
	private WorkOrderDAO workOrderDAO;

	@Override
	public HashMap<String, Object> getWorkOrderableMrpList() {
		HashMap<String,Object> resultMap = new HashMap<>();
		HashMap<String, String> map = new HashMap<>();
		workOrderDAO.getWorkOrderableMrpList(map);
		resultMap.put("gridRowJson", map.get("RESULT"));
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	public HashMap<String, Object> getWorkOrderSimulationList(String mrpGatheringNo, String mrpNo) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNo);		
		map.put("mrpNo", mrpNo);
		workOrderDAO.getWorkOrderSimulationList(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();			

		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;

	}

	@Override
	public HashMap<String, Object> workOrder(String mrpGatheringNo, String workPlaceCode, String productionProcess,
			String mrpNo) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("workPlaceCode", workPlaceCode);
		map.put("productionProcess", productionProcess);
		map.put("mrpNo", mrpNo);	
		workOrderDAO.workOrder(map);
		HashMap<String,Object> resultMap = new HashMap<>();			

		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;

	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;
		workOrderInfoList = workOrderDAO.selectWorkOrderInfoList();
		return workOrderInfoList;

	}

	@Override
	public HashMap<String, Object> workOrderCompletion(String workOrderNo, String actualCompletionAmount) {
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
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		productionPerformanceInfoList = workOrderDAO.selectProductionPerformanceInfoList();
		return productionPerformanceInfoList;

	}

	@Override
	public HashMap<String, Object> showWorkSiteSituation(String workSiteCourse, String workOrderNo,
			String itemClassIfication) {

		HashMap<String,Object> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);
	  	map.put("workSiteCourse", workSiteCourse);
	  	map.put("itemClassIfication", itemClassIfication);
		workOrderDAO.selectWorkSiteSituation(map);
		HashMap<String,Object> resultMap = new HashMap<>();
	  	resultMap.put("gridRowJson",map.get("RESULT"));
	  	resultMap.put("errorCode",map.get("ERROR_CODE"));
	  	resultMap.put("errorMsg",map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	public void workCompletion(String workOrderNo, String itemCode, ArrayList<String> itemCodeListArr) {
		String itemCodeList = itemCodeListArr.toString().replace("[", "").replace("]", "");
		
		HashMap<String,String> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);
	  	map.put("itemCode", itemCode);
	  	map.put("itemCodeList", itemCodeList);
		
		workOrderDAO.updateWorkCompletionStatus(map);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		ArrayList<WorkSiteLog> list = workOrderDAO.workSiteLogList(workSiteLogDate);
		HashMap<String,Object> resultMap = new HashMap<>();
   	  	resultMap.put("gridRowJson",list);
		return resultMap;
	}

}
