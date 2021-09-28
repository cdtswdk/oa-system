package com.cdt.common.pojo;


import java.io.Serializable;

/**
 *
 * @author  pandora gen
 * @param <T>
 */
public class DataResult<T> implements Serializable {
    private static final long serialVersionUID = -1954065564856833013L;
    /**
     *  返回码0 -> 成功,401 -> 没有权限,404 -> 查询的对象不存在,408 -> 请求超时,500 -> 未知错误  201~250 -> 业务自定义返回码
     */
    private int code = 0;
    /**
     * 提示语
     */
    private String message = "";
     /**
     * 返回数据
     */
    private T data;

    public DataResult() {
    }

    public static <T> DataResult<T> success(T data, String message) {
        DataResult<T> result = new DataResult<>();
        result.setCode(RespCode.SUCCESS.getCode()).setMessage(message).setData(data);
        return result;
    }

    public static <T> DataResult<T> success(T data) {
        DataResult<T> result = new DataResult<>();
        result.setCode(RespCode.SUCCESS.getCode()).setMessage("success").setData(data);
        return result;
    }

    public static <T> DataResult<T> timeout(String message) {
        DataResult<T> result = new DataResult<>();
        result.setCode(RespCode.TIMEOUT.getCode()).setMessage(message);
        return result;
    }
    public static <T> DataResult<T> notfound(String message) {
        DataResult<T> result = new DataResult<>();
        result.setCode(RespCode.NOTFOUND.getCode()).setMessage(message);
        return result;
    }


    public static <T> DataResult<T> serverError(String message) {
        DataResult<T> result = new DataResult<>();
        result.setCode(RespCode.UNKNOWN_ERROR.getCode()).setMessage(message);
        return result;
    }



    public int getCode() {
        return this.code;
    }

    public DataResult<T> setCode(int code) {
        this.code = code;
        return this;
    }




    public String getMessage() {
        return this.message;
    }

    public DataResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public DataResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
