package wgh.dubbo.extend.service.server.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wgh.dubbo.extend.service.client.dto.test.TestExtendTableDto;
import wgh.dubbo.extend.service.client.service.test.TestExtendTableService;

public class TestExtendTableServiceTest extends BaseTestTemplate {

	@Autowired
	TestExtendTableService testTableService;
	
	@Test
	public void save() {
		// TODO Auto-generated method stub
		TestExtendTableDto testTableDto =new TestExtendTableDto();
		testTableDto.setName("555");
		System.out.println(testTableService.save(testTableDto));
		
	}

}
