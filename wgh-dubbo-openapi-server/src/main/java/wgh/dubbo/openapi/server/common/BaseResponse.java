package wgh.dubbo.openapi.server.common;

import java.io.Serializable;

public class BaseResponse implements Serializable {
	
	protected static final long serialVersionUID = 1L;
	protected String retCode;
	protected String retMsg;

	public BaseResponse() {

	}

	public BaseResponse(String retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	// public String toJsonString() {
	// ObjectMapper mapper = new ObjectMapper();
	// String jsonStr = null;
	// try {
	// jsonStr = mapper.writeValueAsString(this);
	// } catch (JsonGenerationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return jsonStr;

	// return null;
	// }

}
