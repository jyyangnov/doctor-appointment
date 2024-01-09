package com.yan.daserver.serviceHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenServiceHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /** token过期时间, 单位毫秒 */
    private static final long TOKEN_EXPIRE_IN_MS = Duration.ofHours(2).toMillis();

    public String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void saveToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token, TOKEN_EXPIRE_IN_MS, TimeUnit.MILLISECONDS);
    }

    public Boolean deleteToken(String key) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return redisTemplate.delete(key);
        }
        return false;
    }
}
