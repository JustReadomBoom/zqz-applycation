package com.zqz.service.houseLoan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Author: zqz
 * @CreateTime: 2024/07/24
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class LoanDetailService {


    public void calculate() {
        //贷款总年数
        int yearNum = 30;
        //商贷贷款总金额
        double businessLoanAmt = 690000;
        //商贷年利率
        double businessLoanRate = 0.0365;
        //商贷月利率
        double businessMonthRate = businessLoanRate / 12;

        //公积金贷款总金额
        double reservedLoanAmt = 500000;
        //公积金年利率
        double reservedLoanRate = 0.031;
        //公积金月利率
        double reservedMonthRate = reservedLoanRate / 12;

        //1.先计算商贷
        double businessPrincipal = getPrincipal(yearNum, businessLoanAmt);
        //商贷等额本金月还款利息
        double businessAllPrincipalInterest = 0.0;

        //再计算公积金
        double reservedPrincipal = getPrincipal(yearNum, reservedLoanAmt);
        double reservedAllPrincipalInterest = 0.0;
        for (int month = 1; month <= yearNum * 12; month++) {
            //这里有金额损失,可以前面所有期都使用去尾法保留指定位数的小数点,然后最后一期按照总贷款金额减去前面所有贷款本金，计算出最后一期应还贷款本金
            //利息的小数点统一按照四舍五入的方式保留小数
            double businessPrincipalInterest = getPrincipalInterest(yearNum, businessLoanAmt, month, businessMonthRate);
            businessAllPrincipalInterest += businessPrincipalInterest;
            log.info("【商贷】第{}个月等额本金还款本金:{}, 利息:{}, 当月总还款额:{}", month, businessPrincipal, businessPrincipalInterest, (businessPrincipal + businessPrincipalInterest));
            double reservedPrincipalInterest = getPrincipalInterest(yearNum, reservedLoanAmt, month, reservedMonthRate);
            reservedAllPrincipalInterest += reservedPrincipalInterest;
            log.info("【公积金】第{}个月等额本金还款本金:{}, 利息:{}, 当月总还款额:{}", month, reservedPrincipal, reservedPrincipalInterest, (reservedPrincipal + reservedPrincipalInterest));
            log.info("第{}个月应还总金额:{}", month, (businessPrincipal + businessPrincipalInterest + reservedPrincipal + reservedPrincipalInterest));
        }
        log.info("【商贷】贷款总额:{}, 贷款年限:{}, 等额本金总利息:{}", businessLoanAmt, yearNum, businessAllPrincipalInterest);
        log.info("【公积金】贷款总额:{}, 贷款年限:{}, 等额本金总利息:{}", reservedLoanAmt, yearNum, reservedAllPrincipalInterest);
    }


    /**
     * 当月还款本金
     **/
    public static double getPrincipal(int yearNum, double allLoan) {
        //月均本金
        return allLoan / (yearNum * 12);
    }

    /**
     * 等额本金 还款月当月利息
     * repaymentMonth          还款月
     * 每月还款金额=(贷款本金/还款月数)+(本金—已归还本金累计额)×每月利率
     ***/
    public static double getPrincipalInterest(int yearNum, double allLoan, int repaymentMonth, double ratio) {
        //计算剩余还款月
        int remainingRepaymentMonth = yearNum * 12 - repaymentMonth;
        //计算剩余贷款本金
        double remainingLoan = getPrincipal(yearNum, allLoan) * remainingRepaymentMonth;
        return remainingLoan * ratio;
    }

    /**
     * 等额本息 每月还款额
     * 等额本息每月还款额 = [贷款本金 × 月利率 × (1 + 月利率)^贷款期数] / [(1 + 月利率)^贷款期数 - 1]
     */
    public static double getAverageCapitalPayAmount(int yearNum, double allLoan, double ratio) {
        //贷款月
        int payMonth = yearNum * 12;
        //计算临时利率
        double tmpRatio = Math.pow(1 + ratio, payMonth);
        return allLoan * ratio * tmpRatio / (tmpRatio - 1);
    }


    /***
     *
     * @param principal     等额本金月还款本金
     * @param averageCapitalPayAmount   等额本金月还款金额
     * @return
     */
    public static double getAverageCapitalPayAmountInterest(double principal, double averageCapitalPayAmount) {
        return averageCapitalPayAmount - principal;
    }


}
