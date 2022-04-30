package kr.co.seoulit.system.base.controller;


import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/base/*")
public class ImgFileController{

	@RequestMapping(value="/doFileUpload")
	 public void doFileUpload(@RequestAttribute("reqData")PlatformData reqData) throws IOException {
	        System.out.println("파일업로드 시도 = " + reqData );

	        DataSet dataset = reqData.getDataSet("ds_img");
	        String fileName = (String) dataset.getObject(0, "EMP_FILE_NAME");
	
	        String uploadPath = "C:\\Apache2\\htdocs\\image\\" + fileName; //아파치
	    
	        FileOutputStream fos1 = null;

	        try {
	            //MultipatrFile클래스의 getBytes()를 사용해서 multipartFile의 데이터를 바이트배열로 추출
	            byte fileData[] = dataset.getBlob(0, "IMG_FILE_DATA");
	            //System.out.println(Arrays.toString(fileData));
	            fos1 = new FileOutputStream(uploadPath);
	            //FileOutputStream클래스의 write()로 파일을 filePath에 저장
	            fos1.write(fileData);
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (fos1 != null) {
	                fos1.close();
	            }
	        }

	    }

}
