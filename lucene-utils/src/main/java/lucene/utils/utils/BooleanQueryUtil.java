package lucene.utils.utils;

import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.BooleanClause.Occur;

import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.BooleanQueryItem;
import lucene.utils.entity.LuceneDocItem;
import lucene.utils.entity.SearchBean;

public class BooleanQueryUtil<T extends AbstractLuceneDoc> extends LuceneIKUtil<T>{
	
    private List<BooleanQueryItem> queryItems;
	
	public BooleanQueryUtil(String indexFilePath,
			List<BooleanQueryItem> queryItems) {
		super(indexFilePath);
		this.queryItems = queryItems;
	}

	public List<BooleanQueryItem> getQueryItems() {
		return queryItems;
	}

	public void setQueryItems(List<BooleanQueryItem> queryItems) {
		this.queryItems = queryItems;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<LuceneDocItem> search(T abstractLuceneDoc, boolean autoFill,
			Map<String, Object> mapFilter,
			lucene.utils.entity.SearchBean searchBean) {
		try{
			// 鍒涘缓绱㈠紩鎼滅储鍣�涓斿彧璇�
						DirectoryReader indexReader = DirectoryReader.open(this.directory);
						this.indexSearcher = new IndexSearcher(indexReader);
			BooleanQuery query =new BooleanQuery();
			if(queryItems!=null){
				for (BooleanQueryItem item : queryItems) {
					if(item==null) continue;
					query.add(item.getQuery(), item.getOccur());
				}
			}
			this.query = query;
			// TODO Auto-generated method stub
			return super.search(abstractLuceneDoc, autoFill, mapFilter, searchBean);
		}catch(Exception e){
		}
		return null;
	}
}
