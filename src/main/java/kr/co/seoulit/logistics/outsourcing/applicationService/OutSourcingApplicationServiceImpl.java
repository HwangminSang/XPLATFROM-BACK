package kr.co.seoulit.logistics.outsourcing.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.outsourcing.dao.OutSourcingDAO;
import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;

@Component
public class OutSourcingApplicationServiceImpl implements OutSourcingApplicationService{
	//dao참조변수 선언
	@Autowired
	private OutSourcingDAO outSourcingDAO;
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		// TODO Auto-generated method stub
		HashMap<String,String> params = new HashMap<>();
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		params.put("customerCode", customerCode);
		params.put("itemCode", itemCode);
		params.put("materialStatus", materialStatus);
		return outSourcingDAO.selectOutSourcingList(params);
	}
}
