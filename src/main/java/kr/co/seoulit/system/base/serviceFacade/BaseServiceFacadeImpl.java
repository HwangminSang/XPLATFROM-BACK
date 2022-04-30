package kr.co.seoulit.system.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.system.base.applicationService.AddressApplicationService;
import kr.co.seoulit.system.base.applicationService.BoardApplicationService;
import kr.co.seoulit.system.base.applicationService.CodeApplicationService;
import kr.co.seoulit.system.base.applicationService.ReportApplicationService;
import kr.co.seoulit.system.base.to.AddressTO;
import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.CodeDetailTO;
import kr.co.seoulit.system.base.to.CodeTO;
import kr.co.seoulit.system.base.to.ContractReportTO;
import kr.co.seoulit.system.base.to.EstimateReportTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {
	// 참조변수 선언
	@Autowired
	private CodeApplicationService codeAS;
	@Autowired
	private AddressApplicationService addressAS;
	@Autowired
	private ReportApplicationService reportAS;
	@Autowired
	private BoardApplicationService boardApplicationService;
	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {
		return codeAS.getDetailCodeList(divisionCode);
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {
		return codeAS.getCodeList();
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {
		return codeAS.checkCodeDuplication(divisionCode, newDetailCode);
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {
		return codeAS.batchCodeListProcess(codeList);
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {
		return codeAS.batchDetailCodeListProcess(detailCodeList);
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {
		return codeAS.changeCodeUseCheckProcess(detailCodeList);
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {
		return addressAS.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);
	}

	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {
		// TODO Auto-generated method stub
		return reportAS.getEstimateReport(estimateNo);
	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {
		// TODO Auto-generated method stub
		return reportAS.getContractReport(contractNo);
	}

	@Override
	public void insertBoard(BoardTO board) {
		// TODO Auto-generated method stub
		boardApplicationService.insertBoard(board);
	}

	@Override
	public void insertBoardFile(BoardFileTO boardFileTO) {
		// TODO Auto-generated method stub
		boardApplicationService.insertBoardFile(boardFileTO);
	}

	@Override
	public ArrayList<BoardTO> getBoardList() {
		// TODO Auto-generated method stub
		return boardApplicationService.getBoardList();
	}

	@Override
	public BoardTO getBoardInfo(int bno) {
		// TODO Auto-generated method stub
		return boardApplicationService.getBoardInfo(bno);
	}

	@Override
	public ArrayList<BoardFileTO> getBoardFiles(int bno) {
		// TODO Auto-generated method stub
		return boardApplicationService.getBoardFiles(bno);
	}

	@Override
	public void deleteBoard(String bno) {
		// TODO Auto-generated method stub
		boardApplicationService.deleteBoard(bno);
	}

	@Override
	public void updateBoardDetail(BoardTO board) {
		// TODO Auto-generated method stub
		boardApplicationService.updateBoardDetail(board);
	}

	@Override
	public void deleteBoardFiles(HashMap<String,Integer> map) {
		// TODO Auto-generated method stub
		boardApplicationService.deleteBoardFiles(map);
	}

	@Override
	public BoardFileTO getBoardFile(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return boardApplicationService.getBoardFile(map);
	}

	@Override
	public void updateBoardHit(BoardTO board) {
		// TODO Auto-generated method stub
		boardApplicationService.updateBoardHit(board);
	}

	@Override
	public void insertReply(ReplyBoardTO rbt) {
		// TODO Auto-generated method stub
		boardApplicationService.insertReply(rbt);
	}

	@Override
	public ArrayList<ReplyBoardTO> getReplyList(int bno) {
		// TODO Auto-generated method stub
		return boardApplicationService.getReplyList(bno);
	}

	@Override
	public void deleteReply(int rno) {
		// TODO Auto-generated method stub
		boardApplicationService.deleteReply(rno);
	}

	@Override
	public void updateReply(ReplyBoardTO rbt) {
		// TODO Auto-generated method stub
		boardApplicationService.updateReply(rbt);
	}


}
