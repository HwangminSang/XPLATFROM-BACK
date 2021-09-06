package kr.co.seoulit.system.authorityManager.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.system.authorityManager.to.MenuAuthorityTO;

public interface MenuApplicationService {
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);
	
	public String[] getAllMenuList();
	
}
