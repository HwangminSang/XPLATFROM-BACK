package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.logistics.material.to.StockLogTO;
import kr.co.seoulit.logistics.material.to.StockTO;

public interface StockApplicationService {
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr);

}
