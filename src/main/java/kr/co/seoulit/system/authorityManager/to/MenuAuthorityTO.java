package kr.co.seoulit.system.authorityManager.to;

import kr.co.seoulit.system.common.annotation.ColumnName;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_myMenu")
public class MenuAuthorityTO {
	@ColumnName(name="MENU_ID")
	private String menuCode;
	private String menuName;
	private String authorityGroupCode;
	private String menuLevel;
	private String authority;

	
}
