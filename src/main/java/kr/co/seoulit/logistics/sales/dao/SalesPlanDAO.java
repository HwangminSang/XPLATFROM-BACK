package kr.co.seoulit.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;
import kr.co.seoulit.logistics.sales.to.SalesPlanTO;

@Mapper
public interface SalesPlanDAO {
	public ArrayList<SalesPlanTO> selectSalesPlanList(HashMap<String, String> map);
			
	public int selectSalesPlanCount(String salesPlanDate);
	
	public ArrayList<SalesPlanInMpsAvailableTO>
		selectSalesPlanListInMpsAvailable(HashMap<String, String> map);
	
	public void insertSalesPlan(SalesPlanTO TO);

	public void updateSalesPlan(SalesPlanTO TO);
	
	public void changeMpsStatusOfSalesPlan(HashMap<String, String> map);	
	
	public void deleteSalesPlan(SalesPlanTO TO);
	
}
