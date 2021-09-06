package kr.co.seoulit.system.authorityManager.applicationService;

import org.springframework.dao.DataAccessException;

import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.system.authorityManager.exception.IdNotFoundException;
import kr.co.seoulit.system.authorityManager.exception.PwMissMatchException;
import kr.co.seoulit.system.authorityManager.exception.PwNotFoundException;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException;

}
