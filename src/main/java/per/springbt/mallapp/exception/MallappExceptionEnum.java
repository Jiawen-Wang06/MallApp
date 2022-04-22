package per.springbt.mallapp.exception;

public enum MallappExceptionEnum {
    NEED_USER_NAME(10001, "User name cannot be empty"),
    NEED_PASSWORD(10002, "Need password"),
    PASSWORD_TOO_SHORT(10003, "Password is too short"),
    NAME_EXISTED(10004, "Name already existed"),
    INSERT_FAILED(10005, "Failed to add record into database"),
    INCORRECT_PASSWORD(10006, "Incorrect password"),
    ADD_FAILED(10007, "Add Failed"),
    NEED_TO_LOGIN(10008, "Need to login first"),
    UPDATE_FAIED(10009, "Update Failed"),
    NOT_ADMIN_ROLE(10010, "Don't have admin role"),
    NAME_NOT_NULL(10011, "Parameters can't be null"),
    PARAMETER_ERROR(10012, "Parameters are not correct"),
    DELETE_FAILED(10013, "Delete failed"),
    CREATE_FILE_FAILED(10014, "Create file failed"),
    SYSTEM_ERROR(20000, "System error");
    private Integer code;
    private String msg;
    MallappExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}