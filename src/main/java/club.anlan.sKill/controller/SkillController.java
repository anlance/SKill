package club.anlan.sKill.controller;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.rabbitmq.MQSender;
import club.anlan.sKill.rabbitmq.SkillMessage;
import club.anlan.sKill.redis.GoodsKey;
import club.anlan.sKill.result.CodeMsg;
import club.anlan.sKill.result.Result;
import club.anlan.sKill.service.*;
import club.anlan.sKill.vo.GoodsVo;
import club.anlan.sKill.vo.LoginVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/skill")
public class SkillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    MQSender sender;


    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 系统初始化
     * */
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.getGoodsVo();
        if(goodsList == null) {
            return;
        }
        for(GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSkillGoodsStock, ""+goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping(value = "do_skill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> skill(Model model,User user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.SKILL_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getSkillGoodsStock, ""+goodsId);//10
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SKILL_OVER);
        }
        //判断是否已经秒杀到了
        SKillOrder order = orderService.getSkillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_SKILL);
        }
        //入队
        SkillMessage mm = new SkillMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendSkillMessage(mm);
        return Result.success(0);//排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model,User user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result  =skillService.getSkillResult(user.getId(), goodsId);
        return Result.success(result);
    }

}
