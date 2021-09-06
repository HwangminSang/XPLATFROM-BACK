package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;
@Data
public class ProductionWorkInstructionTO extends BaseTO  {
	 private int workInstructionAmount;
	 private String workInstructionNo;
	 private String description;
	 private String itemCode;
	 private String productionStatus;
	 private String instructionDate;
	 private String mrpGatheringNo;
	 private String itemName;
	 private String unitOfWorkInstruction;

}