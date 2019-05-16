package club.anlan.sKill.redis;

public interface KeyPrefix {
    int expireSeconds();
    String getPrefix();
}
