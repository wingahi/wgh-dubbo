package wgh.dubbo.portal.testapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





import wgh.dubbo.common.Consts;
import wgh.dubbo.portal.BaseController;
import wgh.dubbo.portal.common.ObjectResponse;
import wgh.dubbo.service.client.dto.test.TestTableDto;
import wgh.dubbo.service.client.service.test.TestTableService;

@Controller()
@RequestMapping(produces = "application/json;charset=UTF-8", value = "/test")
public class TestController extends BaseController {
	
	@Autowired
	TestTableService testTableService;
	
	@RequestMapping(value= "/testSave")
	public @ResponseBody 
	ObjectResponse testSave(@RequestParam(value = "name", required = true) String name){
		ObjectResponse response = new ObjectResponse(Consts.API_CODE_ERROR,"");
		try {
			TestTableDto testTableDto=new TestTableDto();
			testTableDto.setName(name);
			String result = testTableService.save(testTableDto);
			response.setResult(result);
			response.setRetCode(Consts.API_CODE_SUCCESS);
		} catch (Exception e) {
			response.setRetMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return response;
	}
	
	@RequestMapping(value= "/getFromRedis")
	public @ResponseBody 
	ObjectResponse getFromRedis(@RequestParam(value = "id", required = true) int id){
		ObjectResponse response = new ObjectResponse(Consts.API_CODE_ERROR,"");
		try {
		
			TestTableDto result = testTableService.getFromRedis(id);
			response.setResult(result);
			response.setRetCode(Consts.API_CODE_SUCCESS);
		} catch (Exception e) {
			response.setRetMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return response;
	}
}
