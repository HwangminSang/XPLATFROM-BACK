package kr.co.seoulit.system.base.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;

public interface BoardApplicationService {

	public void insertBoard(BoardTO board);

	public void insertBoardFile(BoardFileTO boardFileTO);

	public ArrayList<BoardTO> getBoardList();

	public BoardTO getBoardInfo(int bno);

	public ArrayList<BoardFileTO> getBoardFiles(int bno);

	public void deleteBoard(String bno);

	public void updateBoardDetail(BoardTO board);

	public void deleteBoardFiles(HashMap<String,Integer> map);

	public BoardFileTO getBoardFile(HashMap<String, Integer> map);

	public void updateBoardHit(BoardTO board);

	public void insertReply(ReplyBoardTO rbt);

	public ArrayList<ReplyBoardTO> getReplyList(int bno);

	public void deleteReply(int rno);

	public void updateReply(ReplyBoardTO rbt);

}
