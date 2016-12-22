package com.bin.entity;

import com.bin.util.AttributeUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 优惠券表
 * Created by xiongzhe on 2016/11/03.
 */
public class CouponDO  {
    private static final long serialVersionUID = 3522223333019547165L;
    /**
     * 唯一id
     */
    private Long id;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 1: 折扣券 2:代金券
     */
    private Integer couponType;
    /**
     * 优惠券额度(分>100/折<100)
     */
    private Long couponAmt;
    /**
     * 有效期开始时间
     */
    private Date startTime;
    /**
     * 有效期结束时间
     */
    private Date endTime;
    /**
     * '优惠券状态 1:正常 2:停用(正常终止) 3:禁用(异常终止) 4:过期'
     */
    private Integer status;
    /**
     * 优惠劵最多发放张数
     */
    private Long maxCount;
    /**
     * 自动发放操作类型 1:充值，2:支付, 3:注册
     */
    private String triggerType;
    /**
     * 充值自动发放最小费用
     */
    private Long rechargeMinFee;
    /**
     * 充值可重复触发标志(0:不可 1:可以)
     */
    private Integer rechargeRepeatTriggerFlag;
    /**
     * 支付自动发放最小费用
     */
    private Long payMinFee;
    /**
     * 支付可重复触发标志(0:不可 1:可以)
     */
    private Integer payRepeatTriggerFlag;
    /**
     * 使用下限金额
     */
    private Long useMinFee;
    /**
     * 使用上限金额
     */
    private Long useMaxFee;
    /**
     * 支持业务类型
     */
    private String useBizType;
    /**
     * 创建人编码
     */
    private String createCode;
    /**
     * 修改人编码
     */
    private String modifyCode;

    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 属性
     */
    private Map<String, String> attributes = Maps.newHashMap();
    /**
     * 乐观锁控制
     */
    private Integer version;
    /**
     * 可见 visible=1
     * 不可见 visible=2
     */
    private int visible;


    public CouponDO() {
    }

    public Long getId() {
        return id;
    }

    public CouponDO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public CouponDO setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public CouponDO setCouponType(Integer couponType) {
        this.couponType = couponType;
        return this;
    }

    public Long getCouponAmt() {
        return couponAmt;
    }

    public CouponDO setCouponAmt(Long couponAmt) {
        this.couponAmt = couponAmt;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public CouponDO setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public CouponDO setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public CouponDO setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Long getMaxCount() {
        return maxCount;
    }

    public CouponDO setMaxCount(Long maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public CouponDO setTriggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public Long getRechargeMinFee() {
        return rechargeMinFee;
    }

    public CouponDO setRechargeMinFee(Long rechargeMinFee) {
        this.rechargeMinFee = rechargeMinFee;
        return this;
    }

    public Integer getRechargeRepeatTriggerFlag() {
        return rechargeRepeatTriggerFlag;
    }

    public CouponDO setRechargeRepeatTriggerFlag(Integer rechargeRepeatTriggerFlag) {
        this.rechargeRepeatTriggerFlag = rechargeRepeatTriggerFlag;
        return this;
    }

    public Long getPayMinFee() {
        return payMinFee;
    }

    public CouponDO setPayMinFee(Long payMinFee) {
        this.payMinFee = payMinFee;
        return this;
    }

    public Integer getPayRepeatTriggerFlag() {
        return payRepeatTriggerFlag;
    }

    public CouponDO setPayRepeatTriggerFlag(Integer payRepeatTriggerFlag) {
        this.payRepeatTriggerFlag = payRepeatTriggerFlag;
        return this;
    }

    public Long getUseMinFee() {
        return useMinFee;
    }

    public CouponDO setUseMinFee(Long useMinFee) {
        this.useMinFee = useMinFee;
        return this;
    }

    public Long getUseMaxFee() {
        return useMaxFee;
    }

    public CouponDO setUseMaxFee(Long useMaxFee) {
        this.useMaxFee = useMaxFee;
        return this;
    }

    public String getUseBizType() {
        return useBizType;
    }

    public CouponDO setUseBizType(String useBizType) {
        this.useBizType = useBizType;
        return this;
    }

    public String getCreateCode() {
        return createCode;
    }

    public CouponDO setCreateCode(String createCode) {
        this.createCode = createCode;
        return this;
    }

    public String getModifyCode() {
        return modifyCode;
    }

    public CouponDO setModifyCode(String modifyCode) {
        this.modifyCode = modifyCode;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public CouponDO setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public CouponDO setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public CouponDO setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public CouponDO setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public int getVisible() {
        return visible;
    }

    public CouponDO setVisible(int visible) {
        this.visible = visible;
        return this;
    }



    /**
     * 获取单个属性
     *
     * @param name
     * @return
     */
    public String getAttribute(String name) {
        return this.attributes.get(name);
    }

    /**
     * 增加单个属性
     *
     * @param name
     * @param value
     */
    public void addAttribute(String name, String value) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("attribute name is null");
        }

        if (null == this.attributes) {
            this.attributes = Maps.newHashMap();
        }
        this.attributes.put(name, value);
    }

    /**
     * 增加批量属性
     *
     * @param attributes
     */
    public void addAttributes(Map<String, String> attributes) {
        if (null == attributes) {
            return;
        }

        if (null == this.attributes) {
            this.attributes = Maps.newHashMap();
        }

        this.attributes.putAll(attributes);
    }

    /**
     * 获取所有的属性 key
     *
     * @return
     */
    public Set<String> getAttributesKeys() {
        if (null == this.attributes) {
            this.attributes = Maps.newHashMap();
        }
        return this.attributes.keySet();
    }

    /**
     * 获取属性字符串,系统使用
     *
     * @return
     */
    public String getAttributeString() {
        return AttributeUtil.toString(this.attributes);
    }

    /**
     * 设置属性字符串,系统使用
     *
     * @param str
     */
    public void setAttributeString(String str) {
        this.attributes.putAll(AttributeUtil.fromString(str));
    }

    @Override
    public String toString() {
        return "CouponDO{" +
                "id=" + id +
                ", couponName='" + couponName + '\'' +
                ", couponType=" + couponType +
                ", couponAmt=" + couponAmt +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", maxCount=" + maxCount +
                ", triggerType='" + triggerType + '\'' +
                ", rechargeMinFee=" + rechargeMinFee +
                ", rechargeRepeatTriggerFlag=" + rechargeRepeatTriggerFlag +
                ", payMinFee=" + payMinFee +
                ", payRepeatTriggerFlag=" + payRepeatTriggerFlag +
                ", useMinFee=" + useMinFee +
                ", useMaxFee=" + useMaxFee +
                ", useBizType='" + useBizType + '\'' +
                ", createCode='" + createCode + '\'' +
                ", modifyCode='" + modifyCode + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", attributes=" + attributes +
                ", version=" + version +
                ", visible=" + visible +
                '}';
    }
}
