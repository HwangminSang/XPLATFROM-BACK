package kr.co.seoulit.system.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.seoulit.system.base.to.AddressTO;
import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.CodeDetailTO;
import kr.co.seoulit.system.base.to.CodeTO;
import kr.co.seoulit.system.base.to.ContractReportTO;
import kr.co.seoulit.system.base.to.EstimateReportTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;

public interface BaseServiceFacade {

	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode);

	public ArrayList<CodeTO> getCodeList();

	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode);

	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList);

	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList);

	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList);

	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);

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
