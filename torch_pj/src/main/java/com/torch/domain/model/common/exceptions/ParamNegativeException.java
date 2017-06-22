package com.torch.domain.model.common.exceptions;

/**
 * Created by Administrator on 2017/2/9.
 */
public class ParamNegativeException extends RuntimeException{

    private int num;

    public ParamNegativeException (int num){
        this.num=num;
    }

    public static ParamNegativeException paramNegativeException(int num){
        return new ParamNegativeException(num);
    }
}
