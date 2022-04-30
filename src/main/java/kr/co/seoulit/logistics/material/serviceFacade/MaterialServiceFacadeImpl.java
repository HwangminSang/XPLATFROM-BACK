package kr.co.seoulit.logistics.material.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.logistics.material.applicationService.BomApplicationService;
import kr.co.seoulit.logistics.material.applicationService.OrderApplicationService;
import kr.co.seoulit.logistics.material.applicationService.StockApplicationService;
import kr.co.seoulit.logistics.material.to.BomDeployTO;
import kr.co.seoulit.logistics.material.to.BomInfoTO;
import kr.co.seoulit.logistics.material.to.BomTO;
import kr.co.seoulit.logistics.material.to.OrderInfoTO;
import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MaterialServiceFacadeImpl implements MaterialServiceFacade {
	// 참조변수 선언

	private final BomApplicationService bomApplicationService;

	private final OrderApplicationService orderApplicationService;

	private final StockApplicationService stockApplicationService;
	
	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode,
			String itemClassificationCondition) {
		return bomApplicationService.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {
		return bomApplicationService.getBomInfoList(parentItemCode);
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		return orderApplicationService.getOrderList(startDate, endDate);
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {
		return bomApplicationService.getAllItemWithBomRegisterAvailable();
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {
		return bomApplicationService.batchBomListProcess(batchBomList);

	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpGatheringNoListStr) {
		return orderApplicationService.getOrderDialogInfo(mrpGatheringNoListStr);

	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {
    	return orderApplicationService.order(mrpGaNoArr);
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		// TODO Auto-generated method stub
    	return orderApplicationService.optionOrder(itemCode, itemAmount);
	}

	@Override
	public ArrayList<StockTO> getStockList() {
		return stockApplicationService.getStockList();
	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate, String endDate) {
		return stockApplicationService.getStockLogList(startDate, endDate);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		return orderApplicationService.getOrderInfoListOnDelivery();
	}

	@Override
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
    	return stockApplicationService.warehousing(orderNoArr);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		return orderApplicationService.getOrderInfoList(startDate,endDate);
	}
	
	@Override
	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr) {
		// TODO Auto-generated method stub
		return orderApplicationService.checkOrderInfo(orderNoArr);
	}
}
