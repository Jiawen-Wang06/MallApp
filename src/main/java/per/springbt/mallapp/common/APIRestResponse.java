package per.springbt.mallapp.common;

import per.springbt.mallapp.exception.MallappExceptionEnum;

public class APIRestResponse<T> {
    private Integer status;
    private String msg;
    private T data;
    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";
    @Override
    public String toString() {
        return "APIRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
    public APIRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public APIRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public APIRestResponse() {
        this(OK_CODE,OK_MSG);
    }
    public static <T> APIRestResponse<T> success(){
        return new APIRestResponse<>();
    }

    public static <T> APIRestResponse<T> success(T result){
        APIRestResponse<T> response = new APIRestResponse<>();
        response.setData(result);
        return response;
    }
    public static<T> APIRestResponse<T> error(MallappExceptionEnum ex){
        return new APIRestResponse<>(ex.getCode(),ex.getMsg());
    }
    public static<T> APIRestResponse<T> error(Integer code, String msg){
        return new APIRestResponse<>(code, msg);
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}