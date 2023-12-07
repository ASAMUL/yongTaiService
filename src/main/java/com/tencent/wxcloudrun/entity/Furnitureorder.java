package com.tencent.wxcloudrun.entity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 家具订单表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FurnitureOrder")
public class Furnitureorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FOId", type = IdType.AUTO)
    private Integer FOId;

    /**
     * 订单编号
     */
    @TableField("FONo")
    private String FONo;

    /**
     * 订单类型（现货订单，预售订单，预览订单）
     */
    @TableField("FOType")
    private String FOType;

    /**
     * 订单金额
     */
    @TableField("FOPrice")
    private BigDecimal FOPrice;

    /**
     * 订单购买家具编号
     */
    @TableField("FOFId")
    private Integer FOFId;

    /**
     * 订单购买配件编号
     */
    @TableField("FOFAId")
    private Integer FOFAId;

    /**
     * 订单折扣价
     */
    @TableField("FODiscountPrice")
    private BigDecimal FODiscountPrice;

    /**
     * 订单实际余款
     */
    @TableField("FOBalance")
    private BigDecimal FOBalance;

    /**
     * 订单状态
     */
    @TableField("FOStatus")
    private String FOStatus;

    /**
     * 支付状态（完成支付，待收尾款）
     */
    @TableField("FOPayStatus")
    private String FOPayStatus;

    /**
     * 支付类型（线上支付，线下支付）
     */
    @TableField("FOPayType")
    private String FOPayType;

    /**
     * 人工减免订单价格
     */
    @TableField("AdjPrice")
    private BigDecimal AdjPrice;

    /**
     * 下单客户账号
     */
    @TableField("FOCId")
    private Integer FOCId;

    /**
     * 客户联系方式
     */
    @TableField("FOCTel")
    private String FOCTel;

    /**
     * 客户收货地址
     */
    @TableField("FOCTDAddress")
    private String FOCTDAddress;

    /**
     * 收货时间
     */
    @TableField("FOCTDTime")
    private Date FOCTDTime;

    /**
     * 收货方式
     */
    @TableField("FOCTDWay")
    private String FOCTDWay;

    /**
     * 客户备注
     */
    @TableField("FORemark")
    private String FORemark;

    /**
     * 创建时间
     */
    @TableField("CreateTime")
    private Date CreateTime;

    /**
     * 创建用户id
     */
    @TableField("CreateUser")
    private String CreateUser;

    /**
     * 更新时间
     */
    @TableField("UpdateTime")
    private Date UpdateTime;

    /**
     * 更新用户id
     */
    @TableField("UpdateUser")
    private String UpdateUser;

    /**
     * 是否删除
     */
    @TableField("IsDeleted")
    private String IsDeleted;

}
