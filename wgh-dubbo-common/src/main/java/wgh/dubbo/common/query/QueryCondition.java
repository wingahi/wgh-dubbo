package wgh.dubbo.common.query;

/**
 * @author liuyu
 * 
 */
public class QueryCondition {

	private String operator = null;
	private String field = null;
	private Object[] value = null;

	public QueryCondition() {
	}

	public QueryCondition(QueryOperator operator, String field, Object... value) {
		this.operator = operator.getValue();
		this.field = field;
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object[] getValue() {
		return value;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}

}
