package in.nareshit.raghu.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface MyCollectionUtil {

	//JDK 1.8 Static Method in Interface
	public static Map<String,String> corvertListToMap(List<Object[]> list){
		//JDK 8 - Stream (Convert List->Map)
				/*Map<Integer,String> map =
						list.stream()
						.collect(
								Collectors.toMap(
										ob->Integer.valueOf(ob.toString()), 
										ob->ob.toString())
								);*/
		Map<String,String> map = new LinkedHashMap<>();
		for(Object[] ob:list) {
			map.put(
					ob[0].toString(), 
					ob[1].toString()
				);
		}
		return map;
	}
}
