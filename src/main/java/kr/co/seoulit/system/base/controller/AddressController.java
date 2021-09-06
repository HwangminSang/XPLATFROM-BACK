package kr.co.seoulit.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import kr.co.seoulit.system.base.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.system.base.to.AddressTO;

@RestController
@RequestMapping("/base/*")
public class AddressController{

	// serviceFacade 참조변수 선언
	@Autowired
	private BaseServiceFacade baseSF;

	@RequestMapping(value="/searchAddressList.do", method=RequestMethod.POST)
	public ModelAndView searchAddressList(HttpServletRequest request) {

		String sidoName = request.getParameter("sidoName");
		String searchAddressType = request.getParameter("searchAddressType");
		String searchValue = request.getParameter("searchValue");
		String mainNumber = request.getParameter("mainNumber");
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<AddressTO> addressList = baseSF.getAddressList(sidoName, searchAddressType, searchValue,
				mainNumber);
		map.put("addressList", addressList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);

	}

}
