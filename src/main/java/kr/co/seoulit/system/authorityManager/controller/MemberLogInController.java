package kr.co.seoulit.system.authorityManager.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.system.authorityManager.exception.IdNotFoundException;
import kr.co.seoulit.system.authorityManager.exception.PwMissMatchException;
import kr.co.seoulit.system.authorityManager.exception.PwNotFoundException;
import kr.co.seoulit.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;

@RestController
public class MemberLogInController  {
    
	@Autowired
	private AuthorityManagerServiceFacade authorityManagerSF;
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
    public ModelAndView LogInCheck(HttpServletRequest request){
  
        ModelMap modelMap = new ModelMap();
        try {

            HttpSession session = request.getSession();
            String companyCode = request.getParameter("companyCode");      //COM-01
            String workplaceCode = request.getParameter("workplaceCode");  //BRC-01
            String userId = request.getParameter("userId");               //1111
            String userPassword = request.getParameter("userPassword");   //1111

            EmpInfoTO TO = authorityManagerSF.accessToAuthority(companyCode, workplaceCode, userId, userPassword);
            
            if (TO != null) {
               
                session.setAttribute("sessionID", session.getId());
                session.setAttribute("userId", TO.getUserId());                //유저ID
                session.setAttribute("empCode", TO.getEmpCode());              //사원번호
                session.setAttribute("empName", TO.getEmpName());              //사원이름
                session.setAttribute("deptCode", TO.getDeptCode());            //부서코드
                session.setAttribute("deptName", TO.getDeptName());            //부서명
                session.setAttribute("positionCode", TO.getPositionCode());    //직급코드
                session.setAttribute("positionName", TO.getPositionName());    //직급명
                session.setAttribute("companyCode", TO.getCompanyCode());      //회사코드
                session.setAttribute("workplaceCode", workplaceCode);          //사업장코드
                session.setAttribute("workplaceName", TO.getWorkplaceName());  //사업장명
                session.setAttribute("image", TO.getImage()); 				   // 사진 
                session.setAttribute("authorityGroupCode", TO.getAuthorityGroupList()); //권한그룹리스트       
                session.setAttribute("authorityGroupMenuList", TO.getAuthorityGroupMenuList());  //권한그룹메뉴리스트
                
                // 메뉴 DB에서 가져오기
                String[] allMenuList = authorityManagerSF.getAllMenuList();     

                session.setAttribute("allMenuList", allMenuList[0]);   // 모든 메뉴 리스트
                session.setAttribute("navMenuList", allMenuList[1]);   // nav바 메뉴 리스트 
              
			}
            
        } catch (IdNotFoundException e1) {
            e1.printStackTrace();
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e1.getMessage());
        } catch (PwNotFoundException e2) {
            e2.printStackTrace();
            modelMap.put("errorCode", -3);
            modelMap.put("errorMsg", e2.getMessage());
        } catch (PwMissMatchException e3) {
            e3.printStackTrace();
            modelMap.put("errorCode", -4);
            modelMap.put("errorMsg", e3.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("errorCode", -5);
            modelMap.put("errorMsg", e.getMessage());
        } 

        return new ModelAndView("jsonView",modelMap);
    }
}
