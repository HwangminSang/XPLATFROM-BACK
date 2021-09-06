package kr.co.seoulit.system.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.seoulit.system.base.to.BoardFileTO;
import kr.co.seoulit.system.base.to.BoardTO;

@Component
public class FileUtil {
	private static final String filePath = "C:\\upload\\file\\"; // 파일이 저장될 위치
	
	public void deleteFile(String fileName) {
		File file =new File(filePath+fileName);
		if(file.exists()) {
			file.delete();
		}
	}
	
	public List<BoardFileTO> parseInsertFileInfo(BoardTO boardTO, MultipartHttpServletRequest mpRequest) throws Exception{
		
		Iterator<String> iterator = mpRequest.getFileNames();
		
		//MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<BoardFileTO> list = new ArrayList<>();

		int bno = boardTO.getBno();
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}

		String name;
		while(iterator.hasNext()) {
			name=iterator.next();
			List<MultipartFile> fileMultiList = mpRequest.getFiles(name);
			for(MultipartFile multipartFile:fileMultiList) {
				if(multipartFile.isEmpty() == false) {
					originalFileName = multipartFile.getOriginalFilename();
					originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
					storedFileName = getRandomString() + originalFileExtension;
					
					File file2 = new File(filePath + storedFileName);
					multipartFile.transferTo(file2);
					BoardFileTO boardFileTO=new BoardFileTO();
					boardFileTO.setBno(bno);
					boardFileTO.setOrgFileName(originalFileName);
					boardFileTO.setStoredFileName(storedFileName);
					boardFileTO.setFileSize(multipartFile.getSize());
					list.add(boardFileTO);
				}
			}
			
		}
		return list;
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}