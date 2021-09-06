package kr.co.seoulit.system.base.to;

import lombok.Data;

@Data
public class BoardFileTO {
	private int fileNo;
	private int bno;
	private String orgFileName;
	private String storedFileName;
	private long fileSize;
	private String regDate;
	private String delGB;
}

