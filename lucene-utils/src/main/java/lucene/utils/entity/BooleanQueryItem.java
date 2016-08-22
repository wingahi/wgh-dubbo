package lucene.utils.entity;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
/**
 * 
 * @author admin
 *
 */
public class BooleanQueryItem {

	private Query query;
	private BooleanClause.Occur occur;
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public BooleanClause.Occur getOccur() {
		return occur;
	}
	public void setOccur(BooleanClause.Occur occur) {
		this.occur = occur;
	}
	
}
