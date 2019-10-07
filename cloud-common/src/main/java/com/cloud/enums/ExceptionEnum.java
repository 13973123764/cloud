package com.cloud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zf
 * @date 2019-09-27-23:01
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum
{

    CATEGORY_NOT_FOUND(404,"分类不存在"),
    BRAND_NOT_FOUND(404,"品牌未找到"),
    SAVE_BRAND_ERROR(500,"新增品牌失败"),
    SYSTEM_ERROR(500,"服务器错误"),
    FILE_UPLOAD_ERROR(500,"文件上传失败"),
    FILE_TYPE_ERROR(500, "无效的文件类型"),
    GOODS_NOT_FOUND(404, "商品不存在"),
    SPEC_GROUP_NOT_FOUND(404, "规格组不存在"),
    SAVE_GOODS_ERROR(500, "新增商品失败"),
    GOODS_SKU_NOT_FOUND(404, "商品明细不存在"),
    GOODS_STOCK_NOT_FOUND(404, "商品库存不存在"),
    GOODS_UPDATE_ERROR(500, "商品修改失败"),
    GOODS_ID_ERROR(404,"商品id不能为空"),
    INVALID_USER_DATA(400,"请求参数有误"),
    USER_NOT_FOUND(404,"用户不存在"),
    INVALID_PASSWORD(404,"密码错误"),
    INVALID_CODE(400,"无效的验证码"),
    CREATE_ORDER_ERROR(500, "创建订单失败"),
    STOCK_NOT_ENOUGH(404,"库存不足")


    ;
    int status;
    String msg;

}
