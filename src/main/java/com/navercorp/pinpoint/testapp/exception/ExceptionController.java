package com.navercorp.pinpoint.testapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> runtimeExceptionHandler(RuntimeException e){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "");
		response.put("status", "REQUEST_FAIL");
		response.put("error_message", "Application RuntimeException");
		return response;
	}
}
