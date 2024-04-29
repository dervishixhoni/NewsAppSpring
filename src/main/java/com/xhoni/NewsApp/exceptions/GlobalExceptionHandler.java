package com.xhoni.NewsApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404"); // name of your error page
        mav.addObject("exception", ex);
        return mav;
    }
}