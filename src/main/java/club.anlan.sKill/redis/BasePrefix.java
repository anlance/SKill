package club.anlan.sKill.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     *
     * @return
     * 0 永不过期
     */
    public int expireSeconds() {
        return expireSeconds;
    }

    public String getPrefix() {
        String className =  getClass().getSimpleName().toString();
        return className+":"+prefix;
    }
}
