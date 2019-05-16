package club.anlan.sKill.service;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.vo.GoodsVo;

public interface OrderService {

    SKillOrder getSkillOrderByUserIdGoodsId(Long userId, long goodsId);

    OrderInfo createOrder(User user, GoodsVo goods);
}
