package club.anlan.sKill.rabbitmq;

import club.anlan.sKill.service.Impl.RedisServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSkillMessage(SkillMessage mm) {
        String msg = RedisServiceImpl.beanToString(mm);
        amqpTemplate.convertAndSend(MQConfig.SKILL_QUEUE, msg);
    }


}
