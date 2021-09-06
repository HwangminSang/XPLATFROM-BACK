package kr.co.seoulit.system.authorityManager.to;

import lombok.Data;

@Data
public class MenuAuthorityTO {
	private String menuCode;
	private String menuName;
	private String authorityGroupCode;
	private String menuLevel;
	private String authority;

	
}
