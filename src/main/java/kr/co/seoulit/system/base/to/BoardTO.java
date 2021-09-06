package kr.co.seoulit.system.base.to;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BoardTO {
	private int bno;		// 게시글번호
	private String name;		//작성자
	private String title;		//제목
	private String contents;		//내용
	private int hit;			//조회수
	private String uploadDate;  //업로드한 날짜
	private String password; //게시판 비밀번호 --> 삭제하거나 등록할 때
	private ArrayList<BoardFileTO> boardFiles = new ArrayList<BoardFileTO>(); //게시판에 올린 파일들
	private List<Integer> removeFiles;
	private int replyCount;
}
