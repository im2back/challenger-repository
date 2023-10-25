package github.io.im2back.challenger.infra.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import github.io.im2back.challenger.model.util.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidacaoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<StandardError> handleValidacaoException(ValidacaoException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError error = new StandardError(status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<StandardError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError error = new StandardError(status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<StandardError> handleEntityNotFoundException(EntityNotFoundException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError error = new StandardError(status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.badRequest().body(error);

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<StandardError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		StandardError error = new StandardError(status.value(), ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status.value()).body(error);

	}

}
