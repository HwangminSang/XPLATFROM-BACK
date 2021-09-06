package kr.co.seoulit.logistics.material.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class BomInfoTO extends BaseTO {
	
	private String itemCode;
	private String parentItemCode;
	private int no;
	private String itemName;
	private String itemClassification;
	private String itemClassificationName;
	private int netAmount;
	private String description;
	
}
