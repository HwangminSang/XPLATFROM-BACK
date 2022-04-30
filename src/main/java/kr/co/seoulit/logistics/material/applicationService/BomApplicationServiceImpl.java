package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.logisticsInfo.applicationService.ItemApplicationService;
import kr.co.seoulit.logistics.logisticsInfo.applicationService.WarehouseApplicationService;
import kr.co.seoulit.logistics.material.mapper.BomDAO;
import kr.co.seoulit.logistics.material.to.BomDeployTO;
import kr.co.seoulit.logistics.material.to.BomInfoTO;
import kr.co.seoulit.logistics.material.to.BomTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class BomApplicationServiceImpl implements BomApplicationService {

	// DAO 참조변수 선언
	
	private final BomDAO bomDAO;
	
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {
		HashMap<String, String> map = new HashMap<>();
		map.put("deployCondition" , deployCondition);
		map.put("itemCode" , itemCode);
		map.put("itemClassificationCondition" , itemClassificationCondition);
		return bomDAO.selectBomDeployList(map);
	}
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		ArrayList<BomInfoTO> bomInfoList = null;
		bomInfoList = bomDAO.selectBomInfoList(parentItemCode);
		return bomInfoList;
	}

	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {
		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = null;
		allItemWithBomRegisterAvailable = bomDAO.selectAllItemWithBomRegisterAvailable();
		return allItemWithBomRegisterAvailable;
	}

	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		int deleteCount = 0;

		for (BomTO TO : batchBomList) {

			String status = TO.getStatus();

			switch (status) {

			case "INSERT":

				bomDAO.insertBom(TO);

				insertCount++;

				break;

			case "UPDATE":

				bomDAO.updateBom(TO);

				updateCount++;

				break;

			case "DELETE":

				bomDAO.deleteBom(TO);

				deleteCount++;

				break;

			}

		}

		resultMap.put("INSERT", insertCount);
		resultMap.put("UPDATE", updateCount);
		resultMap.put("DELETE", deleteCount);
		return resultMap;
	}

}
