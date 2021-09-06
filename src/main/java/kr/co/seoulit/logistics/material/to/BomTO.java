package kr.co.seoulit.logistics.material.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class BomTO extends BaseTO {

	private String itemCode;
	private String parentItemCode;
	private int no;
	private int netAmount;
	private String description;
}