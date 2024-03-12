package com.leetao.ltapi.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leetao.ltapi.common.model.domain.User;
import com.leetao.ltapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热
 * @author leetao
 */
@Component
@Slf4j
public class PreCacheJob {

	@Resource
	private UserService userService;

	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private RedissonClient redissonClient;

	private List<Long> mainUserList = Arrays.asList(1L);

	@Scheduled(cron = "30 19 19 * * *")
	public void doCacheRecommendUsers(){
		RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
		try {
			if(lock.tryLock(0,-1,TimeUnit.SECONDS)){
				for(long userId : mainUserList){
					String redisKey = String.format("yupao:user:recommend:%s",userId);
					ValueOperations<String,Object> operations = redisTemplate.opsForValue();
					QueryWrapper<User> queryWrapper = new QueryWrapper<>();
					Page<User> userPage = userService.page(new Page<>(1, 10), queryWrapper);
					try {
						operations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("redis set key error",e);
					}
				}
			}
		} catch (InterruptedException e) {
			log.error("doCacheRecommendUsers error",e);
		}finally {
			lock.unlock();
		}


	}


}
