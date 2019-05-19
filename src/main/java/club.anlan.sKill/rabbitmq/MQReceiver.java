package club.anlan.sKill.rabbitmq;

import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.service.Impl.RedisServiceImpl;
import club.anlan.sKill.service.OrderService;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.SkillService;
import club.anlan.sKill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SkillService skillService;

    @RabbitListener(queues=MQConfig.SKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        SkillMessage mm  = RedisServiceImpl.stringToBean(message, SkillMessage.class);
        User user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SKillOrder order = orderService.getSkillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        skillService.doSkill(user, goods);
    }

}
