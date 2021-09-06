package kr.co.seoulit.system.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.system.basicInfo.applicationService.CompanyApplicationService;
import kr.co.seoulit.system.basicInfo.applicationService.CustomerApplicationService;
import kr.co.seoulit.system.basicInfo.applicationService.DepartmentApplicationService;
import kr.co.seoulit.system.basicInfo.applicationService.FinancialAccountAssociatesApplicationService;
import kr.co.seoulit.system.basicInfo.applicationService.WorkplaceApplicationService;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;
import kr.co.seoulit.system.basicInfo.to.CustomerTO;
import kr.co.seoulit.system.basicInfo.to.DepartmentTO;
import kr.co.seoulit.system.basicInfo.to.FinancialAccountAssociatesTO;
import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;

@Service
public class BasicInfoServiceFacadeImpl implements BasicInfoServiceFacade{
    // 참조변수 선언
	@Autowired
    private CustomerApplicationService customerAS;
	@Autowired
    private FinancialAccountAssociatesApplicationService associatsAS;
	@Autowired
    private CompanyApplicationService companyAS;
	@Autowired
    private WorkplaceApplicationService workplaceAS;
	@Autowired
    private DepartmentApplicationService deptAS;

    @Override
    public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode) {
        return customerAS.getCustomerList(searchCondition, companyCode, workplaceCode);
    }

    @Override
    public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {
        return customerAS.batchCustomerListProcess(customerList);
    }

    @Override
    public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,String workplaceCode) {
        return associatsAS.getFinancialAccountAssociatesList(searchCondition,workplaceCode);
    }

    @Override
    public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
            ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {
        return associatsAS.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);
    }
    @Override
	public ArrayList<CompanyTO> getCompanyList() {
		return companyAS.getCompanyList();
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {
		return workplaceAS.getWorkplaceList(companyCode);
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode) {
		return deptAS.getDepartmentList(searchCondition, companyCode, workplaceCode);
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {
		return companyAS.batchCompanyListProcess(companyList);
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {
		return workplaceAS.batchWorkplaceListProcess(workplaceList);
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {
		return deptAS.batchDepartmentListProcess(departmentList);
	}

}
