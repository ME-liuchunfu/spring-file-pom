package xin.spring.file.common.exception;

/**
 * @author spring
 * @Package xin.spring.file.common.exception
 * @ClassName StorageException.java
 * @descript
 * @date 2021/9/3 9:31
 */
public class StorageException extends RuntimeException{

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
