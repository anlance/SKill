package club.anlan.sKill.vo;

import club.anlan.sKill.domain.Goods;
import club.anlan.sKill.domain.User;

import java.sql.Date;

public class GoodsDetailVo {
    private int skillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private User user;
    public int getSkillStatus() {
        return skillStatus;
    }
    public void setSkillStatus(int skillStatus) {
        this.skillStatus = skillStatus;
    }
    public int getRemainSeconds() {
        return remainSeconds;
    }
    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }
    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}

