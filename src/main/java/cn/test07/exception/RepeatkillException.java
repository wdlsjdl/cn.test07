package cn.test07.exception;

/**
 * Created by VNT on 2017/4/19.
 */
public class RepeatkillException extends  SeckillException {
    public RepeatkillException(String message) {
        super(message);
    }

    public RepeatkillException(String message, Throwable cause) {
        super(message, cause);
    }
}
