package club.anlan.sKill.service;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.vo.GoodsVo;

public interface SkillService {

    OrderInfo doSkill(User user, GoodsVo goods);

    long getSkillResult(Long userId, long goodsId);
}
