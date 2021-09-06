package kr.co.seoulit.logistics.material.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class BomDeployTO extends BaseTO {
	
	private String bomNo;
	private int bomLevel;
	private String parentItemCode;
	private String itemCode;
	private String itemName;
	private String unitOfStock;
	private int netAmount;
	private String lossRate;
	private String necessaryAmount;	
	private String leadTime;
	private String isLeaf;
	private String description;

}
