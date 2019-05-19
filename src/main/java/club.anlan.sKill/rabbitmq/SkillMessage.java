package club.anlan.sKill.rabbitmq;

import club.anlan.sKill.domain.User;

public class SkillMessage {
    private User user;
    private long goodsId;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
