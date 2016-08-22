package lucene.utils.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;

public class LuceneFilter {
	@SuppressWarnings("deprecation")
	private List<Filter> filterList; 
    public LuceneFilter(){ 
        filterList = new ArrayList<Filter>(); 
    } 
    @SuppressWarnings("deprecation")
	public void addFilter(String Field,String Value){ 
        Term term=new Term(Field,Value);//娣诲姞term 
        QueryWrapperFilter filter=new QueryWrapperFilter(new TermQuery(term));//娣诲姞杩囨护鍣�
        filterList.add(filter);//鍔犲叆List锛屽彲浠ュ鍔犲鍊嬭繃婊�
    } 
    @SuppressWarnings("deprecation")
	public Query getFilterQuery(Query query){ 
        for(int i=0;i<filterList.size();i++){ 
            //鍙栧嚭澶氬�杩囨护鍣紝鍦ㄧ粨鏋滀腑鍐嶆瀹氫綅缁撴灉 
            query = new FilteredQuery(query, filterList.get(i)); 
        } 
        return query; 
    }    
}
