package lucene.utils.entity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public interface LuceneDocItem {

	/**
	 * 添加索引回调
	 * @param luceneDocItem
     * @return Document
     */
	public Document addDocument(LuceneDocItem luceneDocItem);
	/**
	 * 查询结果填充数据
	 * @param document
	 * @return
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	public LuceneDocItem fillData(Document document,Analyzer analyzer,Highlighter highlighter) throws IOException, InvalidTokenOffsetsException;

}
