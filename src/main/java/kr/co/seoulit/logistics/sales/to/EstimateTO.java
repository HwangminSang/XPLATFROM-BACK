package kr.co.seoulit.logistics.sales.to;

import java.util.ArrayList;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class EstimateTO extends BaseTO {
	private String effectiveDate;
	private String estimateNo;
	private String estimateRequester;
	private String description;
	private String contractStatus;
	private String customerCode;
	private String personCodeInCharge;
	private String personNameCharge;
	private String estimateDate;
	private ArrayList<EstimateDetailTO> estimateDetailTOList;

}