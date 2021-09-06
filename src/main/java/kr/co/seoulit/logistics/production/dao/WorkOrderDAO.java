package kr.co.seoulit.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkSiteLog;

@Mapper
public interface WorkOrderDAO {

	public HashMap<String,Object> getWorkOrderableMrpList(HashMap<String, String> map);
	
	public HashMap<String,Object> getWorkOrderSimulationList(HashMap<String, String> map);	
	
	public HashMap<String,Object> workOrder(HashMap<String, String> map);
	
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList();
	
	public HashMap<String,Object> workOrderCompletion(HashMap<String, String> map);
	
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList();
	
	public HashMap<String,Object> selectWorkSiteSituation(HashMap<String, Object> map);
	
	public void updateWorkCompletionStatus(HashMap<String,String> map);
	
	public ArrayList<WorkSiteLog> workSiteLogList(String workSiteLogDate);
	
}
