package wgh.dubbo.service.server.test;

import javax.annotation.Resource;

import wgh.dubbo.persistence.dao.test.TestTableDao;
import wgh.dubbo.persistence.entity.test.TestTable;
import wgh.dubbo.service.client.dto.test.TestTableDto;
import wgh.dubbo.service.client.service.test.TestTableService;

public class TestTableServiceImpl implements TestTableService {

	@Resource
	TestTableDao testTableDao;

	@Override
	public String save(TestTableDto testTableDto) {
		// TODO Auto-generated method stub
		TestTable testTable = new TestTable();
		testTable.setName("ssssssssssssssssss");
		testTableDao.save(testTable);
		// testTableDto.setName("ssssssssssssssssss");
		// redisUtil.setObject("1232", testTableDto);
		return "保存成功";
	}

	@Override
	public TestTableDto getFromRedis(int id) {
		// TODO Auto-generated method stub

		return null;// redisUtil.getObject(id);
	}

}
