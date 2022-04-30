package kr.co.seoulit.logistics.production.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.logistics.production.applicationService.MpsApplicationService;
import kr.co.seoulit.logistics.production.applicationService.MrpApplicationService;
import kr.co.seoulit.logistics.production.applicationService.WorkOrderApplicationService;
import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.MrpGatheringTO;
import kr.co.seoulit.logistics.production.to.MrpTO;
import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderSimulationTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ProductionServiceFacadeImpl implements ProductionServiceFacade {


	// 참조변수 선언

	private final MpsApplicationService mpsApplicationService;

	private final MrpApplicationService mrpApplicationService;
	
	private final WorkOrderApplicationService workOrderApplicationService;
	
	@Override
	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {
		return mpsApplicationService.getMpsList(startDate, endDate, includeMrpApply);
	}

	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		return mpsApplicationService.getContractDetailListInMpsAvailable(searchCondition, startDate,endDate);

	}

	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		return mpsApplicationService.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);
	}

	@Override//변환하다.
	public List<MpsTO> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {
		return mpsApplicationService.convertContractDetailToMps(contractDetailInMpsAvailableList);
	}

	@Override
	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList) {
		return mpsApplicationService.convertSalesPlanToMps(contractDetailInMpsAvailableList);
	}

	@Override
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {
		return mpsApplicationService.batchMpsListProcess(mpsTOList);
	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {	
		return mrpApplicationService.searchMrpList(mrpGatheringStatusCondition);
	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {
		return mrpApplicationService.searchMrpList(dateSearchCondtion, startDate, endDate);
	}

	@Override
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {
		return mrpApplicationService.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
	}

	@Override
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {
		return mrpApplicationService.searchMrpGatheringList(dateSearchCondtion, startDate, endDate);
	}

	@Override
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {
		return mrpApplicationService.openMrp(mpsNoArr);
	}
	
	@Override
	public HashMap<String, Object> registerMrp(String mrpRegisterDate, String mpsNo) {
		return mrpApplicationService.registerMrp(mrpRegisterDate, mpsNo);
	}

	@Override
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {
		return mrpApplicationService.batchMrpListProcess(mrpTOList);
	}

	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {
		return mrpApplicationService.getMrpGathering(mrpNoArr);
	}
	
	@Override
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,ArrayList<String> mrpNoArr,HashMap<String, String> mrpNoAndItemCodeMap) {
		return mrpApplicationService.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);
	}
	
	@Override
	public HashMap<String, Object> getWorkOrderableMrpList() {
		return workOrderApplicationService.getWorkOrderableMrpList();
	}
	
	@Override
	public ArrayList<WorkOrderSimulationTO> getWorkOrderSimulationList(ArrayList<String> mrpNo) {
		return workOrderApplicationService.getWorkOrderSimulationList(mrpNo);
	}
	
	@Override
	public HashMap<String,Object> workOrder(ArrayList<String> mrpGatheringNo,String workPlaceCode,String productionProcess,ArrayList<String> mrpNo) {
    	return workOrderApplicationService.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);
	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
		return workOrderApplicationService.getWorkOrderInfoList();
	}

	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {
    	return workOrderApplicationService.workOrderCompletion(workOrderNo,actualCompletionAmount);
	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList(String startDate,String endDate) {
		return workOrderApplicationService.getProductionPerformanceInfoList(startDate,endDate);
	}

	@Override
	public HashMap<String,Object> showWorkSiteSituation(String workSiteCourse,String workOrderNo) {
		return workOrderApplicationService.showWorkSiteSituation(workSiteCourse,workOrderNo);
	}

	@Override
	public void workCompletion(String workOrderNo) {
		workOrderApplicationService.workCompletion(workOrderNo);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		return workOrderApplicationService.workSiteLogList(workSiteLogDate);
	}
}
