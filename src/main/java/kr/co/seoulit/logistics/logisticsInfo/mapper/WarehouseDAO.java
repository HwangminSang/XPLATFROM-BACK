package kr.co.seoulit.logistics.logisticsInfo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.logisticsInfo.to.WarehouseTO;

@Mapper
public interface WarehouseDAO {
	public ArrayList<WarehouseTO> selectWarehouseList();
}
