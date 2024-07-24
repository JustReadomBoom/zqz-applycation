package com.zqz.service.houseLoan;

import com.zqz.service.entity.LoanDetail;
import com.zqz.service.mapper.LoanDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zqz
 * @CreateTime: 2024/07/24
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class LoanDetailService {

    @Resource
    private LoanDetailMapper loanDetailMapper;


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
        //商贷等额本金
        double businessAverageCapitalPayAmount = getAverageCapitalPayAmount(yearNum, businessLoanAmt, businessMonthRate);
        //商贷等额本金月还款利息
        double businessAverageCapitalPayAmountInterest = getAverageCapitalPayAmountInterest(businessPrincipal, businessAverageCapitalPayAmount);
        double businessAllPrincipalInterest = 0.0;
        double businessAllAverageCapitalPayAmountInterest = 0.0;


        //再计算公积金
        double reservedPrincipal = getPrincipal(yearNum, reservedLoanAmt);
        //公积金等额本金
        double reservedAverageCapitalPayAmount = getAverageCapitalPayAmount(yearNum, reservedLoanAmt, reservedMonthRate);
        //公积金等额本金月还款利息
        double reservedAverageCapitalPayAmountInterest = getAverageCapitalPayAmountInterest(reservedPrincipal, reservedAverageCapitalPayAmount);
        double reservedAllPrincipalInterest = 0.0;
        double reservedAllAverageCapitalPayAmountInterest = 0.0;


        List<LoanDetail> dataList = new ArrayList<>();
        for (int month = 1; month <= yearNum * 12; month++) {
            //这里有金额损失,可以前面所有期都使用去尾法保留指定位数的小数点,然后最后一期按照总贷款金额减去前面所有贷款本金，计算出最后一期应还贷款本金
            //利息的小数点统一按照四舍五入的方式保留小数
            LoanDetail detail = new LoanDetail();
            detail.setMonthId(month);
            detail.setYearNum(yearNum);
            detail.setBusinessLoanAmount(new BigDecimal(businessLoanAmt));
            detail.setReservedLoanAmount(new BigDecimal(reservedLoanAmt));

            double businessPrincipalInterest = getPrincipalInterest(yearNum, businessLoanAmt, month, businessMonthRate);
            businessAllPrincipalInterest += businessPrincipalInterest;
            businessAllAverageCapitalPayAmountInterest += businessAverageCapitalPayAmountInterest;

            detail.setBusinessEqualInterestRate(new BigDecimal(businessLoanRate));
            detail.setBusinessEqualInterestPrincipal(new BigDecimal(businessPrincipal));
            detail.setBusinessEqualInterestInterest(new BigDecimal(businessPrincipalInterest));
            detail.setBusinessEqualInterestTotal(new BigDecimal(businessPrincipal + businessPrincipalInterest));

            detail.setBusinessEqualPrincipalRate(new BigDecimal(businessLoanRate));
            detail.setBusinessEqualPrincipalPrincipal(new BigDecimal(businessPrincipal));
            detail.setBusinessEqualPrincipalInterest(new BigDecimal(businessAverageCapitalPayAmountInterest));
            detail.setBusinessEqualPrincipalTotal(new BigDecimal(businessPrincipal + businessAverageCapitalPayAmountInterest));

            System.out.println("【商贷】第" + month + "个月等额本息还款本金:" + businessPrincipal + ";利息:" + businessPrincipalInterest + ",当月总还款额:" + (businessPrincipal + businessPrincipalInterest));
            System.out.println("【商贷】第" + month + "个月等额本金还款本金:" + businessPrincipal + ";利息:" + businessAverageCapitalPayAmountInterest + ",当月总还款额:" + (businessPrincipal + businessAverageCapitalPayAmountInterest));
            System.out.println(" ");


            double reservedPrincipalInterest = getPrincipalInterest(yearNum, reservedLoanAmt, month, reservedMonthRate);
            reservedAllPrincipalInterest += reservedPrincipalInterest;
            reservedAllAverageCapitalPayAmountInterest += reservedAverageCapitalPayAmountInterest;

            detail.setReservedEqualInterestRate(new BigDecimal(reservedLoanRate));
            detail.setReservedEqualInterestPrincipal(new BigDecimal(reservedPrincipal));
            detail.setReservedEqualInterestInterest(new BigDecimal(reservedPrincipalInterest));
            detail.setReservedEqualInterestTotal(new BigDecimal(reservedPrincipal + reservedPrincipalInterest));

            detail.setReservedEqualPrincipalRate(new BigDecimal(reservedLoanRate));
            detail.setReservedEqualPrincipalPrincipal(new BigDecimal(reservedPrincipal));
            detail.setReservedEqualPrincipalInterest(new BigDecimal(reservedAverageCapitalPayAmountInterest));
            detail.setReservedEqualPrincipalTotal(new BigDecimal(reservedPrincipal + reservedAverageCapitalPayAmountInterest));

            System.out.println("【公积金】第" + month + "个月等额本息还款本金:" + reservedPrincipal + ";利息:" + reservedPrincipalInterest + ",当月总还款额:" + (reservedPrincipal + reservedPrincipalInterest));
            System.out.println("【公积金】第" + month + "个月等额本金还款本金:" + reservedPrincipal + ";利息:" + reservedAverageCapitalPayAmountInterest + ",当月总还款额:" + (reservedPrincipal + reservedAverageCapitalPayAmountInterest));
            System.out.println(" ");

            dataList.add(detail);
        }
        loanDetailMapper.insertBatch(dataList);

        System.out.println("【商贷】贷款总额:" + businessLoanAmt + ";贷款时间:" + yearNum + "年;等额本息总利息:" + businessAllPrincipalInterest + ";等额本金总利息:" + businessAllAverageCapitalPayAmountInterest);
        System.out.println("【公积金】贷款总额:" + reservedLoanAmt + ";贷款时间:" + yearNum + "年;等额本息总利息:" + reservedAllPrincipalInterest + ";等额本金总利息:" + reservedAllAverageCapitalPayAmountInterest);
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
