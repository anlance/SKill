package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.mapper.OrderMapper;
import club.anlan.sKill.service.OrderService;
import club.anlan.sKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public SKillOrder getSkillOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderMapper.getSkillOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSkillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderMapper.insert(orderInfo);

        SKillOrder sKillOrder = new SKillOrder();
        sKillOrder.setGoodsId(goods.getId());
        sKillOrder.setOrderId(orderInfo.getId());
        sKillOrder.setUserId(user.getId());
        orderMapper.insertSKillOrder(sKillOrder);

        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
