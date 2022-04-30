package kr.co.seoulit.system.common.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

@Component
public class FIleUtil {
	private String path = "C:\\Apache2\\htdocs\\image\\";

	public void fileUpload(String fileName, byte[] file) {
		FileOutputStream out = null;
		try {
			if (fileName != null) {
				System.out.println("fileName : " + fileName);
				out = new FileOutputStream(path+fileName);	
				
				BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
				
				bufferedOut.write(file);
				bufferedOut.flush();
				bufferedOut.close();
				
				out.close();
				bufferedOut = null;
				out = null;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public String getPath() {
		return path;
	}
}