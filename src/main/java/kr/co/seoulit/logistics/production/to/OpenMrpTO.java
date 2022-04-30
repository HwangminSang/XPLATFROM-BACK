package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_mrpOpenTemp")
public class OpenMrpTO extends BaseTO {
	
	private String mpsNo;
	private String bomNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String orderDate;
	private String requiredDate;
	private String planAmount;
	private String totalLossRate;	 
	private String caculatedAmount;
	private int requiredAmount;
	private String unitOfMrp;

}
