package lucene.utils.utils;

import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Weight;

import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.LuceneDocItem;

public class MatchAllDocsQueryUtil<T extends AbstractLuceneDoc> extends
		LuceneIKUtil<T> {

	private String searchQuery;

	public MatchAllDocsQueryUtil(String indexFilePath, String searchQuery) {
		super(indexFilePath);
		this.searchQuery = searchQuery;
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
			// create the term query object
			Query query = new MatchAllDocsQuery();
			Weight weight = query.createWeight(this.indexSearcher, true);
			this.query = query;
			// TODO Auto-generated method stub
			return super.search(abstractLuceneDoc, autoFill, mapFilter,
					searchBean);
		} catch (Exception e) {
		}
		return null;
	}
}
