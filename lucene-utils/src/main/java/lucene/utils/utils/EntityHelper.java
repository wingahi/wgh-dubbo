package lucene.utils.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lucene.utils.annotation.IndexField;
import lucene.utils.entity.EntityValueMapper;

import org.apache.commons.beanutils.BeanUtils;

public final class EntityHelper {

	public static Map<String,Class> analyzeEntity(Object entity) {
		Map<String,Class> fieldMap = new HashMap<String,Class>();
		String result = "";
		int beginnumber = 0;
		int endnumber = 0;

		try {

			if (entity == null) {

			} else {
				Field[] fields = entity.getClass().getDeclaredFields();
				Method[] menthods = entity.getClass().getMethods();
				List param = new ArrayList();
				for (int i = 0; menthods != null && i < menthods.length; i++) {
					menthods[i].setAccessible(true); // 鏆村姏鍙嶅皠
					String column = menthods[i].getName();
					if ((!column.equals("getClass")) && column.contains("get")) {
						Object value = menthods[i].invoke(entity,
								new Object[] {});
						Type type = menthods[i].getGenericReturnType();
						String classtype = type.toString()
								.replace("class ", "");
						// 鐢ㄤ簬寮哄埗杞崲
						Class cls = Class.forName(classtype);

						String method = menthods[i].getName();
						method = method.replace("get", "");
						method = method.substring(0,1).toLowerCase()+method.substring(1);
						fieldMap.put(method, cls);
					}
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return fieldMap;
	}
	
	public static Map<String,EntityValueMapper> analyzeEntityValue(Object entity) {
		Map<String,EntityValueMapper> fieldMap = new HashMap<String,EntityValueMapper>();
		String result = "";
		int beginnumber = 0;
		int endnumber = 0;
		try {
			if (entity == null) {

			} else {
				Field[] fields = entity.getClass().getDeclaredFields();
				Method[] menthods = entity.getClass().getMethods();
				EntityValueMapper evMapper = null;
				for (int i = 0; menthods != null && i < menthods.length; i++) {
					menthods[i].setAccessible(true); // 鏆村姏鍙嶅皠
					String column = menthods[i].getName();
					if ((!column.equals("getClass")) && column.contains("get")) {
						Object value = menthods[i].invoke(entity,
								new Object[] {});
						evMapper = new EntityValueMapper();
						Type type = menthods[i].getGenericReturnType();
						IndexField indexField = menthods[i].getAnnotation(IndexField.class);
						if(indexField!=null){
							String method = menthods[i].getName();
							method = method.replace("get", "");
							method = method.substring(0,1).toLowerCase()+method.substring(1);
							evMapper.setIndex(indexField.index());
							evMapper.setStore(indexField.store());
							evMapper.setValue(value);
						    fieldMap.put(method, evMapper);
					    }
			
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return fieldMap;
	}
	
	// Map --> Bean 2: 鍒╃敤org.apache.commons.beanutils 宸ュ叿绫诲疄鐜�Map --> Bean  
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
  
    // Map --> Bean 1: 鍒╃敤Introspector,PropertyDescriptor瀹炵幇 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 寰楀埌property瀵瑰簲鐨剆etter鏂规硶  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
  
    // Bean --> Map 1: 鍒╃敤Introspector鍜孭ropertyDescriptor 灏咮ean --> Map  
    public static Map<String, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 杩囨护class灞炴�  
                if (!key.equals("class")) {  
                    // 寰楀埌property瀵瑰簲鐨刧etter鏂规硶  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
}
