package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.Goods;
import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.mapper.GoodsMapper;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.service.OrderService;
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

    @Transactional
    public OrderInfo doSkill(User user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOrder(user,goods);
    }
}
