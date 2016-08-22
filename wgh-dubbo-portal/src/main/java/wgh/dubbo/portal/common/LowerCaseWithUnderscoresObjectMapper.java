package wgh.dubbo.portal.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.annotation.JsonInclude.Include;







public class LowerCaseWithUnderscoresObjectMapper extends ObjectMapper {
	public LowerCaseWithUnderscoresObjectMapper() {
		super();
		setSerializationInclusion(Include.NON_NULL);//null值不打印
		//CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
		setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);//小写规范
	}
}