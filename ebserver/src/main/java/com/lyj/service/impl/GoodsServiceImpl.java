package com.lyj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Comment;
import com.lyj.entity.GoodType;
import com.lyj.entity.Goods;
import com.lyj.mapper.CommentMapper;
import com.lyj.mapper.GoodTypeMapper;
import com.lyj.mapper.GoodsMapper;
import com.lyj.service.GoodsService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired(required = false)
    private GoodsMapper goodsMapper;
    @Autowired(required = false)
    private GoodTypeMapper goodTypeMapper;
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Override
    public String showIndexData(Model model, HttpSession session , Integer typeId) {

        List<Goods> advertisementList = goodsMapper.selectList(
                new QueryWrapper<Goods>().eq("is_advertisement",1));

        session.setAttribute("advertisementList",advertisementList);
        //商品分类信息
        List<GoodType> goodTypes = goodTypeMapper.selectList(new QueryWrapper<>());
        session.setAttribute("goodTypes",goodTypes);
        List<Goods> recommendGoods;
        List<Goods> lastedGoods;
        if(typeId==null||typeId==0) {
            //查询推荐商品
            recommendGoods = goodsMapper.selectList(
                    new QueryWrapper<Goods>()
                            .eq("is_recommend", 1));

            //查询最新商品
            lastedGoods = goodsMapper.selectList(new QueryWrapper<Goods>().eq("is_advertisement", 0));
        }else {
            recommendGoods = goodsMapper.selectList(
                    new QueryWrapper<Goods>()
                            .eq("is_recommend", 1).eq("goods_type_id",typeId));

            //查询最新商品
            lastedGoods = goodsMapper.selectList(new QueryWrapper<Goods>()
                    .eq("is_advertisement", 0).eq("goods_type_id",typeId));

        }
        model.addAttribute("recommendGoods", recommendGoods);
        model.addAttribute("lastedGoods", lastedGoods);

        return "reception/index";

    }


    @Override
    public String getGoodsDetail(Model model, Integer id) {
        Goods goods = goodsMapper.selectById(id);
        List<Comment> commentList = commentMapper.selectCommentByGoodsId(id);
        model.addAttribute("goods",goods);
        model.addAttribute("typeName",
                goodTypeMapper.selectById(goods.getGoodsTypeId()).getTypeName());
        model.addAttribute("commentList",commentList);
        return "reception/goodsDetail";
    }

    @Override
    public String selectGoodsResult(String name, Model model) {

        List<Goods> recommendGoods = goodsMapper.selectRecommendGoodsByLikeName(name);
        List<Goods> lastedGoods = goodsMapper.selectGoodsByLikeName(name);

        model.addAttribute("recommendGoods", recommendGoods);
        model.addAttribute("lastedGoods", lastedGoods);

        return "reception/index";
    }


    //----------------------------------------------------------------------------
    //后台服务


    @Override
    public String selectAllGoodsTypeByPage(Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<GoodType> goodTypeList = goodTypeMapper.selectGoodsTypeByPage(index, size);
        Integer nums = goodTypeMapper.countAll();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);

        model.addAttribute("goodTypeList", goodTypeList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "backstage/selectGoodsType";
    }

    @Override
    public JSONResult<Void> checkDeleteGoodsType(Integer id) {

        List<Goods> goodsList = goodsMapper.selectList(new QueryWrapper<Goods>()
                .eq("goods_type_id", id));
        if (goodsList == null || goodsList.size() == 0) {
            return new JSONResult<>(200, "可以删除");
        }
        return new JSONResult<>(201, "该类型下有商品，不能删除");
    }

    @Override
    public String deleteGoodsTypeById(Integer id) {
        goodTypeMapper.deleteById(id);
        return "redirect:/goods/selectAllTypeByPage";
    }


    @Override
    public String addGoodsType(String typeName) {
        GoodType goodType = new GoodType().setTypeName(typeName);
        goodTypeMapper.insert(goodType);
        return "redirect:/goods/selectAllTypeByPage";
    }

    @Override
    public JSONResult<Void> checkTypeName(String typeName) {
        if (typeName == null || typeName.equals("")) {
            return new JSONResult<>(201, "类型名名称不能为空");
        }
        if (goodTypeMapper.selectOne(new QueryWrapper<GoodType>()
                .eq("type_name", typeName)) != null) {
            return new JSONResult<>(202, "类型名称已存在，请勿重复添加");
        }
        return new JSONResult<>(200, "类型名称可用");
    }

    @Override
    public String selectAllGoodsByPage(String act, Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<Goods> goodsList = goodsMapper.selectGoodsByPage(index, size);
        Integer nums = goodsMapper.countAll();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);

        model.addAttribute("goodTypeList", goodsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("act", act);
        return "backstage/selectGoods";

    }

    @Override
    public String goodsDetail(Integer id, String act, Model model) {
        Goods goods = goodsMapper.selectById(id);
        List<GoodType> goodTypeList = goodTypeMapper.selectList(new QueryWrapper<>());
        String goodsTypeName = goodTypeMapper.selectById(goods.getGoodsTypeId()).getTypeName();
        goods.setTypeName(goodsTypeName);
        System.out.println(goods);
        model.addAttribute("goods", goods);
        model.addAttribute("goodTypeList", goodTypeList);
        if (act.equals("update")) {
            return "backstage/updateAGoods";
        } else if (act.equals("detail")) {
            return "backstage/detail";
        } else {
            return "error";
        }
    }

    @Override
    public String deleteGoodsById(Integer id) {
        goodsMapper.deleteById(id);
        return "redirect:/goods/selectAllGoodsByPage?act=" + "deleteSelect";
    }

    @Override
    public String updateGoods(Goods goods) {
        int row = goodsMapper.updateById(goods);
        return "redirect:/goods/selectAllGoodsByPage?act=" + "select";
    }

    @Override
    public String addGoods(Goods goods) {
        int row = goodsMapper.insert(goods);
        return "redirect:/goods/selectAllGoodsByPage?act=" + "select";
    }

    @Override
    public String toAddGoods(Model model) {
        List<GoodType> goodTypeList = goodTypeMapper.selectList(new QueryWrapper<>());
        model.addAttribute("goodTypeList",goodTypeList);
        return "backstage/addGoods";
    }

    @Override
    public Object[] goodImgUpload(MultipartFile fileUpload, MultipartFile fileUpload1, String path) {
        if (fileUpload != null) {
            String filename = fileUpload.getOriginalFilename();

            String suffixName = filename.substring(filename.lastIndexOf("."));
            long date = new Date().getTime();

            String newName = date + suffixName;
            String filePath = null;
            try {
                filePath = ResourceUtils.getURL("classpath:").getPath() + "static/images/";
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            String realPath = filePath.replace('/', '\\').substring(1, filePath.length()).replace("%20", " ");
            File file1 = new File(path, newName);
            try {
                fileUpload1.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            File file = new File(realPath, newName);

            try {
                fileUpload.transferTo(file);
                return new Object[]{0, "../images/" + newName, newName};
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return new Object[]{-1};
    }


}
