package wgh.dubbo.cache.impl.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wgh.dubbo.cache.impl.BaseTestTemplate;
import wgh.dubbo.cache.impl.redis.RedisUtil;



public class RedisUtilTest extends BaseTestTemplate  {

	@Autowired
	RedisUtil redisUtil;

	@Test
	public void save() {
		// TODO Auto-generated method stub
		System.out.println(redisUtil.sadd("sss", "65555"));
	}
}
