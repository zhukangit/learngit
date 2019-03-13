package test;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/1/31 9:47
 * @功能说明： 功能描述
 */




import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class MapTest2 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        Map<Integer, Object> map4 = new HashMap<Integer, Object>();
        String str = null ;
        String str2 = "str2";
        map.put("str", str);
        map.put("str2", str2);
        map.put("b", "map1");
        map.put("a", null);
        map.put("aa", null);

        map.put("c", map2);
        map2.put("11", "map2");
        map2.put("12", null);

        map2.put("d", map3);
        map3.put("111", "map3");
        map3.put("121", null);
        map3.put("", null);
        map3.put(null, null);

        map4.put(1234, "map2value");
        map4.put(12345, null);
        map4.put(5432, "");
        map3.put("map4", map4);
        System.out.println(map);
        System.out.println(MapTestUtil.mapRemoveNullByRecursion(map));



    }
}