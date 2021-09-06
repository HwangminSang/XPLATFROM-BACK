package kr.co.seoulit.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;
public interface OutSourcingServiceFacade {
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate,String toDate,String customerCode,String itemCode,String materialStatus);
}
