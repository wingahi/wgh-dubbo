package wgh.dubbo.common.query;

/**
 * @author liuyu
 * 
 */
public enum QueryOperator {
	EQUAL("="), LIKE("like"), IN("in"), BETWEEN("between"), NOTIN("not in"), GREATER(">"), GREATER_AND_EQUAL(">="), LESS(
			"<"), LESS_AND_EQUAL("<="), IS_NULL("is null"), IS_NOT_NULL("is not null");

	private String value = null;

	private QueryOperator(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
