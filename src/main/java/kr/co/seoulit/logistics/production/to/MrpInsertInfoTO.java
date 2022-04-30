package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_mrpInsertInf")
public class MrpInsertInfoTO {
	
	private String firstMrpNo;
	private String lastMrpNo;
	private String length;

}
