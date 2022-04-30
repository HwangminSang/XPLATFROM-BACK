package kr.co.seoulit.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.logisticsInfo.mapper.ItemDAO;
import kr.co.seoulit.logistics.logisticsInfo.mapper.WarehouseDAO;
import kr.co.seoulit.logistics.logisticsInfo.repository.ItemRepository;
import kr.co.seoulit.logistics.logisticsInfo.to.WarehouseTO;
import kr.co.seoulit.logistics.material.mapper.BomDAO;
import kr.co.seoulit.system.base.mapper.CodeDetailDAO;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService{


	private final WarehouseDAO warehouseDAO;
	
	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList(){
		ArrayList<WarehouseTO> warehouseList = null;
		warehouseList = warehouseDAO.selectWarehouseList();
		return warehouseList;
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
}
