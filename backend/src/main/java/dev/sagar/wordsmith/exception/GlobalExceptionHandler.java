package dev.sagar.wordsmith.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Global exception handler for the application.
 *
 * <p>This class handles various exceptions thrown within the application and
 * returns structured error responses.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a 404 Not Found response.
     *
     * @param exception the ResourceNotFoundException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request);
    }

    /**
     * Handles UsernameNotFoundException and returns a 404 Not Found response.
     *
     * @param exception the UsernameNotFoundException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request);
    }

    /**
     * Handles BadCredentialsException and returns a 401 Unauthorized response.
     *
     * @param exception the BadCredentialsException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", exception.getMessage(), request);
    }

    /**
     * Handles AlreadyExistException and returns a 409 Conflict response.
     *
     * @param exception the AlreadyExistException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistException(AlreadyExistException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Conflict", exception.getMessage(), request);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response.
     *
     * @param exception the MethodArgumentNotValidException that was thrown
     * @param request   the current web request
     * @return a structured error response with validation error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return buildErrorResponse(errors, request);
    }

    /**
     * Handles IllegalArgumentException and returns a 400 Bad Request response.
     *
     * @param exception the IllegalArgumentException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request);
    }

    /**
     * Handles PropertyReferenceException and returns a 400 Bad Request response.
     *
     * @param exception the PropertyReferenceException that was thrown
     * @param request   the current web request
     * @return a structured error response with details about the exception
     */
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePropertyReferenceException(PropertyReferenceException exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request);
    }

    /**
     * Handles generic exceptions and returns a 500 Internal Server Error response.
     *
     * @param exception the Exception that was thrown
     * @param request   the current web request
     * @return a structured error response indicating an unexpected error occurred
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception exception, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred.", request);
    }

    /**
     * Builds a structured error response for a specific exception.
     *
     * @param status  the HTTP status
     * @param error   the error type
     * @param message the error message
     * @param request the current web request
     * @return an {@link ErrorResponse} object containing the error details
     */
    private ErrorResponse buildErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getDescription(false))
                .build();
    }

    /**
     * Builds a structured error response for validation errors.
     *
     * @param errors  a list of validation error messages
     * @param request the current web request
     * @return an {@link ErrorResponse} object containing the validation error details
     */
    private ErrorResponse buildErrorResponse(List<String> errors, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message("Bad Request")
                .errors(errors)
                .path(request.getDescription(false))
                .build();
    }
}
