package club.anlan.sKill.controller;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.result.CodeMsg;
import club.anlan.sKill.result.Result;
import club.anlan.sKill.service.*;
import club.anlan.sKill.vo.GoodsVo;
import club.anlan.sKill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SkillService skillService;

    @RequestMapping(value = "do_skill", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> skill(Model model,User user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.error(CodeMsg.SKILL_OVER);
        }
        //判断是否已经秒杀到了
        SKillOrder order = orderService.getSkillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_SKILL);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = skillService.doSkill(user, goods);
        return Result.success(orderInfo);
    }

}
