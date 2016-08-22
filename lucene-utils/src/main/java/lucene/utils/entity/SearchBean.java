package lucene.utils.entity;

import java.util.List;

import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;

public class SearchBean {

	private Integer n;
	private SortField[] sortFields;
	private Integer start;
	private Integer end;
	
	public int getN() {
		if(n==null){
			n = Integer.MAX_VALUE;
		}
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public SortField[] getSortFields() {
		return sortFields;
	}
	public void setSortFields(SortField[] sortFields) {
		this.sortFields = sortFields;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}

}
