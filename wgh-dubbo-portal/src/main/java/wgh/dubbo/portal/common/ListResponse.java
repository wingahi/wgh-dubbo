package wgh.dubbo.portal.common;

import java.util.List;

@SuppressWarnings("serial")
public class ListResponse<T> extends BaseResponse {

	List<T> resultList;
	private Integer sinceId;
	private Integer maxId;

	public ListResponse() {
		super();
	}
	public ListResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public Integer getSinceId() {
		return sinceId;
	}
	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}
	public Integer getMaxId() {
		return maxId;
	}
	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

}
