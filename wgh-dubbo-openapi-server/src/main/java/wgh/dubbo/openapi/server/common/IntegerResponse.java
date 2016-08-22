package wgh.dubbo.openapi.server.common;

@SuppressWarnings("serial")
public class IntegerResponse extends BaseResponse {
	private Integer result;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public IntegerResponse(Integer result) {
		super();
		this.result = result;
	}

	public IntegerResponse() {
		// TODO Auto-generated constructor stub
	}

	public IntegerResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}

}
