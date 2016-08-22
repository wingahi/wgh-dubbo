package lucene.utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.LuceneDocItem;
import lucene.utils.entity.SearchBean;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

public class TermRangeQueryUtil<T extends AbstractLuceneDoc> extends LuceneIKUtil<T> {

	private String fieldName;
	private String searchQueryMin;
	private String searchQueryMax;
	
	public TermRangeQueryUtil(String indexFilePath, String fieldName,
			String searchQueryMin, String searchQueryMax) {
		super(indexFilePath);
		this.fieldName = fieldName;
		this.searchQueryMin = searchQueryMin;
		this.searchQueryMax = searchQueryMax;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSearchQueryMin() {
		return searchQueryMin;
	}

	public void setSearchQueryMin(String searchQueryMin) {
		this.searchQueryMin = searchQueryMin;
	}

	public String getSearchQueryMax() {
		return searchQueryMax;
	}

	public void setSearchQueryMax(String searchQueryMax) {
		this.searchQueryMax = searchQueryMax;
	}

	/**
	 * 
	 * Description锛氭煡璇�
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @param where
	 *            鏌ヨ鏉′欢
	 * @param scoreDoc
	 *            鍒嗛〉鏃剁敤
	 */
	public List<LuceneDocItem> search(T abstractLuceneDoc,boolean autoFill,Map<String,Object> mapFilter,SearchBean searchBean) {
		List<LuceneDocItem> result = new ArrayList<LuceneDocItem>();
		try {
			// 鍒涘缓绱㈠紩鎼滅储鍣�涓斿彧璇�
			DirectoryReader indexReader = DirectoryReader.open(this.directory);
			this.indexSearcher = new IndexSearcher(indexReader);
			
			this.query = new TermRangeQuery(fieldName, 
					new BytesRef(searchQueryMin),new BytesRef(searchQueryMax),true,false);
			 return super.search(abstractLuceneDoc, autoFill, mapFilter, searchBean);
		}catch(Exception e){
		}
		return null;
	}
}
