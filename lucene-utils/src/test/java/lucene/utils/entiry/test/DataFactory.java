package lucene.utils.entiry.test;


import java.util.ArrayList;
import java.util.List;

import lucene.utils.entity.LuceneDocItem;

/**
 * 
 *
 *  @version 锛�1.0
 *  
 *  @author  锛�鑻忚嫢骞�             <a href="mailto:DennisIT@163.com">鍙戦�閭欢</a>
 *    
 *  @since   锛�1.0        鍒涘缓鏃堕棿:    2013-4-7    涓嬪崍01:54:34
 *     
 *  @function锛�TODO        
 *
 */
public class DataFactory {

    
    private static DataFactory dataFactory = new DataFactory();
    
    private DataFactory(){
        
    }
    
    public List<Medicine> getData(){
        List<Medicine> list = new ArrayList<Medicine>();
        list.add(new Medicine(1,"閾惰姳 鎰熷啋棰楃矑","鍔熻兘涓绘不锛氶摱鑺辨劅鍐掗绮�锛屽ご鐥�娓呯儹锛岃В琛紝鍒╁捊銆"));
        list.add(new Medicine(2,"鎰熷啋 姝㈠挸绯栨祮","鍔熻兘涓绘不锛氭劅鍐掓鍜崇硸娴�瑙ｈ〃娓呯儹锛屾鍜冲寲鐥般�"));
        list.add(new Medicine(3,"鎰熷啋鐏甸绮�","鍔熻兘涓绘不锛氳В鐑晣鐥涖�澶寸棝 ,娓呯儹銆�"));
        list.add(new Medicine(4,"鎰熷啋鐏佃兌鍥�","鍔熻兘涓绘不锛氶摱鑺辨劅鍐掗绮�锛屽ご鐥�娓呯儹锛岃В琛紝鍒╁捊銆�"));
        list.add(new Medicine(5,"浠佸拰 鎰熷啋棰楃矑","鍔熻兘涓绘不锛氱枏椋庢竻鐑紝瀹ｈ偤姝㈠挸,瑙ｈ〃娓呯儹锛屾鍜冲寲鐥般�"));
        return list;
        
    }
    
    public static DataFactory getInstance(){
        return dataFactory;
    }
}