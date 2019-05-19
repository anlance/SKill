package club.anlan.sKill.service;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.vo.GoodsVo;

import java.awt.image.BufferedImage;

public interface SkillService {

    OrderInfo doSkill(User user, GoodsVo goods);

    long getSkillResult(Long userId, long goodsId);

    boolean checkPath(User user, long goodsId, String path);

    String createSkillPath(User user, long goodsId);

    BufferedImage createVerifyCode(User user, long goodsId);

    boolean checkVerifyCode(User user, long goodsId, int verifyCode);
}
