package kr.co.seoulit.hr.affair.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_img")
public class ImgTO {

    private String empFileName;
    private byte[] imgFileData;
    private String status;

}
