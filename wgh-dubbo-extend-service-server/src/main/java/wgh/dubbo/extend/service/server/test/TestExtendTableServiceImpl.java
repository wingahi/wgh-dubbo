package wgh.dubbo.extend.service.server.test;

import org.springframework.beans.factory.annotation.Autowired;

import wgh.dubbo.extend.persistence.impl.dao.test.ExtendTestTableDao;
import wgh.dubbo.extend.persistence.impl.entity.test.ExtendTestTable;
import wgh.dubbo.extend.service.client.dto.test.TestExtendTableDto;
import wgh.dubbo.extend.service.client.service.test.TestExtendTableService;

public class TestExtendTableServiceImpl implements TestExtendTableService {

	@Autowired
	ExtendTestTableDao testTableDao;
	
	@Override
	public String save(TestExtendTableDto testTableDto) {
		// TODO Auto-generated method stub
		ExtendTestTable testTable=new ExtendTestTable();
		testTable.setName(testTableDto.getName());
	    testTableDao.save(testTable);
	    return "保存成功";
	}

}
