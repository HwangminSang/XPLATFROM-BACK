package kr.co.seoulit.system.base.to;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CodeTO extends BaseTO {
	
	private String divisionCodeNo;
	private String codeType;
	private String divisionCodeName;
	private String codeChangeAvailable;
	private String description;
	private ArrayList<CodeDetailTO> codeDetailTOList;

}