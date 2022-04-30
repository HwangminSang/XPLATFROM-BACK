package kr.co.seoulit.system.authorityManager.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.system.authorityManager.to.MenuTO;

@Mapper
public interface MenuDAO {

	public ArrayList<MenuTO> selectAllMenuList();
	
}
