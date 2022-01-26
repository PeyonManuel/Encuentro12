package com.example.encuentro2.repository;

import com.example.encuentro2.model.Restaurante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

@Repository
public class RestauranteRedisRepository {
    public static final String RESTAURANTE_KEY = "RESTAURANTE";

    private static final Logger logger = LoggerFactory.getLogger(RestauranteRedisRepository.class);


    private HashOperations<String, String, String> hashOperations;
    @Autowired
    private RedisTemplate redisTemplate;

    public RestauranteRedisRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(String restaurante, String id) {
        hashOperations.put(RESTAURANTE_KEY, id, restaurante);
    }

    public List findAll(){
        return hashOperations.values(RESTAURANTE_KEY);
    }

   public String findById(String id) {
        try {
            return  hashOperations.get(RESTAURANTE_KEY, id);
        }catch(JedisConnectionException jedisConnectionException){
            logger.error("error");
            return null;
        }
    }

    public void update(Restaurante restaurante) {
        //save(restaurante);
    }

    public void delete(String id) {
        hashOperations.delete(RESTAURANTE_KEY, id);
    }

}
