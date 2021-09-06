package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;
@Data
public class MrpTO extends BaseTO {
	
	private String mrpNo;
	private String mpsNo;
	private String mrpGatheringNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private int requiredAmount;
	private String orderDate;
	private String requiredDate;
	private String mrpGatheringStatus;

}