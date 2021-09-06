package kr.co.seoulit.system.base.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.system.base.dao.BoardDAO;
import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;

@Component
public class BoardApplicationServiceImpl implements BoardApplicationService{

	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void insertBoard(BoardTO board) {
		// TODO Auto-generated method stub
		boardDAO.insertBoard(board);
	}

	@Override
	public void insertBoardFile(BoardFileTO boardFileTO) {
		// TODO Auto-generated method stub
		boardDAO.insertBoardFile(boardFileTO);
	}

	@Override
	public ArrayList<BoardTO> getBoardList() {
		// TODO Auto-generated method stub
		return boardDAO.selectBoardList();
	}

	@Override
	public BoardTO getBoardInfo(int bno) {
		// TODO Auto-generated method stub
		return boardDAO.selectBoard(bno);
	}

	@Override
	public ArrayList<BoardFileTO> getBoardFiles(int bno) {
		// TODO Auto-generated method stub
		return boardDAO.selectFileList(bno);
	}

	@Override
	public void deleteBoard(String iBno) {
		// TODO Auto-generated method stub
		int bno = Integer.parseInt(iBno);
		boardDAO.deleteBoard(bno);
		boardDAO.deleteBoardFile(bno);
	}

	@Override
	public void updateBoardDetail(BoardTO board) {
		// TODO Auto-generated method stub
		boardDAO.updateBoardDetail(board);
	}

	@Override
	public void deleteBoardFiles(HashMap<String,Integer> map) {
		// TODO Auto-generated method stub
		boardDAO.deleteBoardFiles(map);
	}

	@Override
	public BoardFileTO getBoardFile(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return boardDAO.getBoardFile(map);
	}

	@Override
	public void updateBoardHit(BoardTO board) {
		// TODO Auto-generated method stub
		boardDAO.updateBoardHit(board);
	}

	@Override
	public void insertReply(ReplyBoardTO rbt) {
		// TODO Auto-generated method stub
		boardDAO.insertReply(rbt);
	}

	@Override
	public ArrayList<ReplyBoardTO> getReplyList(int bno) {
		// TODO Auto-generated method stub
		return boardDAO.getReplyList(bno);
	}

	@Override
	public void deleteReply(int rno) {
		// TODO Auto-generated method stub
		boardDAO.deleteReply(rno);
	}

	@Override
	public void updateReply(ReplyBoardTO rbt) {
		// TODO Auto-generated method stub
		boardDAO.updateReply(rbt);
	}
	
}
