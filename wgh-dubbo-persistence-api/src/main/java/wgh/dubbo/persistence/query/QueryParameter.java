package wgh.dubbo.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author liuyu
 * 
 */
public class QueryParameter {

	private QueryPage queryPage = null;

	private QueryOrderBy orderBy = null;

	private Integer perPageSize = null;

	private Integer pageNumber = null;
	/**
	 * 多条件优先排序（按优先条件级别由高往低依次添加）
	 */
	private List<QueryOrderBy> orderByList = null;

	private List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();

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

	public QueryParameter() {
	}

	public QueryParameter(QueryPage queryPage) {
		this.queryPage = queryPage;
	}

	public QueryOrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(QueryOrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public List<QueryOrderBy> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<QueryOrderBy> orderByList) {
		this.orderByList = orderByList;
	}

	public <VT extends Serializable> void addCondition(
			QueryCondition queryConditionDto) {
		queryConditions.add(queryConditionDto);
	}

	public <VT extends Serializable> void addCondition(QueryOperator operator,
			String field, Object value) {
		QueryCondition queryConditionDto = new QueryCondition(operator, field,
				value);

		queryConditions.add(queryConditionDto);
	}

	/**
	 * 在使用bewteen时，使用该方法添加查询条件。
	 * 
	 * @param <VT>
	 * @param operator
	 * @param field
	 * @param value1
	 *            Bewteen中的起始值
	 * @param value2
	 *            Bewteen中的结束值
	 */
	public <VT extends Serializable> void addCondition(QueryOperator operator,
			String field, Object... value) {
		QueryCondition queryConditionDto = new QueryCondition(operator, field,
				value);

		queryConditions.add(queryConditionDto);
	}

	public void setQueryPage(QueryPage queryPageDto) {
		this.queryPage = queryPageDto;
	}

	public void setQueryConditions(List<QueryCondition> queryConditionDtos) {
		this.queryConditions = queryConditionDtos;
	}

	public List<QueryCondition> getQueryConditions() {
		for (QueryCondition queryCondition : queryConditions) {
			Object[] value = queryCondition.getValue();
			for (int i = 0; i < value.length; i++) {
				Object object = value[i];
				if (object instanceof XMLGregorianCalendar) {
					XMLGregorianCalendar cal = (XMLGregorianCalendar) object;
					Date date = cal.toGregorianCalendar().getTime();
					value[i] = date;
				}
			}
		}

		return queryConditions;
	}

	public QueryPage getQueryPage() {
		return queryPage;
	}

}
