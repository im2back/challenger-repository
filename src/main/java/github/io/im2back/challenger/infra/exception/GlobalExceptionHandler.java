package github.io.im2back.challenger.infra.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import github.io.im2back.challenger.model.ValidacaoException;
import jakarta.servlet.http.HttpServletRequest;
@ControllerAdvice
public class GlobalExceptionHandler {
	
	


	    @ExceptionHandler(ValidacaoException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<StandardError> handleValidacaoException(ValidacaoException ex,HttpServletRequest request) {
	    	HttpStatus status = HttpStatus.CONFLICT;
	    	StandardError error = new StandardError(status.value(),ex.getMessage(),request.getRequestURI());
	        return ResponseEntity.badRequest().body(error);
	    }
	}



