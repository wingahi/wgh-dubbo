package wgh.dubbo.common.query;

/**
 * @author liuyu
 * 
 */
public class QueryOrderBy {

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private String fieldName = null;
	private String orderBy = null;
	
	public QueryOrderBy() {
		
	}
	public QueryOrderBy(String fieldName) {
		this.fieldName = fieldName;
		this.orderBy = ASC;
	}
	public QueryOrderBy(String fieldName, String orderBy) {
		this.fieldName = fieldName;
		this.orderBy = orderBy;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
