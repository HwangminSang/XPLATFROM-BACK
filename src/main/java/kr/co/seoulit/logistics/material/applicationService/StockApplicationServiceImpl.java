package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.material.mapper.OrderDAO;
import kr.co.seoulit.logistics.material.mapper.StockDAO;
import kr.co.seoulit.logistics.material.repository.OrderRepository;
import kr.co.seoulit.logistics.material.repository.StockRepository;
import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@Component
public class StockApplicationServiceImpl implements StockApplicationService{
		// DAO 참조변수 선언
	
		private final StockDAO stockDAO;
	
		private final  StockRepository stockRepository;

		@Override
		public ArrayList<StockTO> getStockList() {
			
		    //jpa 구현 
		
			List<StockTO> stockList = stockRepository.findAll(Sort.by(Sort.Direction.ASC, "itemCode"));
			return new ArrayList<>(stockList);
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
			
			//jpa 미구현 - procedure 호출  -- 발주진행상태 : 운송중 --> 입고완료
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
