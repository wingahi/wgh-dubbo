package wgh.dubbo.openapi.server.common;

import java.util.List;

@SuppressWarnings("serial")
public class PageResponse<T> extends BaseResponse {
	int pageNum = 0;
	int totalCount = 0;
	int pageSize = 0;
	List<T> resultList;

	public PageResponse() {
	}

	public PageResponse(int pageNum, int totalCount, int pageSize,
			List<T> resultList) {
		super();
		this.pageNum = pageNum;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.resultList = resultList;
	}

	public PageResponse(String retCode, String retMsg, int pageNum,
			int pageSize, int totalCount, List<T> resultList) {
		this.retCode = retCode;
		this.retMsg = retMsg;
		this.pageNum = pageNum;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.resultList = resultList;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public PageResponse(String retCode, String retMsg) {
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}

}
