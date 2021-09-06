package kr.co.seoulit.system.base.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.seoulit.system.base.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;
import kr.co.seoulit.system.base.to.ReplyBoardTO;
import kr.co.seoulit.system.base.util.FileUtil;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	private BaseServiceFacade baseSF;
	@Autowired
	private FileUtil fileUtil;
	
	@RequestMapping(value="/replyReInsert",method=RequestMethod.POST)
	public String replyReInsert(ReplyBoardTO rbt) {
		baseSF.insertReply(rbt);
		return "redirect:boardDetail.html?bno="+rbt.getBno();
	}
	
	@RequestMapping(value="/replyUpdate",method=RequestMethod.POST)
	public String updateReply(ReplyBoardTO rbt) {
		baseSF.updateReply(rbt);
		return "redirect:boardDetail.html?bno="+rbt.getBno();
	}
	
	@RequestMapping(value="/replyDelete",method=RequestMethod.POST)
	public String deleteReply(int bno,int rno) {
		baseSF.deleteReply(rno);
		return "redirect:boardDetail.html?bno="+bno;
	}
	
	@RequestMapping(value="/insertReply",method=RequestMethod.POST)
	public String insertReply(ReplyBoardTO rbt) {
		baseSF.insertReply(rbt);
		return "redirect:boardDetail.html?bno="+rbt.getBno();
	}
	
	@RequestMapping(value="/getReplyList",method=RequestMethod.GET)
	public ModelAndView getReplyList(int bno) {
		ArrayList<ReplyBoardTO> list = baseSF.getReplyList(bno);
		HashMap<String,Object> map = new HashMap<>();
		map.put("list",list);
		return new ModelAndView("jsonView",map);
	}
	
	@RequestMapping(value="/updateBoardHit", method=RequestMethod.POST)
	public ModelAndView updateBoardHit(BoardTO board) {
		baseSF.updateBoardHit(board);
		HashMap<String,Integer> map = new HashMap<>();
		map.put("bno",board.getBno());
		return new ModelAndView("jsonView",map);
	}
	
	@RequestMapping(value="/fileDown", method=RequestMethod.POST)
	public void fileDown(@RequestParam Map<String,String> map,HttpServletResponse response) throws Exception{
		String storedFileName = map.get("storedFileName");
		String originalFileName = map.get("orgFileName");
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte[]형식으로 변환한다.
		byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\upload\\file\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value="/getBoardDetailInfo")
	public ModelAndView getBoardDetailInfo(int bno) {
		HashMap<String,Object> map = new HashMap<>();
		BoardTO board = baseSF.getBoardInfo(bno);
		ArrayList<BoardFileTO> files=baseSF.getBoardFiles(bno);
		map.put("json",board);
		map.put("files",files);
		return new ModelAndView("jsonView",map);
	}
	
	@RequestMapping(value="/insertBoard" ,method=RequestMethod.POST)
	public ModelAndView insertBoard(BoardTO board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		ModelAndView mv = new ModelAndView();
		baseSF.insertBoard(board);
		List<BoardFileTO> list=fileUtil.parseInsertFileInfo(board,multipartHttpServletRequest);
		int size = list.size();
		for(int i=0;i<size;i++) {
			baseSF.insertBoardFile(list.get(i));
		}
		mv.setViewName("redirect:board.html");
		return mv;
	}
	
	@RequestMapping(value="/getBoardList", method=RequestMethod.GET)
	public ModelAndView getBoardList() {
		HashMap<String,Object> map = new HashMap<>();
		ArrayList<BoardTO> list = baseSF.getBoardList();
		for(BoardTO b : list) {
			ArrayList<ReplyBoardTO> r = baseSF.getReplyList(b.getBno());
			b.setReplyCount(r.size());
		}
		map.put("json",list);
		ModelAndView mv = new ModelAndView("jsonView",map);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value="/deleteBoard",method=RequestMethod.POST)
	public ModelAndView deleteBoard(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String bno=request.getParameter("bno");
		baseSF.deleteBoard(bno);
		mv.setViewName("redirect:board.html");
		return mv;
	}
	
	@RequestMapping(value="/updateBoardDetail", method=RequestMethod.POST)
	public @ResponseBody void updateBoardDetail(BoardTO board,MultipartHttpServletRequest multipartHttpServletRequest)throws Exception {
		HashMap<String,Integer> map = new HashMap<>();
		baseSF.updateBoardDetail(board);
		if(board.getRemoveFiles()!=null) {
			for(int fileNo:board.getRemoveFiles()) {
				map.clear();
				map.put("fileNo",fileNo);
				map.put("bno",board.getBno());
				BoardFileTO boardFileTO=baseSF.getBoardFile(map);
				fileUtil.deleteFile(boardFileTO.getStoredFileName());
				baseSF.deleteBoardFiles(map);
			}
		}
		List<BoardFileTO> list=fileUtil.parseInsertFileInfo(board,multipartHttpServletRequest);
		int size = list.size();
		for(int i=0;i<size;i++) {
			baseSF.insertBoardFile(list.get(i));
		}
		
	}
}
