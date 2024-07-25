package com.zqz;

import org.junit.Test;

/**
 * @Author: zqz
 * @CreateTime: 2024/07/24
 * @Description: TODO
 * @Version: 1.0
 */
public class FangTest {
    @Test
    public void test1() {
        //贷款总年数
        int yearNum = 30;
        //贷款总金额
        double allLoan = 500000;
        //月利率
        double ratio = 0.031/12;
        double principal = getPrincipal(yearNum, allLoan);
        //等额本金
        double allPrincipalInterest = 0.0;
        for (int month = 1; month <= yearNum * 12; month++) {
            //这里有金额损失,可以前面所有期都使用去尾法保留指定位数的小数点,然后最后一期按照总贷款金额减去前面所有贷款本金，计算出最后一期应还贷款本金
            //利息的小数点统一按照四舍五入的方式保留小数
            double principalInterest = getPrincipalInterest(yearNum, allLoan, month, ratio);
            allPrincipalInterest += principalInterest;

            System.out.println("第" + month + "个月等额本金还款本金:" + principal + ";利息:" + principalInterest + ",当月总还款额:" + (principal + principalInterest));
            System.out.println(" ");
        }
        System.out.println("贷款总额:" + allLoan + ";贷款时间:" + yearNum + "年;等额本金总利息:" + allPrincipalInterest);
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
