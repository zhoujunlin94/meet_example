package io.github.zhoujunlin94.example.web;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2023年05月09日 9:53
 * @desc
 */
public class GuavaTest {

    @Test
    public void test1() {
        // jdk 实现双键map
        Map<String, Map<String, Integer>> map = new HashMap<>();
        //存放元素
        Map<String, Integer> workMap = new HashMap<>();
        workMap.put("Jan", 20);
        workMap.put("Feb", 28);
        map.put("Hydra", workMap);
        //取出元素
        Integer dayCount = map.get("Hydra").get("Jan");
        System.out.println(dayCount);
    }

    @Test
    public void test2() {
        HashBasedTable<String, String, Integer> table = HashBasedTable.create();
        //存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        //取出元素
        Integer dayCount = table.get("Hydra", "Jan");
        System.out.println(dayCount);

        Set<String> rowKeySet = table.rowKeySet();
        System.out.println(rowKeySet);

        Set<String> columnKeySet = table.columnKeySet();
        System.out.println(columnKeySet);

        Collection<Integer> values = table.values();
        System.out.println(values);

        // 遍历
        for (String rowKey : table.rowKeySet()) {
            Set<Map.Entry<String, Integer>> rows = table.row(rowKey).entrySet();
            int total = 0;
            for (Map.Entry<String, Integer> row : rows) {
                total += row.getValue();
            }
            System.out.println(rowKey + ":" + total);
        }

        // 行列转换
        Table<String, String, Integer> table2 = Tables.transpose(table);
        Set<Table.Cell<String, String, Integer>> cells = table2.cellSet();
        cells.forEach(cell -> {
            System.out.println(cell.getRowKey() + ":" + cell.getColumnKey() + ":" + cell.getValue());
        });

        // 转换为JDK的嵌套Map
        Map<String, Map<String, Integer>> rowMap = table.rowMap();
        Map<String, Map<String, Integer>> columnMap = table.columnMap();
        System.out.println(rowMap);
        System.out.println(columnMap);

    }

    @Test
    public void test3() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("Hydra", "Programmer");
        biMap.put("Tony", "IronMan");
        biMap.put("Thanos", "Titan");
        //使用key获取value
        System.out.println(biMap.get("Tony"));

        BiMap<String, String> inverse = biMap.inverse();
        //使用value获取key
        System.out.println(inverse.get("Titan"));

        // 覆盖了原biMap中数据 Tony->Stark
        inverse.put("IronMan", "Stark");
        System.out.println(biMap);

        // 报错  key value均不可重复
        //biMap.put("Thanos2","Titan");
        // 强行覆盖
        biMap.forcePut("Thanos2", "Titan");
        System.out.println(biMap);
    }

    @Test
    public void test4() {
        // 多值Map
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day", 1);
        multimap.put("day", 2);
        multimap.put("day", 8);
        multimap.put("month", 3);
        System.out.println(multimap);
    }

    @Test
    public void test5() {

        System.out.println(Range.closedOpen(0, 60));

        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0, 60), "fail");
        rangeMap.put(Range.closed(60, 90), "satisfactory");
        rangeMap.put(Range.openClosed(90, 100), "excellent");

        System.out.println(rangeMap.get(59));
        System.out.println(rangeMap.get(60));
        System.out.println(rangeMap.get(90));
        System.out.println(rangeMap.get(91));
    }

}
