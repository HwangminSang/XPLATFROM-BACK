package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;
@Data
public class ProductionResultManagementTO extends BaseTO {
	 private String productionResultNo;
	 private String workInstructionNo;
	 private String description;
	 private String productionDate;
	 private String itemCode;
	 private String unitOfProductionResult;
	 private String productionAmount;
	 private String itemName;

}