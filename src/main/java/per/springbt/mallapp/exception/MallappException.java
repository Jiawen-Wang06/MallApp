package per.springbt.mallapp.exception;

public class MallappException extends RuntimeException {
    private final Integer code;
    private final String msg;
    public MallappException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public MallappException(MallappExceptionEnum e) {
        this(e.getCode(), e.getMsg());
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}