package com.ylt.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JsonResult {
    private Object data;
    private String message;
    private boolean success;
    private JsonResultType type;
    public static final JsonResult FAILED = new JsonResult(false, (Object)null, (String)null);
    public static final JsonResult SUCCEED = new JsonResult(true, (Object)null, (String)null);

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public JsonResultType getType() {
        return this.type;
    }

    public JsonResult(boolean success, Object data, String message) {
        this.data = data;
        this.success = success;
        this.type = success ? JsonResultType.NORMAL : JsonResultType.FAILED;
        this.message = null != message ? message : (success ? "操作成功。" : "操作失败。");
    }

    public static JsonResult from(OperationResult operationResult) {
        if (operationResult.isSuccess()) {
            return SUCCEED;
        } else {
            List<String> errs = operationResult.getErrors();
            return new JsonResult(false, errs, (String)errs.get(0));
        }
    }

    public static JsonResult from(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return SUCCEED;
        } else {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, String> errMap = new HashMap(errors.size());
            Iterator var3 = errors.iterator();

            while(var3.hasNext()) {
                FieldError error = (FieldError)var3.next();
                errMap.put(error.getField(), error.getDefaultMessage());
            }

            return failed((Object)errMap);
        }
    }

    public static JsonResult from(Page<?> page) {
        return new JsonResult(true, page, (String)null);
    }

    public static JsonResult success(String message) {
        return new JsonResult(true, (Object)null, message);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(true, data, (String)null);
    }

    public static JsonResult success(Object data, String message) {
        return new JsonResult(true, data, message);
    }

    public static JsonResult failed(String message) {
        return new JsonResult(false, (Object)null, message);
    }

    public static JsonResult failed(Object data) {
        return new JsonResult(false, data, (String)null);
    }

    public static JsonResult failed(Object data, String message) {
        return new JsonResult(false, data, message);
    }

    @JsonFormat(
            shape = JsonFormat.Shape.NUMBER
    )
    public enum JsonResultType {
        NORMAL,
        WARRING,
        FAILED,
        MODELERROR;

        private JsonResultType() {
        }
    }
}
