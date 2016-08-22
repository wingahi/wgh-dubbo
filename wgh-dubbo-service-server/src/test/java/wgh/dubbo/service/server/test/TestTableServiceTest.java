package wgh.dubbo.service.server.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wgh.dubbo.service.client.dto.test.TestTableDto;
import wgh.dubbo.service.client.service.test.TestTableService;

public class TestTableServiceTest extends BaseTestTemplate {

	@Autowired
	TestTableService testTableService;
	
	@Test
	public void save() {
		// TODO Auto-generated method stub
		TestTableDto testTableDto =new TestTableDto();
		testTableDto.setName("555");
		System.out.println(testTableService.save(testTableDto));
		
	}

}
