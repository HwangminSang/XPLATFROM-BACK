package kr.co.seoulit.system.base.to;

import lombok.Data;

@Data
public class ReplyBoardTO {
	private int rno;
	private int bno;
	private int depth;
	private int parentNo;
	private String content;
	private String writer;
	private String empCode;
	private String password;
	private String uploadDate;
	private String image;
}
