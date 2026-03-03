package hr.talent.acquisition.exception;

import ch.ivyteam.ivy.bpm.error.BpmError;
import ch.ivyteam.ivy.environment.Ivy;

public class BpmExceptionHandler {
  public static String logFormattedError(BpmError error) {
    if (error == null) {
        return "";
    }

    StringBuilder errorLog = new StringBuilder();
    errorLog.append("\n");
    errorLog.append("================================================================================\n");
    errorLog.append("SMART WORKFLOW AGENT ERROR\n");
    errorLog.append("================================================================================\n");
    
    // Error basic information
    errorLog.append("Error Code: ").append(error.getErrorCode()).append("\n");
    
    Throwable cause = error.getCause();
    if (cause != null) {
      errorLog.append("Error Type: ").append(cause.getClass().getName()).append("\n");
      errorLog.append("Error Message: ").append(cause.getMessage()).append("\n");
      errorLog.append("\n");
      
      // Stack trace
      errorLog.append("Stack Trace:\n");
      errorLog.append("--------------------------------------------------------------------------------\n");
      StackTraceElement[] stackTrace = cause.getStackTrace();
      if (stackTrace != null && stackTrace.length > 0) {
        for (StackTraceElement element : stackTrace) {
          errorLog.append("  at ").append(element.getClassName())
                  .append(".").append(element.getMethodName())
                  .append("(").append(element.getFileName())
                  .append(":").append(element.getLineNumber())
                  .append(")\n");
        }
      }
      
      // Nested causes
      Throwable rootCause = cause.getCause();
      int level = 1;
      while (rootCause != null && level <= 5) {
        errorLog.append("\n");
        errorLog.append("Caused by [Level ").append(level).append("]: ")
                .append(rootCause.getClass().getName())
                .append(": ").append(rootCause.getMessage()).append("\n");
        
        StackTraceElement[] rootStackTrace = rootCause.getStackTrace();
        if (rootStackTrace != null && rootStackTrace.length > 0) {
          for (StackTraceElement element : rootStackTrace) {
            errorLog.append("  at ").append(element.getClassName())
                    .append(".").append(element.getMethodName())
                    .append("(").append(element.getFileName())
                    .append(":").append(element.getLineNumber())
                    .append(")\n");
          }
        }
        
        rootCause = rootCause.getCause();
        level++;
      }
      
      if (rootCause != null) {
        errorLog.append("\n... and more nested causes\n");
      }
    } else {
      errorLog.append("No cause information available\n");
    }
    
    errorLog.append("================================================================================\n");
    
    Ivy.log().error(errorLog.toString());
    return errorLog.toString();
  }
  
  public static String logFormattedError(Exception exception) {
    if (exception == null) {
        return "";
    }

    StringBuilder errorLog = new StringBuilder();
    errorLog.append("\n");
    errorLog.append("================================================================================\n");
    errorLog.append("SMART WORKFLOW AGENT ERROR\n");
    errorLog.append("================================================================================\n");
    
    // Error basic information
    errorLog.append("Error Type: ").append(exception.getClass().getName()).append("\n");
    errorLog.append("Error Message: ").append(exception.getMessage()).append("\n");
    errorLog.append("\n");
    
    // Stack trace
    errorLog.append("Stack Trace:\n");
    errorLog.append("--------------------------------------------------------------------------------\n");
    StackTraceElement[] stackTrace = exception.getStackTrace();
    if (stackTrace != null && stackTrace.length > 0) {
      for (StackTraceElement element : stackTrace) {
        errorLog.append("  at ").append(element.getClassName())
                .append(".").append(element.getMethodName())
                .append("(").append(element.getFileName())
                .append(":").append(element.getLineNumber())
                .append(")\n");
      }
    }
    
    // Nested causes
    Throwable rootCause = exception.getCause();
    int level = 1;
    while (rootCause != null && level <= 5) {
      errorLog.append("\n");
      errorLog.append("Caused by [Level ").append(level).append("]: ")
              .append(rootCause.getClass().getName())
              .append(": ").append(rootCause.getMessage()).append("\n");
      
      StackTraceElement[] rootStackTrace = rootCause.getStackTrace();
      if (rootStackTrace != null && rootStackTrace.length > 0) {
        for (StackTraceElement element : rootStackTrace) {
          errorLog.append("  at ").append(element.getClassName())
                  .append(".").append(element.getMethodName())
                  .append("(").append(element.getFileName())
                  .append(":").append(element.getLineNumber())
                  .append(")\n");
        }
      }
      
      rootCause = rootCause.getCause();
      level++;
    }
    
    if (rootCause != null) {
      errorLog.append("\n... and more nested causes\n");
    }
    
    errorLog.append("================================================================================\n");
    
    Ivy.log().error(errorLog.toString());
    return errorLog.toString();
  }
}
