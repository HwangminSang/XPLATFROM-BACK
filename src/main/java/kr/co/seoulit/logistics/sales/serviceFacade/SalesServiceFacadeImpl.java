package kr.co.seoulit.logistics.sales.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.logistics.sales.applicationService.ContractApplicationService;
import kr.co.seoulit.logistics.sales.applicationService.DeliveryApplicationService;
import kr.co.seoulit.logistics.sales.applicationService.EstimateApplicationService;
import kr.co.seoulit.logistics.sales.applicationService.SalesPlanApplicationService;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.DeliveryInfoTO;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import kr.co.seoulit.logistics.sales.to.SalesPlanTO;

@Service
public class SalesServiceFacadeImpl implements SalesServiceFacade {

	// 참조변수 선언
	@Autowired
	private EstimateApplicationService estimateAS;
	@Autowired
	private ContractApplicationService contractAS;
	@Autowired
	private SalesPlanApplicationService salesPlanAS;
	@Autowired
	private DeliveryApplicationService deliveryAS;
	
	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {
		return estimateAS.getEstimateList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {
		return estimateAS.getEstimateDetailList(estimateNo);
	}

	@Override
	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {
		return estimateAS.addNewEstimate(estimateDate, newEstimateTO);
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo) {
		return estimateAS.batchEstimateDetailListProcess(estimateDetailTOList,estimateNo);
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {
		return contractAS.getContractList(searchCondition, paramArray);
	}

	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(String searchCondition, String[] paramArray) {
		return contractAS.getDeliverableContractList(searchCondition, paramArray);
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {
		return contractAS.getContractDetailList(estimateNo);
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
		return contractAS.getEstimateListInContractAvailable(startDate, endDate);
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractTO) {
		return contractAS.addNewContract(contractDate, personCodeInCharge, workingContractTO);
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {
		return contractAS.batchContractDetailListProcess(contractDetailTOList);
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
		contractAS.changeContractStatusInEstimate(estimateNo, contractStatus);
	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {
		return salesPlanAS.getSalesPlanList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {
		return salesPlanAS.batchSalesPlanListProcess(salesPlanTOList);
	}

	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList() {
		return deliveryAS.getDeliveryInfoList();
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {
		return deliveryAS.batchDeliveryListProcess(deliveryTOList);
	}

	@Override
	public HashMap<String, Object> deliver(String contractDetailNo) {
		return deliveryAS.deliver(contractDetailNo);
	}
	
}
