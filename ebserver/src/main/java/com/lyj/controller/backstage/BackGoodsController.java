package com.lyj.controller.backstage;

import com.lyj.entity.Goods;
import com.lyj.service.GoodsService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("goods")
public class BackGoodsController {
    public static final Integer PAGESIZE = 6;//页面大小

    @Value("${saveImage.path}")
    private String path;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("selectAllTypeByPage")
    public String selectAllTypeByPage(Integer currentPage, Model model) {
        return goodsService.selectAllGoodsTypeByPage(currentPage, PAGESIZE, model);
    }

    @PostMapping("checkDeleteGoodsType")
    @ResponseBody
    public JSONResult<Void> checkDeleteGoodsType(Integer id) {
        return goodsService.checkDeleteGoodsType(id);
    }

    @RequestMapping("deleteGoodsType")
    public String deleteGoodsType(Integer id) {
        return goodsService.deleteGoodsTypeById(id);
    }

    @PostMapping("checkTypeName")
    @ResponseBody
    public JSONResult<Void> checkTypeName(String typeName) {
        return goodsService.checkTypeName(typeName);
    }

    @PostMapping("addGoodsType")
    public String addGoodsType(String typeName) {
        return goodsService.addGoodsType(typeName);
    }

    @RequestMapping("selectAllGoodsByPage")
    public String selectAllGoodsByPage(String act, Integer currentPage, Model model) {
        return goodsService.selectAllGoodsByPage(act, currentPage, PAGESIZE, model);
    }

    @RequestMapping("detail")
    public String goodsDetail(Integer id, String act, Model model) {
        return goodsService.goodsDetail(id, act, model);
    }

    @RequestMapping("deleteGoodsById")
    public String deleteGoodsById(Integer id) {
        return goodsService.deleteGoodsById(id);
    }
    @PostMapping("updateGoods")
    public String updateGoods(Goods goods){
        return goodsService.updateGoods(goods);
    }
    @PostMapping("addGoods")
    public String addGoods(Goods goods){
        return goodsService.addGoods(goods);
    }

    /**
     * 商品管理 修改 图片上传
     **/
    @RequestMapping("goodImgUpload")
    @ResponseBody
    public Object[] goodImgUpload(MultipartFile fileUpload,MultipartFile fileUpload1)  {
        return goodsService.goodImgUpload( fileUpload, fileUpload1 ,path);
    }
}
