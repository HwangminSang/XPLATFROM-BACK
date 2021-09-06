package kr.co.seoulit.system.base.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.system.base.to.ContractReportTO;
import kr.co.seoulit.system.base.to.EstimateReportTO;

@Mapper
public interface ReportDAO {

	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> selectContractReport(String contractNo);
	
}
