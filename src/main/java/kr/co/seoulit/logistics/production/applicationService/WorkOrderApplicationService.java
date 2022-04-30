package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderSimulationTO;

public interface WorkOrderApplicationService {

	public HashMap<String,Object> getWorkOrderableMrpList();

	public ArrayList<WorkOrderSimulationTO> getWorkOrderSimulationList(ArrayList<String> mrpNo);	
	
	public HashMap<String,Object> workOrder(ArrayList<String> mrpGatheringNo,String workPlaceCode,String productionProcess,ArrayList<String> mrpNo);
	
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList();
	
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList(String startDate,String endDate);

	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount);
	
	public HashMap<String,Object> showWorkSiteSituation(String workSiteCourse,String workOrderNo);
	
	public void workCompletion(String workOrderNo);
	
	public HashMap<String,Object> workSiteLogList(String workSiteLogDate);
	
} 
 