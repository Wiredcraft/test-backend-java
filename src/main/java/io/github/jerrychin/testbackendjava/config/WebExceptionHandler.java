package io.github.jerrychin.testbackendjava.config;

import io.github.jerrychin.testbackendjava.model.vo.ResponseVO;
import io.github.jerrychin.testbackendjava.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler, converts exception to JSON response.
 */
@ControllerAdvice
public class WebExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ResponseVO response = new ResponseVO(exception.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handle(HttpServletRequest request, Throwable throwable) {
        log.error("{} {}", request.getMethod(), request.getRequestURI(), throwable);
        ResponseVO response = new ResponseVO(throwable.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
