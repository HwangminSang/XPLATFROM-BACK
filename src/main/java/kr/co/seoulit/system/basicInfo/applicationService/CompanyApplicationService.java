package kr.co.seoulit.system.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.system.basicInfo.to.CompanyTO;

public interface CompanyApplicationService {

	public ArrayList<CompanyTO> getCompanyList();
	
	public String getNewCompanyCode();
	
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList);
	
	
	
}
