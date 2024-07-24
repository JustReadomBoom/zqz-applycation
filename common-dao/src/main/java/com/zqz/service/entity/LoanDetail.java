package com.zqz.service.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zqz
 * @CreateTime: 2024/07/24
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class LoanDetail {

    private Integer id;

    /**
     * 月份
     */
    private Integer monthId;

    /**
     * 贷款年限
     */
    private Integer yearNum;

    /**
     * 商贷金额
     */
    private BigDecimal businessLoanAmount;

    /**
     * 公积金贷款金额
     */
    private BigDecimal reservedLoanAmount;

    /**
     * 商贷(等额本息)年利率
     */
    private BigDecimal businessEqualInterestRate;

    /**
     * 商贷(等额本息)当月应还本金
     */
    private BigDecimal businessEqualInterestPrincipal;

    /**
     * 商贷(等额本息)当月应还利息
     */
    private BigDecimal businessEqualInterestInterest;

    /**
     * 商贷(等额本息)当月应还总金额
     */
    private BigDecimal businessEqualInterestTotal;

    /**
     * 商贷(等额本金)年利率
     */
    private BigDecimal businessEqualPrincipalRate;

    /**
     * 商贷(等额本金)当月应还本金
     */
    private BigDecimal businessEqualPrincipalPrincipal;

    /**
     * 商贷(等额本金)当月应还利息
     */
    private BigDecimal businessEqualPrincipalInterest;

    /**
     * 商贷(等额本金)当月应还总金额
     */
    private BigDecimal businessEqualPrincipalTotal;

    /**
     * 公积金(等额本息)年利率
     */
    private BigDecimal reservedEqualInterestRate;

    /**
     * 公积金(等额本息)当月应还本金
     */
    private BigDecimal reservedEqualInterestPrincipal;

    /**
     * 公积金(等额本息)当月应还利息
     */
    private BigDecimal reservedEqualInterestInterest;

    /**
     * 公积金(等额本息)当月应还总金额
     */
    private BigDecimal reservedEqualInterestTotal;

    /**
     * 公积金(等额本金)年利率
     */
    private BigDecimal reservedEqualPrincipalRate;

    /**
     * 公积金(等额本金)当月应还本金
     */
    private BigDecimal reservedEqualPrincipalPrincipal;

    /**
     * 公积金(等额本金)当月应还利息
     */
    private BigDecimal reservedEqualPrincipalInterest;

    /**
     * 公积金(等额本金)当月应还总金额
     */
    private BigDecimal reservedEqualPrincipalTotal;

    /**
     * 创建时间
     */
    private Date createTime;


}
