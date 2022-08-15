package com.lyj.service;

import com.lyj.entity.Goods;
import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface GoodsService {
    String showIndexData(Model model, HttpSession session,Integer typeId);

    String getGoodsDetail(Model model ,Integer id);

    String selectGoodsResult(String name, Model model);

    //-----------------------------------------------------------------------------------------------------
    //后台服务

    String selectAllGoodsTypeByPage(Integer page, Integer size, Model model);

    /**
     * 验证该类型可以被删除
     * @param id 商品类型id
     * @return
     */
    JSONResult<Void> checkDeleteGoodsType(Integer id);
    /**
     * 通过id删除商品类型
     * @param id
     * @return
     */
    String deleteGoodsTypeById(Integer id);

    String addGoodsType(String typeName);

    /**
     * 在添加类型时检查类型名的合法性并返回判断结果 （为空？ 已存在？）
     * @param typeName
     * @return
     */
    JSONResult<Void> checkTypeName(String typeName);

    /**
     * 分页查询所有商品
     * @param act          (act : select / update / delete
     * @param page  查询的页码
     * @param model
     * @return
     */
    String selectAllGoodsByPage(String act , Integer page ,Integer size ,Model model);

    /**
     * 根据act 查询相应数据跳转相应页面
     * @param id   商品id
     * @param act  行为： update ，detail
     * @param model
     * @return
     */
    String goodsDetail(Integer id, String act , Model model);

    /**
     * 删除商品
     * @param id 商品id
     * @return
     */
    String deleteGoodsById(Integer id);

    String updateGoods(Goods goods);

    String addGoods(Goods goods);

    String toAddGoods(Model model);

    Object[] goodImgUpload(MultipartFile fileUpload, MultipartFile fileUpload1, String path);






}
