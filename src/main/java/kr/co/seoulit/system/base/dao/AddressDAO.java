package kr.co.seoulit.system.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.system.base.to.AddressTO;

@Mapper
public interface AddressDAO {

	public AddressTO selectSidoCode(String sidoName);
	
	public ArrayList<AddressTO> selectRoadNameAddressList(HashMap<String, String> map);
	
	public ArrayList<AddressTO> selectJibunAddressList(HashMap<String, String> map);

	
}
