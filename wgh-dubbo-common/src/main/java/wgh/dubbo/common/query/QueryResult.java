package wgh.dubbo.common.query;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuyu
 * 
 */
@SuppressWarnings("serial")
public final class QueryResult<T> implements Serializable{

	private String resultCode;
	private String resultMsg;
	private Integer total = null;
	private Integer perPageSize = null;
	private Integer pageNumber = null;
	private List<T> results = null;

	public QueryResult() {
	}

	public QueryResult(Integer total, Integer perPageSize, Integer pageNumber,
			List<T> results) {
		this.total = total;
		this.perPageSize = perPageSize;
		this.pageNumber = pageNumber;
		this.results = results;
	}

	public QueryResult(int total, List<T> results) {
		this.total = total;
		this.results = results;
	}

	public QueryResult(int total, QueryPage queryPage, List<T> results) {
		this.total = total;
		if (queryPage != null) {
			this.perPageSize = queryPage.getPerPageSize();
			this.pageNumber = queryPage.getPageNumber();
		}
		this.results = results;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getPerPageSize() {
		return perPageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public List<T> getResults() {
		return results;
	}

	public boolean hasResult() {
		if (results == null) {
			return false;
		}
		if (results.size() < 1) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		int size = results == null ? 0 : results.size();

		return String.format("QueryResult[total:%s, perPageSize:%s, pageNumber:%s, resultSize:%d].",
						total, perPageSize, pageNumber, size);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
