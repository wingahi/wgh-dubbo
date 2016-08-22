package wgh.dubbo.portal.common;

@SuppressWarnings("serial")
public class BooleanResponse extends BaseResponse {
	private boolean result;

	public BooleanResponse() {
	}

	public BooleanResponse(boolean result) {
		super();
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public BooleanResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}

}
