package kr.co.seoulit.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.logistics.logisticsInfo.to.WarehouseTO;


public interface WarehouseApplicationService {
	
	public ArrayList<WarehouseTO> getWarehouseInfoList();

	public void modifyWarehouseInfo(WarehouseTO warehouseTO);

	public String findLastWarehouseCode();
}
