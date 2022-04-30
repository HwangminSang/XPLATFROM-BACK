package kr.co.seoulit.system.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.system.authorityManager.exception.IdNotFoundException;
import kr.co.seoulit.system.authorityManager.exception.PwMissMatchException;
import kr.co.seoulit.system.authorityManager.exception.PwNotFoundException;


@Aspect //PointCut과 Advice 설정 그리고 Aspect 구현을 함께 제공한다.
@Configuration //설정파일을 만들기 위한 애노테이션 or Bean을 등록, 스프링에서 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig를 상속받은 임의의 클래스를 만들고 그것을 스프링 빈으로 등록
               //@Bean만 사용해도 스프링 빈으로 등록은 되지만 싱글톤이 유지되지는 않는다
@EnableAspectJAutoProxy // 타켓클래스에 기본은 false 인터페이스-jdk dynamic proxy를 이용 ,   true 인터페이스 CGLIB를 이용
@Slf4j
public class CommonAspect {
	
	//	ExceptionHandler
	  @org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)
	  public ModelAndView idNotFoundExceptionHandler(HttpServletRequest request, IdNotFoundException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -2);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################IdNotFoundException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	  @org.springframework.web.bind.annotation.ExceptionHandler(PwMissMatchException.class)
	  public ModelAndView pwMissMatchException(HttpServletRequest request, PwMissMatchException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -4);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################PwMissMatchException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	  @org.springframework.web.bind.annotation.ExceptionHandler(PwNotFoundException.class)
	  public ModelAndView pwNotFoundExceptionHandler(HttpServletRequest request, PwNotFoundException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -3);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################PwNotFoundException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	  @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
	  public ModelAndView dataAcessExceptionHandler(HttpServletRequest request, DataAccessException e) {
	     
		  ModelAndView mv = new ModelAndView("/errorPage");
		  
		  mv.addObject("errorCode", -3);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#####################DataAccessException###################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	 @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	 public ModelAndView defaultExceptionHandler(HttpServletRequest request,Exception exception){ 
		
		 ModelAndView mv = new ModelAndView("/errorPage"); 
		 mv.addObject("exception", exception);
		 System.out.println("******************** 전체익셉션");
		 
		 log.error("defaultExceptionHandler", exception);
	 
	 return mv; 
	 
	 }
	 
//	LoggerAspect
	 @Component// @Component는 클래스 레벨에서 선언함으로써 스프링이 런타임시에 컴포넌트스캔을 하여 자동으로 빈을 찾고(detect) 등록하는 애노테이션
	           //개발자가 컨트롤이 불가능한 외부 라이브러리를 빈으로 등록하고 싶을때 @Bean을 사용하며, 개발자가 직접 컨트롤이 가능한 클래스의 경우 @Component
	 @Aspect
	 public class LoggerAspect {
		
	
		 //PointCut의 속성에 핵심코드의 어느 부분까지 공통 기능을 사용하겟다고 명시 < 
		@Around("execution(* kr.co.seoulit..serviceFacade.*.*(..)) or execution(* kr.co.seoulit..applicationService.*.*(..)) or execution(* kr.co.seoulit..mapper.*.*(..))")
		public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
			String type = "";
			String name = joinPoint.getSignature().getDeclaringTypeName();
			if (name.indexOf("Facade") > -1) {
				type = "ServiceFacadeImpl  \t:  ";
			}
			else if (name.indexOf("Application") > -1) {
				type = "ApplicationServiceImpl  \t:  ";
			}
			else if (name.indexOf("DAO") > -1) {
				type = "DAO  \t\t:  ";
			}
			log.info(type +" \n\t:"+ name + "." + joinPoint.getSignature().getName() + "()");
			
			Object obj = joinPoint.proceed();
			
			return obj;
		}
	 }
	
//	TransactionAspect
	 @Configuration
	 public class TransactionAspect {
	 	
	 	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	 	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* kr.co.seoulit..serviceFacade.*.*(..) ) "; 
	 	
	    @Autowired
	 	private PlatformTransactionManager transactionManager ;
	 	
	 	@SuppressWarnings("deprecation")
		@Bean	//메서드레벨에서 선언 
	 	public TransactionInterceptor transactionAdvice(){
	 		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
	 		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
	 		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
	 		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(DataAccessException.class)));
	 		source.setTransactionAttribute(transactionAttribute);
	 		
	 	
	 		return new TransactionInterceptor(transactionManager, source);
	 	}
	 	
	 	@Bean //메서드레벨에서 선언
	 	public Advisor transactionAdviceAdvisor(){
	 		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	 		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
	 		
	 		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	 	}
	 }

}
