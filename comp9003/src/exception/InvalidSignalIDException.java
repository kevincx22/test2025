package exception;

/**
 * 自定义异常类，用于处理无效的signalID
 */
public class InvalidSignalIDException extends Exception {
    public InvalidSignalIDException(String msg) {
        super(msg);
    }
}
