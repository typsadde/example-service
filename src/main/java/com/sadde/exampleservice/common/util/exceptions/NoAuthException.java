package com.sadde.exampleservice.common.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoAuthException extends RuntimeException {
  public NoAuthException() {
    super("Auth required");
  }
}
