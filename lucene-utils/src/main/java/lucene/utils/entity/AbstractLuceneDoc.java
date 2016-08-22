package lucene.utils.entity;

import java.io.IOException;

import lucene.utils.annotation.IndexField;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;


public abstract class AbstractLuceneDoc implements LuceneDocItem {

	private String strId="";

	private Integer dataType=0;
	
	@IndexField(index = Field.Index.NOT_ANALYZED, store = Field.Store.YES)
	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	@IndexField(index = Field.Index.NOT_ANALYZED, store = Field.Store.YES)
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Document addDocument(LuceneDocItem luceneDocItem) {
		// TODO Auto-generated method stub
		return null;
	}


	

	public LuceneDocItem fillData(Document document, Analyzer analyzer,
			Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
