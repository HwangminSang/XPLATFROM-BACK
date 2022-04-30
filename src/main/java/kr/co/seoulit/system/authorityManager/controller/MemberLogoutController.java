package kr.co.seoulit.system.authorityManager.controller;


import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@AllArgsConstructor
@RestController
public class MemberLogoutController {
	


    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public ModelAndView Logout(HttpServletRequest request) {
    
        HttpSession session = request.getSession();
        session.invalidate();
       
        return new ModelAndView("loginForm");
    }
}
