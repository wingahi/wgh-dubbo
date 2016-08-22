package wgh.dubbo.extend.service.client.dto.weixin;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class MapObjectDto implements Serializable{
	
	private HashMap<String,String> mapDto;

	public HashMap<String, String> getMapDto() {
		return mapDto;
	}

	public void setMapDto(HashMap<String, String> mapDto) {
		this.mapDto = mapDto;
	}
	
}
