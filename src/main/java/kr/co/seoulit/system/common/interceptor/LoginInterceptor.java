package kr.co.seoulit.system.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@SuppressWarnings("deprecation")
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();				// Servlet Container에게 session 요청 --> session 생성
		String userId = (String)session.getAttribute("userId");
		
		if(userId == null) {									// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
			response.sendRedirect("/loginForm.html");
			System.out.println("로그인이 필요함");			
			return false;										// 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
			
		}else {
			return true;
		}
	}
	
}
