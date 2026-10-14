package com.company.intra.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 전역 예외 -> 에러페이지 매핑 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handle(Exception e, Model model) {
        log.error("Unhandled exception", e);
        model.addAttribute("errorMessage", e.getMessage());
        return "error/500";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException e, Model model) {
        log.warn("Bad request: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error/400";
    }
}
