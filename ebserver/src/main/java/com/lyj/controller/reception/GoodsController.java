package com.lyj.controller.reception;

import com.lyj.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
/*    @GetMapping("getAdvertisementInfo")
    public String getAdvertisementInfo(Model model){
        return goodsService.findAdvertisement(model)
    }*/

}
