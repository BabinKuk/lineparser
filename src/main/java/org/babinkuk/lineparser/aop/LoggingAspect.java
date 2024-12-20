package org.babinkuk.lineparser.aop;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private Logger log = Logger.getLogger(getClass().getName());
	
	// setup pointcut declaration
	// match any class in the package (first *), any method (second *), any number of args (..)
	@Pointcut("execution(* org.babinkuk.lineparser.controller.*.*(..))")
	private void forControllerPackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.service.*.*(..))")
	private void forServicePackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.generic.staticformat.*.*(..))")
	private void forStaticFormatPackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.generic.staticformat.formatter.*.*(..))")
	private void forFormatterPackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.generic.staticformat.parsedobject.*.*(..))")
	private void forParsedObjectPackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.generic.staticformat.parser.*.*(..))")
	private void forParserPackage() {
	}
	
	@Pointcut("execution(* org.babinkuk.lineparser.generic.staticformat.validator.*.*(..))")
	private void forValidatorPackage() {
	}
	
	// combine all pointcuts above
	@Pointcut("forControllerPackage() || forServicePackage() || forStaticFormatPackage() || forFormatterPackage() || forParsedObjectPackage() || forParserPackage() || forValidatorPackage()")
//	@Pointcut("forControllerPackage() || forServicePackage()")
	private void forAppFlow() {
	}
	
	@Before("forAppFlow()")
	private void before(JoinPoint joinPoint) {
		// display method
		String method = joinPoint.getSignature().toShortString();
		log.info("--> in @Before : " + method);
		
		// get method arguments
		Object[] args = joinPoint.getArgs();
		
		// display method arguments
		for (Object tempArg : args) {
			log.info("--> argument : " + tempArg);
		}
	}
	
	@AfterReturning(
		pointcut = "forAppFlow()",
		returning = "result"
	)
	private void afterReturning(JoinPoint joinPoint, Object result) {
		// display method
		String method = joinPoint.getSignature().toShortString();
		log.info("--> in @AfterReturning : " + method);
		
		// display data returned
		log.info("--> data : " + result);
	}
}