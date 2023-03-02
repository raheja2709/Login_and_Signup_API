package comtaskxrXrTask.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import comtaskxrXrTask.ApiError.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class RestExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	

	@ExceptionHandler(UserException.class)
	protected ResponseEntity<Object> handleInvalidDataExcetpion(UserException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getError());
		apiError.setDescription(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	

}
