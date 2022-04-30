package kr.co.seoulit.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.logistics.outsourcing.applicationService.OutSourcingApplicationService;
import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class OutSourcingServiceFacadeImpl implements OutSourcingServiceFacade{
		// 참조변수 선언
		
		private final OutSourcingApplicationService outSourcingApplicationService;
		
		@Override
		public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
			// TODO Auto-generated method stub
			return outSourcingApplicationService.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
		}
}
