package club.anlan.sKill.redis;

public class SkillKey extends BasePrefix {

    private SkillKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SkillKey isGoodsOver = new SkillKey(0,"go");
    public static SkillKey getSkillPath = new SkillKey(60, "sp");
    public static SkillKey getSkillVerifyCode = new SkillKey(300, "svc");
}
