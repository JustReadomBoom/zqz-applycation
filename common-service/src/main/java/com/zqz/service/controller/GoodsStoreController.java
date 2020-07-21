package com.zqz.service.controller;

import com.zqz.service.biz.GoodsStoreBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 3:48 PM 2020/7/21
 */
@Controller
@RequestMapping("/")
public class GoodsStoreController {

    @Autowired
    private GoodsStoreBizService goodsStoreBizService;

    @GetMapping("test")
    public ModelAndView stepOne(Model model) {
        return new ModelAndView("test", "model", model);
    }

    @PostMapping("secKill")
    @ResponseBody
    public String secKill(@RequestParam(value = "code", required = true) String code, @RequestParam(value = "num", required = true) Integer num) {
        return goodsStoreBizService.updateGoodsStore(code, num);
    }
}
