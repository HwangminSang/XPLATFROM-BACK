package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;
public interface MpsApplicationService {

	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply);
	
	public ArrayList<ContractDetailInMpsAvailableTO> 
		getContractDetailListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public ArrayList<SalesPlanInMpsAvailableTO> 
		getSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public List<MpsTO> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList);

	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList);
	
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList);

	
	// applicationService 내부 메서드
	public String getNewMpsNo(String mpsPlanDate);
	
	// applicationService 내부 메서드	
	public void changeMpsStatusInContractDetail(String mpsStatus, String contractDetailNo);
	
	// applicationService 내부 메서드
	public void changeMpsStatusInSalesPlan(String mpsStatus, String salesPlanNo);
}
