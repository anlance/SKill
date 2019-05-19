package club.anlan.sKill.redis;

public class SkillKey extends BasePrefix {

    private SkillKey(String prefix) {
        super(prefix);
    }
    public static SkillKey isGoodsOver = new SkillKey("go");
}
