package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.material.dao.StockDAO;
import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;

@Component
public class StockApplicationServiceImpl implements StockApplicationService{
		// DAO 참조변수 선언
		@Autowired
		private StockDAO stockDAO;

		@Override
		public ArrayList<StockTO> getStockList() {
			ArrayList<StockTO> stockList = null;
			stockList = stockDAO.selectStockList();
			return stockList;
		}
	
		@Override
		public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {
			HashMap<String, String> map = new HashMap<>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			return stockDAO.selectStockLogList(map);
		}

		@Override
		public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
			HashMap<String, String> map = new HashMap<>();
			String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
			map.put("orderNoList", orderNoList);
			
			stockDAO.warehousing(map);
			
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("errorCode", map.get("ERROR_CODE"));
			resultMap.put("errorMsg", map.get("ERROR_MSG"));
			
        	return resultMap;
		}
		
}
