package kr.co.seoulit.system.base.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.system.base.to.ContractReportTO;
import kr.co.seoulit.system.base.to.EstimateReportTO;


public interface ReportApplicationService {

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
	
}
