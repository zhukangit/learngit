package test;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/1/31 9:46
 * @功能说明： 功能描述
 */




import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class MapTestUtil {

    /**
     * 递归删除map中的null值
     * @param map
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> mapRemoveWithNullByRecursion(Map<String, Object> map){
        Set<Entry<String, Object>> set = map.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        Map map2 = new HashMap();
        while(it.hasNext()){
            Entry<String, Object> en = it.next();
            if(!(en.getValue() instanceof Map)){
                if(null == en.getValue() || "".equals(en.getValue())){
                    it.remove();
                }
            }else{
                map2 = (Map) en.getValue();
                mapRemoveWithNullByRecursion(map2);
            }
        }
        return map;
    }

    /**
     * 递归删除map中的null值
     * @param obj
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object mapRemoveNullByRecursion(Object obj){
        Map<String,Object> map=(Map<String,Object>)obj;
        Set<Entry<String, Object>> set = map.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        Map map2 = new HashMap();
        while(it.hasNext()){
            Entry<String, Object> en = it.next();
            if(!(en.getValue() instanceof Map)){
                if(null == en.getValue() || "".equals(en.getValue())){
                    it.remove();
                }
            }else{
                map2 = (Map) en.getValue();
                mapRemoveWithNullByRecursion(map2);
            }
        }
        return obj;
    }
}


