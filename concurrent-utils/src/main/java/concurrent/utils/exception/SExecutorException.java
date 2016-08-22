package concurrent.utils.exception;

@SuppressWarnings("serial")
public class SExecutorException extends RuntimeException {

	
    public SExecutorException() {
        super();
    }

   
    public SExecutorException(String message) {
        super(message);
    }

   
    public SExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

  
    public SExecutorException(Throwable cause) {
        super(cause);
    }


    protected SExecutorException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
