package com.ylt.common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OperationResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean _success;
    private List<String> _errs;
    public static final OperationResult SUCCEED = new OperationResult();

    private OperationResult() {
        this._success = true;
    }

    public OperationResult(String... errors) {
        this((Collection)Arrays.asList(errors));
    }

    public OperationResult(Collection<String> errors) {
        if (null == errors) {
            errors = Arrays.asList("发生未知错误。");
        }

        this._success = false;
        this._errs = new ArrayList((Collection)errors);
    }

    public boolean isSuccess() {
        return this._success;
    }

    public List<String> getErrors() {
        return this._errs;
    }

    public static final OperationResult failed(String error) {
        return new OperationResult(new String[]{error});
    }

    public static final OperationResult failed(String... errors) {
        return failed((Collection)Arrays.asList(errors));
    }

    public static final OperationResult failed(Collection<String> errors) {
        return new OperationResult(errors);
    }

    public void addErrors(String... errors) {
        this._errs.addAll(Arrays.asList(errors));
    }
}
