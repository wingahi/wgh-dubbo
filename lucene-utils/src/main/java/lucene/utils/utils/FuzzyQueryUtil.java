package lucene.utils.utils;

import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.BooleanQueryItem;
import lucene.utils.entity.LuceneDocItem;

public class FuzzyQueryUtil<T extends AbstractLuceneDoc> extends
		LuceneIKUtil<T> {

	private String fieldName;
	private String searchQuery;

	public FuzzyQueryUtil(String indexFilePath, String fieldName,
			String searchQuery) {
		super(indexFilePath);
		this.fieldName = fieldName;
		this.searchQuery = searchQuery;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<LuceneDocItem> search(T abstractLuceneDoc, boolean autoFill,
			Map<String, Object> mapFilter,
			lucene.utils.entity.SearchBean searchBean) {
		try {
			// 鍒涘缓绱㈠紩鎼滅储鍣�涓斿彧璇�
			DirectoryReader indexReader = DirectoryReader.open(this.directory);
			this.indexSearcher = new IndexSearcher(indexReader);
			Term term = new Term(fieldName, searchQuery);
			// create the term query object
			Query query = new FuzzyQuery(term);
			this.query = query;
			// TODO Auto-generated method stub
			return super.search(abstractLuceneDoc, autoFill, mapFilter,
					searchBean);
		} catch (Exception e) {
		}
		return null;
	}
}
