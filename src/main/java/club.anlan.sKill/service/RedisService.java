package club.anlan.sKill.service;

import club.anlan.sKill.redis.KeyPrefix;

public interface RedisService {
    <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz);
    <T> boolean set(KeyPrefix keyPrefix, String key, T value);
    <T> boolean exists(KeyPrefix keyPrefix, String key, T value);
    <T> Long incr(KeyPrefix keyPrefix, String key, T value);
    <T> Long decr(KeyPrefix keyPrefix, String key, T value);
}
