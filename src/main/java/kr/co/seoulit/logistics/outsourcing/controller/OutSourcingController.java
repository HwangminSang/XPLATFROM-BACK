package kr.co.seoulit.logistics.outsourcing.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.logistics.outsourcing.mapper.OutSourcingDAO;
import kr.co.seoulit.logistics.outsourcing.serviceFacade.OutSourcingServiceFacade;
import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/outsourcing/*")
public class OutSourcingController{
	// serviceFacade 참조변수 선언
	
	private final OutSourcingServiceFacade outSourcingServiceFacade;
	
	private final DatasetBeanMapper datasetBeanMapper;

	@RequestMapping(value="/getOutSourcingList")
	public void getOutSourcingList(@RequestAttribute("reqData")PlatformData reqData,@RequestAttribute("resData")PlatformData resData) throws Exception {
		

		String fromDate = reqData.getVariable("instructDate").getString();
		String toDate = reqData.getVariable("completeDate").getString();
		String materialStatus = reqData.getVariable("materialStatus").getString();
		String customerCode = "";		
		String itemCode = ""; 			

		// jpa 미구현 - join 구문
		ArrayList<OutSourcingTO> outSourcingList;
			outSourcingList = outSourcingServiceFacade.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

		datasetBeanMapper.beansToDataset(resData, outSourcingList, OutSourcingTO.class);
	}
}
