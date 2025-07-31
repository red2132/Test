import java.util.*;

public class ComplexMapExample {
    public static void main(String[] args) {
        // 최상위 LinkedHashMap 생성
        LinkedHashMap<String, List<Map<String, TreeMap<String, String>>>> complexMap = new LinkedHashMap<>();

        for (int i = 1; i <= 3; i++) {
            String linkedHashMapKey = "group" + i;
            List<Map<String, TreeMap<String, String>>> list = new ArrayList<>();

            for (int j = 1; j <= 3; j++) {
                Map<String, TreeMap<String, String>> hashMap = new HashMap<>();
                TreeMap<String, String> treeMap = new TreeMap<>();

                // TreeMap에 데이터 추가
                for (int k = 1; k <= 3; k++) {
                    treeMap.put("key" + k, "value" + i + j + k); // ex) value123
                }

                hashMap.put("item" + j, treeMap); // HashMap에 TreeMap 추가
                list.add(hashMap); // 리스트에 HashMap 추가
            }

            complexMap.put(linkedHashMapKey, list); // LinkedHashMap에 리스트 추가
        }

        // 출력 확인
        System.out.println(complexMap);
    }
}
