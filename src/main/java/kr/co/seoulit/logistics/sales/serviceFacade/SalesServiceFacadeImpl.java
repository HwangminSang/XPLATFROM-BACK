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
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class SalesServiceFacadeImpl implements SalesServiceFacade {

	// 참조변수 선언

	private final EstimateApplicationService estimateApplicationService;

	private final ContractApplicationService contractApplicationService;
	
	private final SalesPlanApplicationService salesPlanApplicationService;
	
	private final DeliveryApplicationService deliveryApplicationService;
	
	@Override //견적조회
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {
		return estimateApplicationService.getEstimateList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {
		return estimateApplicationService.getEstimateDetailList(estimateNo);
	}

	@Override
	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {
		return estimateApplicationService.addNewEstimate(estimateDate, newEstimateTO);
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList) {
		return estimateApplicationService.batchEstimateDetailListProcess(estimateDetailTOList);
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {
		return contractApplicationService.getContractList(searchCondition, paramArray);
	}

	@Override
	public ArrayList<ContractInfoTO> getContractListByCondition(HashMap<String, String> map) {
		return contractApplicationService.getContractListByCondition(map);
	}
	
	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> map) {
		return contractApplicationService.getDeliverableContractList(map);
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {
		return contractApplicationService.getContractDetailList(estimateNo);
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
		return contractApplicationService.getEstimateListInContractAvailable(startDate, endDate);
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractTO) {
		return contractApplicationService.addNewContract(contractDate, personCodeInCharge, workingContractTO);
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {
		return contractApplicationService.batchContractDetailListProcess(contractDetailTOList);
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
		contractApplicationService.changeContractStatusInEstimate(estimateNo, contractStatus);
	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {
		return salesPlanApplicationService.getSalesPlanList(dateSearchCondition, startDate, endDate);
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {
		return salesPlanApplicationService.batchSalesPlanListProcess(salesPlanTOList);
	}

	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList(HashMap<String, String> map) {
		return deliveryApplicationService.getDeliveryInfoList(map);
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {
		return deliveryApplicationService.batchDeliveryListProcess(deliveryTOList);
	}

	@Override
	public HashMap<String, Object> deliver(String contractDetailNo) {
		return deliveryApplicationService.deliver(contractDetailNo);
	}
	
}
