package lucene.utils.entiry.test;

import java.io.IOException;

import lucene.utils.annotation.IndexField;
import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.LuceneDocItem;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.w3c.dom.views.AbstractView;

/**
 * 
 *
 *  @version 锛�1.0
 *  
 *  @author  锛�鑻忚嫢骞�             <a href="mailto:DennisIT@163.com">鍙戦�閭欢</a>
 *    
 *  @since   锛�1.0        鍒涘缓鏃堕棿:    2013-4-7    涓嬪崍01:52:49
 *     
 *  @function锛�TODO        
 *
 */
public class Medicine extends AbstractLuceneDoc {

    private Integer id;
    private String name;
    private String function;
    
    
    public Medicine() {
    	
    }
    
    
    public Medicine(Integer id, String name, String function) {
        super();
        this.setStrId("Medicine:"+id);
        this.id = id;
        this.name = name;
        this.function = function;
    }

    //getter and setter()    
    @IndexField(index = Field.Index.NOT_ANALYZED, store = Field.Store.YES)
    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
		this.setStrId("Medicine:"+id);
	}

	@IndexField(index = Field.Index.ANALYZED, store = Field.Store.YES)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@IndexField(index = Field.Index.ANALYZED, store = Field.Store.YES)
	public String getFunction() {
		return function;
	}


	public void setFunction(String function) {
		this.function = function;
	}


	public String toString(){
        return this.id + "," +this.name + "," + this.function;
    }

//	public Document addDocument(LuceneDocItem luceneDocItem) {
//		// TODO Auto-generated method stub
//		Document doc = new Document();
//		// Field.Index.NO 琛ㄧず涓嶇储寮�
//		// Field.Index.ANALYZED 琛ㄧず鍒嗚瘝涓旂储寮�
//		// Field.Index.NOT_ANALYZED 琛ㄧず涓嶅垎璇嶄笖绱㈠紩
//		//鑷姩瑙ｆ瀽abstractLuceneDoc
//		doc.add(new Field("id", String.valueOf(id), Field.Store.YES,
//				Field.Index.NOT_ANALYZED));
//		doc.add(new Field("name", name, Field.Store.YES, Field.Index.ANALYZED));
//		doc.add(new Field("function", function, Field.Store.YES,
//				Field.Index.ANALYZED));
//		return doc;
//	}
//
//
//	public LuceneDocItem fillData(Document document, Analyzer analyzer,
//			Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {
//		// TODO Auto-generated method stub
//		Integer id = Integer.parseInt(document.get("id"));
//		String name = document.get("name");
//		String function = document.get("function");
//		// float score = scDoc.score; //鐩镐技搴�
//
//		String lighterName = highlighter.getBestFragment(analyzer,
//				"name", name);
//		if (null == lighterName) {
//			lighterName = name;
//		}
//
//		String lighterFunciton = highlighter.getBestFragment(analyzer,
//				"function", function);
//		if (null == lighterFunciton) {
//			lighterFunciton = function;
//		}
//
//		Medicine medicine = new Medicine();
//		medicine.setId(id);
//		medicine.setName(lighterName);
//		medicine.setFunction(lighterFunciton);
//		return medicine;
//	}
	
}