package kr.co.seoulit.logistics.logisticsInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.seoulit.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import kr.co.seoulit.logistics.logisticsInfo.to.WarehouseTO;

@RestController
@RequestMapping("/logisticsInfo/*")
public class WarehouseController{
	// serviceFacade 참조변수 선언
	@Autowired
	private LogisticsInfoServiceFacade logisticsSF;
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value = "/warehouseInfo.do", method = RequestMethod.GET)
	public ModelAndView getWarehouseList() {
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<WarehouseTO> WarehouseTOList = logisticsSF.getWarehouseInfoList();
		map.put("gridRowJson", WarehouseTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	/*
	 * public ModelAndView modifyWarehouseInfo(HttpServletRequest request,
	 * HttpServletResponse response) { String batchList =
	 * request.getParameter("batchList"); HashMap<String, Object> map = new
	 * HashMap<>(); PrintWriter out = null; try { out = response.getWriter();
	 * WarehouseTO WarehouseTO = gson.fromJson(batchList, WarehouseTO.class);
	 * logisticsSF.modifyWarehouseInfo(WarehouseTO); map.put("errorCode", 1);
	 * map.put("errorMsg", "성공"); } catch (IOException e1) { e1.printStackTrace();
	 * map.put("errorCode", -1); map.put("errorMsg", e1.getMessage()); } catch
	 * (DataAccessException e2) { e2.printStackTrace(); map.put("errorCode", -2);
	 * map.put("errorMsg", e2.getMessage()); } finally {
	 * out.println(gson.toJson(map)); out.close(); } return null; }
	 * 
	 * 
	 * public ModelAndView findLastWarehouseCode(HttpServletRequest request,
	 * HttpServletResponse response){ HashMap<String, Object> map = new HashMap<>();
	 * try { String warehouseCode = logisticsSF.findLastWarehouseCode();
	 * map.put("lastWarehouseCode", warehouseCode); map.put("errorCode", 1);
	 * map.put("errorMsg", "성공"); } catch (DataAccessException e2) {
	 * e2.printStackTrace(); map.put("errorCode", -2); map.put("errorMsg",
	 * e2.getMessage()); } return new ModelAndView("jsonView",map); }
	 */
	

}
