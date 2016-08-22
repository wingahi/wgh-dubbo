package wgh.dubbo.openapi.server.common;

@SuppressWarnings("serial")
public class StringResponse extends BaseResponse {
	private String result;

	public StringResponse() {
		// TODO Auto-generated constructor stub
	}

	public StringResponse(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public StringResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}

}
