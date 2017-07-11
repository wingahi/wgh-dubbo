package wgh.dubbo.persistence.query;

/**
 * 
 * @author liuyu
 * 
 */
public class QueryPage {

	private Integer perPageSize = null;
	private Integer pageNumber = null;

	public QueryPage() {
	}

	/**
	 * 
	 * @param perPageSize
	 *            每页大小
	 * @param pageNumber
	 *            页码
	 */
	public QueryPage(Integer perPageSize, Integer pageNumber) {
		this.perPageSize = perPageSize;
		this.pageNumber = pageNumber;
	}

	public Integer getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
