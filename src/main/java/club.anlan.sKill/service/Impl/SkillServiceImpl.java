package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.Goods;
import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.mapper.GoodsMapper;
import club.anlan.sKill.redis.SkillKey;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.service.OrderService;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.SkillService;
import club.anlan.sKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional
    public OrderInfo doSkill(User user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if(success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        }else {
            return null;
        }
    }

    public long getSkillResult(Long userId, long goodsId) {
        SKillOrder order = orderService.getSkillOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SkillKey.isGoodsOver, ""+goodsId);
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SkillKey.isGoodsOver, ""+goodsId, true);
    }


}
