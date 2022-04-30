package kr.co.seoulit.system.base.mapper;


import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.system.base.to.CodeDetailTO;

@Mapper
public interface CodeDetailDAO {

	/* ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode); */

	void insertDetailCode(CodeDetailTO TO);

	void updateDetailCode(CodeDetailTO TO);

	public void deleteDetailCode(CodeDetailTO TO);
	
	public void changeCodeUseCheck(HashMap<String, String> map);
	
	
}
