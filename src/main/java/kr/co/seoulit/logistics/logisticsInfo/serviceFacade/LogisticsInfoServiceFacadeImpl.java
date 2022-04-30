package kr.co.seoulit.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.logistics.logisticsInfo.applicationService.ItemApplicationService;
import kr.co.seoulit.logistics.logisticsInfo.applicationService.WarehouseApplicationService;
import kr.co.seoulit.logistics.logisticsInfo.mapper.WarehouseDAO;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemInfoTO;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemTO;
import kr.co.seoulit.logistics.logisticsInfo.to.WarehouseTO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {
	
	
	private final ItemApplicationService itemApplicationService;
	
	private final WarehouseApplicationService warehouseApplicationService;
	
	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {
		return itemApplicationService.getItemInfoList(searchCondition, paramArray);
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		return itemApplicationService.batchItemListProcess(itemTOList);
	}

	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList() {
		return warehouseApplicationService.getWarehouseInfoList();
	}

	@Override
	public void modifyWarehouseInfo(WarehouseTO warehouseTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findLastWarehouseCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemTO getStandardUnitPrice(String itemCode) {
		return itemApplicationService.getStandardUnitPrice(itemCode);
		
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {
		return itemApplicationService.getStandardUnitPriceBox(itemCode);
	}

}
