package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;

public interface WorkOrderApplicationService {

	public HashMap<String,Object> getWorkOrderableMrpList();

	public HashMap<String,Object> getWorkOrderSimulationList(String mrpGatheringNo,String mrpNo);	
	
	public HashMap<String,Object> workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo);
	
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList();
	
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList();

	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount);
	
	public HashMap<String,Object> showWorkSiteSituation(String workSiteCourse,String workOrderNo, String itemClassIfication);
	
	public void workCompletion(String workOrderNo,String itemCode , ArrayList<String> itemCodeListArr);
	
	public HashMap<String,Object> workSiteLogList(String workSiteLogDate);
	
} 
 