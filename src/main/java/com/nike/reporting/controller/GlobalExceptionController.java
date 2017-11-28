/**
 * @author omkar_dhulap
 * @Date 28 April 2016
 * @Description Common controller exception handling
 */
package com.nike.reporting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception e) {

		logger.error("ERROR: " + e.getMessage(), e);
		ModelAndView model = new ModelAndView("generic_error");
		// model.addObject("errCode", "NO_Error_Code");
		model.addObject("errMsg", e.getMessage());
		return model;
	}

	// For business validations we will display error on particular jsps and
	// system won't crash

	/*
	 * @ExceptionHandler(NikeException.class) public ModelAndView
	 * handleCustomException(NikeException ex) {
	 * 
	 * ModelAndView model = new ModelAndView("/generic_error");
	 * model.addObject("errCode", ex.getErrCode()); model.addObject("errMsg",
	 * ex.getErrMsg());
	 * 
	 * return model;
	 * 
	 * }
	 */

}