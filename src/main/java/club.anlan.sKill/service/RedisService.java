package club.anlan.sKill.service;

public interface RedisService {
    <T> T get(String key,Class<T> clazz);
    <T> boolean set(String key, T value);
}
