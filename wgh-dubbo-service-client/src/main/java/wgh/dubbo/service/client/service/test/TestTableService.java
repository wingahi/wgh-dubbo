package wgh.dubbo.service.client.service.test;

import wgh.dubbo.service.client.dto.test.TestTableDto;

public interface TestTableService {
	/**
	 * 测试
	 * 
	 * @param testTableDto
	 * @return
	 */
	public String save(TestTableDto testTableDto);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TestTableDto getFromRedis(int id);
}
