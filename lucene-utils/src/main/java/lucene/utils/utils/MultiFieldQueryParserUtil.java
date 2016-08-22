package lucene.utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.LuceneDocItem;
import lucene.utils.entity.SearchBean;

public class MultiFieldQueryParserUtil<T extends AbstractLuceneDoc> extends LuceneIKUtil<T> {
	private String[] fields;
	private String keyword;
	
	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public MultiFieldQueryParserUtil(String indexFilePath, String[] fields,
			String keyword) {
		super(indexFilePath);
		this.fields = fields;
		this.keyword = keyword;
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

			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
					fields, this.analyzer);
			 this.query = queryParser.parse(keyword);
			 return super.search(abstractLuceneDoc, autoFill, mapFilter, searchBean);
		}catch(Exception e){
		}
		return null;
	}
}
