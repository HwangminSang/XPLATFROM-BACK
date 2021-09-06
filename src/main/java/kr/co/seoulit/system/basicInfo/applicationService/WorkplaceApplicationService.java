package kr.co.seoulit.system.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;

public interface WorkplaceApplicationService {
	
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode);
	
	public String getNewWorkplaceCode(String companyCode);
	
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList);
	
}
