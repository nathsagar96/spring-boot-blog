package com.codesmith.wordsmith.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>This exception is typically used in scenarios such as retrieving an entity by its ID from a
 * database when the entity does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
