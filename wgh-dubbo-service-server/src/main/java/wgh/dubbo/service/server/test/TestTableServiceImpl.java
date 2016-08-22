package wgh.dubbo.service.server.test;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;






import wgh.dubbo.cache.impl.redis.RedisUtil;
import wgh.dubbo.persistence.impl.dao.test.TestTableDao;
import wgh.dubbo.persistence.impl.entity.test.TestTable;
import wgh.dubbo.service.client.dto.test.TestTableDto;
import wgh.dubbo.service.client.service.test.TestTableService;

public class TestTableServiceImpl implements TestTableService {

	@Autowired
	TestTableDao testTableDao;
	@Resource
	RedisUtil redisUtil;
	
	@Override
	public String save(TestTableDto testTableDto) {
		// TODO Auto-generated method stub
		TestTable testTable=new TestTable();
		testTable.setName(testTableDto.getName());
	    testTableDao.save(testTable);
	    testTableDto.setName(testTable.getName());
	    redisUtil.setObject(testTable.getId(), testTableDto);
	    return "保存成功";
	}

	@Override
	public TestTableDto getFromRedis(int id) {
		// TODO Auto-generated method stub
		
		return redisUtil.getObject(id);
	}

}
