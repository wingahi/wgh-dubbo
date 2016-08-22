package wgh.dubbo.openapi.server.common;

@SuppressWarnings("serial")
public class ObjectResponse extends BaseResponse {
	
	private Object result;

	public ObjectResponse() {
		// TODO Auto-generated constructor stub
	}

	public ObjectResponse(Object result) {
		super();
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ObjectResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}

}
