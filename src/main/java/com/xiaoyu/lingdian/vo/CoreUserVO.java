package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreUser;

import java.util.Date;

/**
 * 用户表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
@ApiModel(value = "用户")
public class CoreUserVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crusrUuid;

    /**
     * 持卡人姓名/公司名
     */
    @ApiModelProperty(value = "持卡人姓名/公司名")
    private String crusrName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String crusrMobile;

    /**
     * 状态:1启用,0禁用
     */
    @ApiModelProperty(value = "状态:1启用,0禁用")
    private Integer crusrStatus;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期[成为下线时间]")
    private Date crusrCdate;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    private Date crusrUdate;

    /**
     * 性别:1男,2女,0其它
     */
    @ApiModelProperty(value = "性别:1男,2女,0其它")
    private Integer crusrGender;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String crusrAddress;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String crusrRemarks;

    /**
     * 授权OPENID
     */
    @ApiModelProperty(value = "授权OPENID")
    private String crusrOpenid;

    /**
     * 微信用户的昵称
     */
    @ApiModelProperty(value = "微信用户的昵称")
    private String crusrWxNickname;

    /**
     * 微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @ApiModelProperty(value = "微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    private String crusrWxSex;

    /**
     * 微信用户所在城市
     */
    @ApiModelProperty(value = "微信用户所在城市")
    private String crusrWxCity;

    /**
     * 微信用户所在国家
     */
    @ApiModelProperty(value = "微信用户所在国家")
    private String crusrWxCountry;

    /**
     * 微信用户所在省份
     */
    @ApiModelProperty(value = "微信用户所在省份")
    private String crusrWxProvince;

    /**
     * 微信用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    @ApiModelProperty(value = "微信用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空")
    private String crusrWxHeadimgurl;

    /**
     * 用户UnionID
     */
    @ApiModelProperty(value = "用户UnionID")
    private String crusrUnionid;

    /**
     * 邀请二维码
     */
    @ApiModelProperty(value = "邀请二维码")
    private String crusrInvitedCode;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String crusrIdCard;

    /**
     * 银行账号名称
     */
    @ApiModelProperty(value = "银行账号名称")
    private String crusrAccountName;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    private String crusrAccountNo;

    /**
     * 银行开户行
     */
    @ApiModelProperty(value = "银行开户行")
    private String crusrBankName;

    /**
     * 银行开户网点
     */
    @ApiModelProperty(value = "银行开户网点")
    private String crusrBankSite;

    /**
     * 行驶证照片正面
     */
    @ApiModelProperty(value = "行驶证照片正面")
    private String crusrDrivingPic;

    /**
     * 行驶证照片反面
     */
    @ApiModelProperty(value = "行驶证照片反面")
    private String crusrDrivingPicOther;

    /**
     * 车牌
     */
    @ApiModelProperty(value = "车牌")
    private String crusrCarNo;

    /**
     * 行驶证号
     */
    @ApiModelProperty(value = "行驶证号")
    private String crusrDrivingNo;

    /**
     * 营业执照号
     */
    @ApiModelProperty(value = "营业执照号")
    private String crusrBusinessNo;

    /**
     * 身份（1个人2公司）
     */
    @ApiModelProperty(value = "身份（1个人2公司3未知）")
    private Integer crusrType;

    /**
     * 本周佣金总额
     */
    @ApiModelProperty(value = "本周佣金总额")
    private Double crusrWeekIncome;

    /**
     * 累积佣金总额
     */
    @ApiModelProperty(value = "累积佣金总额")
    private Double crusrTotalIncome;

    /**
     * 解冻佣金总额
     */
    @ApiModelProperty(value = "解冻佣金总额")
    private Double crusrEnableIncome;

    /**
     * 冻结佣金总额
     */
    @ApiModelProperty(value = "冻结佣金总额")
    private Double crusrFrozenIncome;

    /**
     * 下级成员数量
     */
    @ApiModelProperty(value = "下级成员数量")
    private Integer crusrDownCount;

    /**
     * 邀请人
     */
    @ApiModelProperty(value = "邀请人")
    private String crusrInviter;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date crusrLastTime;

    /**
     * 邀请码（注册时即产生的20位随机码）
     */
    @ApiModelProperty(value = "邀请码（注册时即产生的20位随机码）")
    private String crusrInviteCode;

    /**
     * 是否已完善1为已完善2为未完善
     */
    @ApiModelProperty(value = "是否已完善1为已完善2为未完善")
    private Integer isComplete;

    /**
     * 所属代理商平台(1本平台)
     */
    @ApiModelProperty(value = "所属代理商平台(1本平台)")
    private String crusrAgent;

    /**
     * 优惠券总和
     */
    @ApiModelProperty(value = "优惠券总和")
    private Integer crusrCouponSum;

    public String getCrusrUuid() {
        return crusrUuid;
    }

    public void setCrusrUuid(String crusrUuid) {
        this.crusrUuid = crusrUuid;
    }

    public String getCrusrName() {
        return crusrName;
    }

    public void setCrusrName(String crusrName) {
        this.crusrName = crusrName;
    }

    public String getCrusrMobile() {
        return crusrMobile;
    }

    public void setCrusrMobile(String crusrMobile) {
        this.crusrMobile = crusrMobile;
    }

    public Integer getCrusrStatus() {
        return crusrStatus;
    }

    public void setCrusrStatus(Integer crusrStatus) {
        this.crusrStatus = crusrStatus;
    }

    public Date getCrusrCdate() {
        return crusrCdate;
    }

    public void setCrusrCdate(Date crusrCdate) {
        this.crusrCdate = crusrCdate;
    }

    public Date getCrusrUdate() {
        return crusrUdate;
    }

    public void setCrusrUdate(Date crusrUdate) {
        this.crusrUdate = crusrUdate;
    }

    public Integer getCrusrGender() {
        return crusrGender;
    }

    public void setCrusrGender(Integer crusrGender) {
        this.crusrGender = crusrGender;
    }

    public String getCrusrAddress() {
        return crusrAddress;
    }

    public void setCrusrAddress(String crusrAddress) {
        this.crusrAddress = crusrAddress;
    }

    public String getCrusrRemarks() {
        return crusrRemarks;
    }

    public void setCrusrRemarks(String crusrRemarks) {
        this.crusrRemarks = crusrRemarks;
    }

    public String getCrusrOpenid() {
        return crusrOpenid;
    }

    public void setCrusrOpenid(String crusrOpenid) {
        this.crusrOpenid = crusrOpenid;
    }

    public String getCrusrWxNickname() {
        return crusrWxNickname;
    }

    public void setCrusrWxNickname(String crusrWxNickname) {
        this.crusrWxNickname = crusrWxNickname;
    }

    public String getCrusrWxSex() {
        return crusrWxSex;
    }

    public void setCrusrWxSex(String crusrWxSex) {
        this.crusrWxSex = crusrWxSex;
    }

    public String getCrusrWxCity() {
        return crusrWxCity;
    }

    public void setCrusrWxCity(String crusrWxCity) {
        this.crusrWxCity = crusrWxCity;
    }

    public String getCrusrWxCountry() {
        return crusrWxCountry;
    }

    public void setCrusrWxCountry(String crusrWxCountry) {
        this.crusrWxCountry = crusrWxCountry;
    }

    public String getCrusrWxProvince() {
        return crusrWxProvince;
    }

    public void setCrusrWxProvince(String crusrWxProvince) {
        this.crusrWxProvince = crusrWxProvince;
    }

    public String getCrusrWxHeadimgurl() {
        return crusrWxHeadimgurl;
    }

    public void setCrusrWxHeadimgurl(String crusrWxHeadimgurl) {
        this.crusrWxHeadimgurl = crusrWxHeadimgurl;
    }

    public String getCrusrIdCard() {
        return crusrIdCard;
    }

    public void setCrusrIdCard(String crusrIdCard) {
        this.crusrIdCard = crusrIdCard;
    }

    public String getCrusrAccountName() {
        return crusrAccountName;
    }

    public void setCrusrAccountName(String crusrAccountName) {
        this.crusrAccountName = crusrAccountName;
    }

    public String getCrusrAccountNo() {
        return crusrAccountNo;
    }

    public void setCrusrAccountNo(String crusrAccountNo) {
        this.crusrAccountNo = crusrAccountNo;
    }

    public String getCrusrBankName() {
        return crusrBankName;
    }

    public void setCrusrBankName(String crusrBankName) {
        this.crusrBankName = crusrBankName;
    }

    public String getCrusrBankSite() {
        return crusrBankSite;
    }

    public void setCrusrBankSite(String crusrBankSite) {
        this.crusrBankSite = crusrBankSite;
    }

    public String getCrusrDrivingPic() {
        return crusrDrivingPic;
    }

    public void setCrusrDrivingPic(String crusrDrivingPic) {
        this.crusrDrivingPic = crusrDrivingPic;
    }

    public Double getCrusrWeekIncome() {
        return crusrWeekIncome;
    }

    public void setCrusrWeekIncome(Double crusrWeekIncome) {
        this.crusrWeekIncome = crusrWeekIncome;
    }

    public Double getCrusrTotalIncome() {
        return crusrTotalIncome;
    }

    public void setCrusrTotalIncome(Double crusrTotalIncome) {
        this.crusrTotalIncome = crusrTotalIncome;
    }

    public Double getCrusrEnableIncome() {
        return crusrEnableIncome;
    }

    public void setCrusrEnableIncome(Double crusrEnableIncome) {
        this.crusrEnableIncome = crusrEnableIncome;
    }

    public Double getCrusrFrozenIncome() {
        return crusrFrozenIncome;
    }

    public void setCrusrFrozenIncome(Double crusrFrozenIncome) {
        this.crusrFrozenIncome = crusrFrozenIncome;
    }

    public Integer getCrusrDownCount() {
        return crusrDownCount;
    }

    public void setCrusrDownCount(Integer crusrDownCount) {
        this.crusrDownCount = crusrDownCount;
    }

    public String getCrusrInviter() {
        return crusrInviter;
    }

    public void setCrusrInviter(String crusrInviter) {
        this.crusrInviter = crusrInviter;
    }

    public Date getCrusrLastTime() {
        return crusrLastTime;
    }

    public void setCrusrLastTime(Date crusrLastTime) {
        this.crusrLastTime = crusrLastTime;
    }

    public String getCrusrInviteCode() {
        return crusrInviteCode;
    }

    public void setCrusrInviteCode(String crusrInviteCode) {
        this.crusrInviteCode = crusrInviteCode;
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public String getCrusrAgent() {
        return crusrAgent;
    }

    public void setCrusrAgent(String crusrAgent) {
        this.crusrAgent = crusrAgent;
    }

    public String getCrusrDrivingPicOther() {
        return crusrDrivingPicOther;
    }

    public void setCrusrDrivingPicOther(String crusrDrivingPicOther) {
        this.crusrDrivingPicOther = crusrDrivingPicOther;
    }

    public Integer getCrusrCouponSum() {
        return crusrCouponSum;
    }

    public void setCrusrCouponSum(Integer crusrCouponSum) {
        this.crusrCouponSum = crusrCouponSum;
    }

    public String getCrusrUnionid() {
        return crusrUnionid;
    }

    public void setCrusrUnionid(String crusrUnionid) {
        this.crusrUnionid = crusrUnionid;
    }

    public String getCrusrInvitedCode() {
        return crusrInvitedCode;
    }

    public void setCrusrInvitedCode(String crusrInvitedCode) {
        this.crusrInvitedCode = crusrInvitedCode;
    }

    public String getCrusrCarNo() {
        return crusrCarNo;
    }

    public void setCrusrCarNo(String crusrCarNo) {
        this.crusrCarNo = crusrCarNo;
    }

    public String getCrusrDrivingNo() {
        return crusrDrivingNo;
    }

    public void setCrusrDrivingNo(String crusrDrivingNo) {
        this.crusrDrivingNo = crusrDrivingNo;
    }

    public String getCrusrBusinessNo() {
        return crusrBusinessNo;
    }

    public void setCrusrBusinessNo(String crusrBusinessNo) {
        this.crusrBusinessNo = crusrBusinessNo;
    }

    public Integer getCrusrType() {
        return crusrType;
    }

    public void setCrusrType(Integer crusrType) {
        this.crusrType = crusrType;
    }

    public CoreUserVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreUser po = (CoreUser) poObj;
        this.crusrUuid = po.getCrusrUuid();
        this.crusrName = po.getCrusrName();
        this.crusrMobile = po.getCrusrMobile();
        this.crusrStatus = po.getCrusrStatus();
        this.crusrCdate = po.getCrusrCdate();
        this.crusrUdate = po.getCrusrUdate();
        this.crusrGender = po.getCrusrGender();
        this.crusrAddress = po.getCrusrAddress();
        this.crusrRemarks = po.getCrusrRemarks();
        this.crusrOpenid = po.getCrusrOpenid();
        this.crusrWxNickname = po.getCrusrWxNickname();
        this.crusrWxSex = po.getCrusrWxSex();
        this.crusrWxCity = po.getCrusrWxCity();
        this.crusrWxCountry = po.getCrusrWxCountry();
        this.crusrWxProvince = po.getCrusrWxProvince();
        this.crusrWxHeadimgurl = po.getCrusrWxHeadimgurl();
        this.crusrUnionid = po.getCrusrUnionid();
        this.crusrInvitedCode = po.getCrusrInvitedCode();
        this.crusrIdCard = po.getCrusrIdCard();
        this.crusrAccountName = po.getCrusrAccountName();
        this.crusrAccountNo = po.getCrusrAccountNo();
        this.crusrBankName = po.getCrusrBankName();
        this.crusrBankSite = po.getCrusrBankSite();
        this.crusrDrivingPic = po.getCrusrDrivingPic();
        this.crusrDrivingPicOther = po.getCrusrDrivingPicOther();
        this.crusrWeekIncome = po.getCrusrWeekIncome();
        this.crusrTotalIncome = po.getCrusrTotalIncome();
        this.crusrEnableIncome = po.getCrusrEnableIncome();
        this.crusrFrozenIncome = po.getCrusrFrozenIncome();
        this.crusrDownCount = po.getCrusrDownCount();
        this.crusrInviter = po.getCrusrInviter();
        this.crusrLastTime = po.getCrusrLastTime();
        this.crusrInviteCode = po.getCrusrInviteCode();
        this.isComplete = po.getIsComplete();
        this.crusrAgent = po.getCrusrAgent();
        this.crusrBusinessNo = po.getCrusrBusinessNo();
        this.crusrCarNo = po.getCrusrCarNo();
        this.crusrType = po.getCrusrType();
        this.crusrDrivingNo = po.getCrusrDrivingNo();
    }
}