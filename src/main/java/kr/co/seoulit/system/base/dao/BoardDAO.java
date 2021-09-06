package kr.co.seoulit.system.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;

@Mapper
public interface BoardDAO {

	public void insertBoard(BoardTO board);

	public void insertBoardFile(BoardFileTO boardFileTO);

	public ArrayList<BoardTO> selectBoardList();

	public BoardTO selectBoard(int bno);

	public ArrayList<BoardFileTO> selectFileList(int bno);

	public void deleteBoard(int bno);

	public void deleteBoardFile(int bno);

	public void updateBoardDetail(BoardTO board);

	public void deleteBoardFiles(HashMap<String,Integer> map);

	public BoardFileTO getBoardFile(HashMap<String, Integer> map);

	public void updateBoardHit(BoardTO board);

	public void insertReply(ReplyBoardTO rbt);

	public ArrayList<ReplyBoardTO> getReplyList(int bno);

	public void deleteReply(int rno);

	public void updateReply(ReplyBoardTO rbt);
}
