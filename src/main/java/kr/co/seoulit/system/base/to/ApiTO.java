package kr.co.seoulit.system.base.to;

import kr.co.seoulit.system.common.annotation.ColumnName;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;


@Dataset(name="gds_Exchange")
@Data
public class ApiTO {

	@ColumnName(name="CUR_UNIT")
	private String cur_unit;
	@ColumnName(name="TTB")
	private String ttb;
	@ColumnName(name="TTS")
	private String tts;
	@ColumnName(name="DEAL_BAS_R")
	private String deal_bas_r;
	@ColumnName(name="BKPR")
	private String bkpr;
	@ColumnName(name="YY_EFEE_R")
	private String yy_efee_r;
	@ColumnName(name="TEN_DD_EFEE_R")
	private String ten_dd_efee_r;
	@ColumnName(name="KFTC_BKPR")
	private String kftc_bkpr;
	@ColumnName(name="CUR_NM")
	private String cur_nm;
}
