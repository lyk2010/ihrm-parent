package com.ihrm.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应对象
 *  {
 *      success ：是否成功
 *      code    ：返回码
 *      message ：返回信息
 *      //返回数据
 *      data：  ：{
 *
 *         }
 *       }
 */
@Data
@NoArgsConstructor
public class Result {

    private Boolean success;//是否成功

    private Integer code;//返回码

    private String msg;//提示信息

    private Object data;//返回数据


    public Result(ResultCode code){
        this.success = code.success;
        this.code = code.code;
        this.msg = code.msg;
    }


    public Result(ResultCode code,Object data){
        this.success = code.success;
        this.code = code.code;
        this.msg = code.msg;
        this.data = data;
    }

    public Result(Integer code,String msg,boolean success){
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }


    public static Result ERROR(){
        return new Result(ResultCode.SERVER_ERROR);
    }

}