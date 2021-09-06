package kr.co.seoulit.system.base.to;

import lombok.Data;

@Data
public class CodeDetailTO extends BaseTO {
	
	private String divisionCodeNo;
	private String detailCode;
	private String detailCodeName;
	private String codeUseCheck;
	private String description;
	

}