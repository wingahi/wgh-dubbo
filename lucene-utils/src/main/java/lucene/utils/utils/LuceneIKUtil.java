package lucene.utils.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lucene.utils.entiry.test.Medicine;
import lucene.utils.entity.AbstractLuceneDoc;
import lucene.utils.entity.EntityValueMapper;
import lucene.utils.entity.LuceneDocItem;
import lucene.utils.entity.SearchBean;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

/**
 * 
 * LuenceProcess.java
 * 
 * @version 锛�1.1
 * 
 * @author 锛�鑻忚嫢骞�<a href="mailto:DennisIT@163.com">鍙戦�閭欢</a>
 * 
 * @since 锛�1.0 鍒涘缓鏃堕棿: Apr 3, 2013 11:48:11 AM
 * 
 *        TODO : Luence涓娇鐢↖K鍒嗚瘝鍣�
 * 
 */

public class LuceneIKUtil<T extends AbstractLuceneDoc> {

	protected Directory directory;
	protected Analyzer analyzer;
	protected IndexSearcher indexSearcher = null;
	protected Query query = null;
	/**
	 * 甯﹀弬鏁版瀯閫�鍙傛暟鐢ㄦ潵鎸囧畾绱㈠紩鏂囦欢鐩綍
	 * 
	 * @param indexFilePath
	 */
	protected LuceneIKUtil(String indexFilePath) {
		try {

			directory = FSDirectory.open(Paths.get(indexFilePath));
			analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);// 5X(JcsegTaskConfig.COMPLEX_MODE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 榛樿鏋勯�,浣跨敤绯荤粺榛樿鐨勮矾寰勪綔涓虹储寮�
	 */
	public LuceneIKUtil() {
		this("/luence/index");
	}

	/**
	 * 鍒涘缓绱㈠紩 Description锛�
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @throws Exception
	 */
	public void createIndex(List<T> luceneDocItems) throws Exception {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);// (Version.LUCENE_CURRENT,analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.deleteAll();
		for (int i = 0; i < luceneDocItems.size(); i++) {
			T luceneDocItem = luceneDocItems.get(i);
			Document document = addDocument(luceneDocItem);
			indexWriter.addDocument(document);
		}

		indexWriter.close();
	}

	/**
	 * 
	 * Description锛�
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @param id
	 * @param title
	 * @param content
	 * @return
	 */
	public Document addDocument(T abstractLuceneDoc) {
		Document doc = new Document();
		// Field.Index.NO 琛ㄧず涓嶇储寮�
		// Field.Index.ANALYZED 琛ㄧず鍒嗚瘝涓旂储寮�
		// Field.Index.NOT_ANALYZED 琛ㄧず涓嶅垎璇嶄笖绱㈠紩
		//鑷姩瑙ｆ瀽abstractLuceneDoc
		Map<String,EntityValueMapper> fieldMap = EntityHelper.analyzeEntityValue(abstractLuceneDoc);
		EntityValueMapper valueMapper = null;
		for (Entry<String, EntityValueMapper> entry : fieldMap.entrySet()) {
			valueMapper = entry.getValue();
			doc.add(new Field(entry.getKey(), String.valueOf(valueMapper.getValue()), valueMapper.getStore(),
					valueMapper.getIndex()));
		}
		return doc;
	}

	/**
	 * 
	 * Description锛�鏇存柊绱㈠紩
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @param id
	 * @param title
	 * @param content
	 */
	public void update(AbstractLuceneDoc abstractLuceneDoc) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,
					indexWriterConfig);
			Document document = abstractLuceneDoc.addDocument(abstractLuceneDoc);
			Term term = new Term("strId", abstractLuceneDoc.getStrId());
			indexWriter.updateDocument(term, document);
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Description锛氭寜鐓D杩涜绱㈠紩
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @param id
	 */
	public void delete(String strId) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,
					indexWriterConfig);
			Term term = new Term("strId",strId);
			indexWriter.deleteDocuments(term);
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<LuceneDocItem> search(T abstractLuceneDoc, boolean autoFill,
			Map<String, Object> mapFilter, SearchBean searchBean){
		List<LuceneDocItem> result = new ArrayList<LuceneDocItem>();
		try{
			if(mapFilter!=null){
				LuceneFilter luceneFilter = new LuceneFilter(); 
				Set<String> keySet = mapFilter.keySet(); 
				for (String key : keySet) {
					luceneFilter.addFilter(key, mapFilter.get(key).toString()); 
				}
				query = luceneFilter.getFilterQuery(query);//缁撴灉杩囨护 
			}
			TopDocs topDocs = null;
			// 杩斿洖鍓峮umber鏉¤褰�
			if(searchBean!=null){
				if(searchBean.getSortFields()!=null && searchBean.getSortFields().length>0){
					topDocs = indexSearcher.search(query, searchBean.getN(),new Sort(searchBean.getSortFields()));//
				}else {
					topDocs = indexSearcher.search(query, searchBean.getN());//
				}
			}else {
				topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
			}
			
			// 淇℃伅灞曠ず
			int totalCount = topDocs.totalHits;
			//System.out.println("鍏辨绱㈠嚭 " + totalCount + " 鏉¤褰�);
	
			// 楂樹寒鏄剧ず
			/*
			 * 鍒涘缓楂樹寒鍣�浣挎悳绱㈢殑缁撴灉楂樹寒鏄剧ず SimpleHTMLFormatter锛氱敤鏉ユ帶鍒朵綘瑕佸姞浜殑鍏抽敭瀛楃殑楂樹寒鏂瑰紡 姝ょ被鏈�涓瀯閫犳柟娉�
			 * 1锛歋impleHTMLFormatter()榛樿鐨勬瀯閫犳柟娉�鍔犱寒鏂瑰紡锛�B>鍏抽敭瀛�/B>
			 * 2锛歋impleHTMLFormatter(String preTag, String
			 * postTag).鍔犱寒鏂瑰紡锛歱reTag鍏抽敭瀛梡ostTag
			 */
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>",
					"</font>");
	
			/*
			 * QueryScorer QueryScorer
			 * 鏄唴缃殑璁″垎鍣ㄣ�璁″垎鍣ㄧ殑宸ヤ綔棣栧厛鏄皢鐗囨鎺掑簭銆俀ueryScorer浣跨敤鐨勯」鏄粠鐢ㄦ埛杈撳叆鐨勬煡璇腑寰楀埌鐨勶紱
			 * 瀹冧細浠庡師濮嬭緭鍏ョ殑鍗曡瘝銆佽瘝缁勫拰甯冨皵鏌ヨ涓彁鍙栭」锛屽苟涓斿熀浜庣浉搴旂殑鍔犳潈鍥犲瓙锛坆oost factor锛夌粰瀹冧滑鍔犳潈銆�
			 * 涓轰簡渚夸簬QueryScoere浣跨敤锛岃繕蹇呴』瀵规煡璇㈢殑鍘熷褰㈠紡杩涜閲嶅啓銆�姣斿锛屽甫閫氶厤绗︽煡璇€�妯＄硦鏌ヨ銆佸墠缂�煡璇互鍙婅寖鍥存煡璇�
			 * 绛夛紝閮借閲嶅啓涓築oolenaQuery涓墍浣跨敤鐨勯」銆�
			 * 鍦ㄥ皢Query瀹炰緥浼犻�鍒癚ueryScorer涔嬪墠锛屽彲浠ヨ皟鐢≦uery.rewrite
			 * (IndexReader)鏂规硶鏉ラ噸鍐橯uery瀵硅薄
			 */
			Scorer fragmentScorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
			Fragmenter fragmenter = new SimpleFragmenter(100);
			/*
			 * Highlighter鍒╃敤Fragmenter灏嗗師濮嬫枃鏈垎鍓叉垚澶氫釜鐗囨銆�
			 * 鍐呯疆鐨凷impleFragmenter灏嗗師濮嬫枃鏈垎鍓叉垚鐩稿悓澶у皬鐨勭墖娈碉紝鐗囨榛樿鐨勫ぇ灏忎负100涓瓧绗︺�杩欎釜澶у皬鏄彲鎺у埗鐨勩�
			 */
			highlighter.setTextFragmenter(fragmenter);
	
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			Map<String,Class> fieldMap = null;
		
			Map<String,Object> mapItem = null;
			if(autoFill){
				fieldMap = EntityHelper.analyzeEntity(abstractLuceneDoc);
			}
			int start=0,end = scoreDocs.length-1;
			if(searchBean.getStart()!=null){
				start = searchBean.getStart();
			}
			if(searchBean.getEnd()!=null){
				end = searchBean.getEnd();
			}
			for (;start<=end;start++) {
				ScoreDoc scDoc = scoreDocs[start];
				Document document = indexSearcher.doc(scDoc.doc);
				LuceneDocItem item = null;
				if(fieldMap!=null&&fieldMap.size()>0){
					mapItem = new HashMap<String, Object>();
					for (Entry<String, Class> fieldItem : fieldMap.entrySet()) {
						String value = document.get(fieldItem.getKey());
						if(value!=null && !value.isEmpty()){
							String lighterFunciton = highlighter.getBestFragment(analyzer,fieldItem.getKey(), value);
							if (null != lighterFunciton) {
								value = lighterFunciton;
							}
						}
						mapItem.put(fieldItem.getKey(), value);
					}
					item = (T) abstractLuceneDoc.getClass().newInstance();
					EntityHelper.transMap2Bean2(mapItem,  item);
				}else {
					item = abstractLuceneDoc.fillData(document,analyzer,highlighter);
					if(item==null){
						throw new Exception("fillData return is null");
					}
				}
				if(item!=null){
					result.add(item);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.getIndexReader().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return result;
	}
}
