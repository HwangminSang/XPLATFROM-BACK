package kr.co.seoulit.hr.affair.controller;

//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.hr.affair.serviceFacade.AffairServiceFacade;
import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.hr.affair.to.EmployeeBasicTO;
import kr.co.seoulit.hr.affair.to.EmployeeDetailTO;
import kr.co.seoulit.hr.affair.to.EmployeeSecretTO;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import lombok.Data;

@RestController
@RequestMapping(value = {"/hr/*", "/authorityManager/*"})
public class EmpController{

	// serviceFacade 참조변수 선언
	@Autowired
	private AffairServiceFacade hrSF;

	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchAllEmpList.do", method=RequestMethod.GET)
	public ModelMap searchAllEmpList(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");
		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String deptCode = request.getParameter("deptCode");

		ArrayList<EmpInfoTO> empList = null;
		String[] paramArray = null;

			switch (searchCondition) {

			case "ALL":

				paramArray = new String[] { companyCode };
				break;

			case "WORKPLACE":

				paramArray = new String[] { companyCode, workplaceCode };
				break;

			case "DEPT":

				paramArray = new String[] { companyCode, deptCode };
				break;

			case "RETIREMENT":

				paramArray = new String[] { companyCode };
				break;

			}

			empList = hrSF.getAllEmpList(searchCondition, paramArray);

			modelMap.put("gridRowJson", empList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		  /*finally {
			out.println(gson.toJson(modelMap));
			out.close();
		}*/
		return modelMap;
	}
	@RequestMapping(value="/searchEmpInfo.do" , method=RequestMethod.GET)
	public ModelMap searchEmpInfo(HttpServletRequest request) {

		String companyCode = request.getParameter("companyCode");
		String empCode = request.getParameter("empCode");

		EmpInfoTO empInfoTO = null;

		//PrintWriter out = null;
			//out = response.getWriter();

			empInfoTO = hrSF.getEmpInfo(companyCode, empCode);

			modelMap.put("empInfo", empInfoTO);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			/*
			 * finally { out.println(gson.toJson(modelMap)); out.close(); }
			 */
		return modelMap;

	}
	@RequestMapping(value="/checkUserIdDuplication.do", method=RequestMethod.GET)
	public ModelMap checkUserIdDuplication(HttpServletRequest request) {

		String companyCode = request.getParameter("companyCode");
		String newUserId = request.getParameter("newUseId");
		//PrintWriter out = null;
			//out = response.getWriter();

			Boolean flag = hrSF.checkUserIdDuplication(companyCode, newUserId);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			/*
			 * finally { out.println(gson.toJson(modelMap)); out.close(); }
			 */
		return modelMap;
	}
	@RequestMapping(value="/checkEmpCodeDuplication.do", method=RequestMethod.GET)
	public ModelMap checkEmpCodeDuplication(HttpServletRequest request) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = request.getParameter("newEmpCode");
			//PrintWriter out = null;
			//out = response.getWriter();

			Boolean flag = hrSF.checkEmpCodeDuplication(companyCode, newEmpCode);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

			/*
			 * finally { out.println(gson.toJson(modelMap)); out.close(); }
			 */
		return modelMap;
	}
	
	@RequestMapping(value= "/getNewEmpCode.do" ,method=RequestMethod.GET)
	public ModelMap getNewEmpCode(HttpServletRequest request) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = null;
		//PrintWriter out = null;
			//out = response.getWriter();

			newEmpCode = hrSF.getNewEmpCode(companyCode);

			modelMap.put("newEmpCode", newEmpCode);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

			/*
			 * finally { out.println(gson.toJson(modelMap)); out.close(); }
			 */
		return modelMap;
	}
	
	@RequestMapping(value= "/batchListProcess.do", method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request) {


		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");
		//PrintWriter out = null;
			//out = response.getWriter();

			ArrayList<EmployeeBasicTO> empBasicList = null;
			ArrayList<EmployeeDetailTO> empDetailList = null;
			ArrayList<EmployeeSecretTO> empSecretList = null;

			HashMap<String, Object> resultMap = null;

			if (tableName.equals("BASIC")) {

				empBasicList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeBasicTO>>() {
				}.getType());

				resultMap = hrSF.batchEmpBasicListProcess(empBasicList);

			} else if (tableName.equals("DETAIL")) {

				empDetailList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeDetailTO>>() {
				}.getType());
				System.out.println(gson.toJson(empDetailList));
				
				resultMap = hrSF.batchEmpDetailListProcess(empDetailList);

			} else if (tableName.equals("SECRET")) {

				empSecretList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeSecretTO>>() {
				}.getType());

				System.out.println(gson.toJson(empSecretList));

				
				resultMap = hrSF.batchEmpSecretListProcess(empSecretList);

			}

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

			/*
			 * finally { out.println(gson.toJson(modelMap)); out.close(); }
			 */

		return modelMap;
	}

}
