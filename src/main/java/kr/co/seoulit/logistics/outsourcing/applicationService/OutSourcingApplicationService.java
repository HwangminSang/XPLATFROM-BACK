package kr.co.seoulit.logistics.outsourcing.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;


public interface OutSourcingApplicationService {

	ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus);

}
