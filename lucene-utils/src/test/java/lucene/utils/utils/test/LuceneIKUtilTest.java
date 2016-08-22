package lucene.utils.utils.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lucene.utils.entiry.test.DataFactory;
import lucene.utils.entiry.test.Medicine;
import lucene.utils.entity.BooleanQueryItem;
import lucene.utils.entity.LuceneDocItem;
import lucene.utils.entity.SearchBean;
import lucene.utils.utils.BooleanQueryUtil;
import lucene.utils.utils.LuceneIKUtil;
import lucene.utils.utils.MultiFieldQueryParserUtil;
import lucene.utils.utils.TermRangeQueryUtil;

import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;

public class LuceneIKUtilTest {


	public static void main(String[] args) {
		String[] fields = { "name", "function" };
		//LuceneIKUtil<Medicine> luceneProcess = new MultiFieldQueryParserUtil<Medicine>("F:/index",fields, "鍒╁捊");
		//List<BooleanQueryItem> queryItems =new ArrayList<BooleanQueryItem>();
		//LuceneIKUtil<Medicine> luceneProcess = new BooleanQueryUtil<Medicine>("F:/index", queryItems);
		LuceneIKUtil<Medicine> luceneProcess = new TermRangeQueryUtil<Medicine>("F:/index", "id", "1", "4");
		try {
			luceneProcess.createIndex(DataFactory.getInstance().getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 淇敼娴嬭瘯
		//luceneProcess.update(new Medicine(1,"閾惰姳 鎰熷啋棰楃矑12522jjjhh33","鍔熻兘涓绘不锛氶摱鑺辨劅鍐掗绮�锛屽ご鐥�娓呯儹锛岃В琛紝鍒╁捊銆�));

		// 鏌ヨ娴嬭瘯
		
		
		Map<String,Object> mapFilter = new HashMap<String,Object>(); 
		//mapFilter.put("id", 1);
		SearchBean searchBean = new SearchBean();
		//searchBean.setN(1);
		SortField[] sortFields=new SortField[1];
		sortFields[0]=new SortField(null,Type.DOC,false);
		//searchBean.setShardHits(new TopDocs[]);
		searchBean.setSortFields(sortFields);
		List<LuceneDocItem> list = luceneProcess.search(new Medicine(),true,mapFilter,searchBean);
		for (int i = 0; i < list.size(); i++) {
			Medicine medicine = (Medicine)(list.get(i));
			System.out.println("(" + medicine.getId() + ")"
					+ medicine.getName() + "\t" + medicine.getFunction()+"\t"+medicine.getStrId()+"\t"+medicine.getDataType());
		}
		// 鍒犻櫎娴嬭瘯
		//luceneProcess.delete("Medicine:1");
	}
	
	
	
}
