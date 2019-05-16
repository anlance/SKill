package club.anlan.sKill.controller;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.UserService;
import club.anlan.sKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model, User user) {
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsVoList = goodsService.getGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        return "goods_list";
    }


    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);
        //查询商品列表
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();
        int skillStatus = 0;
        int remainSeconds = 0;
        if (nowTime < startTime) {//秒杀还未开始
            skillStatus = 0;
            remainSeconds = (int) (nowTime - startTime) / 1000;
        } else if(nowTime>endTime) {//秒杀已经结束
            skillStatus=2;
            remainSeconds=-1;
        } else {//秒杀进行中
            skillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("skillStatus",skillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }


}
