package kr.co.seoulit.logistics.outsourcing.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.logistics.outsourcing.serviceFacade.OutSourcingServiceFacade;
import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;

@RestController
@RequestMapping("/outsourcing/*")
public class OutSourcingController{
	// serviceFacade 참조변수 선언
	@Autowired
	private OutSourcingServiceFacade outSourcingServiceFacade;

	@RequestMapping(value="/getOutSourcingList.do", method=RequestMethod.GET)
	public ModelAndView searchOutSourcingList(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String customerCode = request.getParameter("customerCode");
		String itemCode = request.getParameter("itemCode");
		String materialStatus = request.getParameter("materialStatus");
		ArrayList<OutSourcingTO> outSourcingList;
		outSourcingList = outSourcingServiceFacade.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
		map.put("outSourcingList", outSourcingList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}
}
