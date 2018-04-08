package com.xiaoyu.lingdian.vo;

import com.xiaoyu.lingdian.entity.CoreUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 去提现信息
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
@ApiModel(value = "去提现信息")
public class UserPromoterInfoVO implements BaseVO {

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String crusrMobile;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String crusrIdCard;

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
     * 解冻佣金总额
     */
    @ApiModelProperty(value = "解冻佣金总额")
    private Double crusrEnableIncome;

    /**
     * 冻结佣金总额
     */
    @ApiModelProperty(value = "冻结佣金总额")
    private Double crusrFrozenIncome;

    public String getCrusrMobile() {
        return crusrMobile;
    }

    public void setCrusrMobile(String crusrMobile) {
        this.crusrMobile = crusrMobile;
    }

    public String getCrusrIdCard() {
        return crusrIdCard;
    }

    public void setCrusrIdCard(String crusrIdCard) {
        this.crusrIdCard = crusrIdCard;
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

    public UserPromoterInfoVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreUser po = (CoreUser) poObj;
        this.crusrMobile = po.getCrusrMobile();
        this.crusrIdCard = po.getCrusrIdCard();
        this.crusrAccountNo = po.getCrusrAccountNo();
        this.crusrBankName = po.getCrusrBankName();
        this.crusrBankSite = po.getCrusrBankSite();
        this.crusrEnableIncome = po.getCrusrEnableIncome();
        this.crusrFrozenIncome = po.getCrusrFrozenIncome();
    }
}