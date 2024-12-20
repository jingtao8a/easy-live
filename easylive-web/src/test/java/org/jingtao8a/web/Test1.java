package org.jingtao8a.web;

import io.lettuce.core.ScriptOutputType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Test1 {
    private static class Record {
        int a;
        Record() {}
        Record(int a) { this.a = a; }
    }
    @Test
    public void test() {
//        List<String> keyList = Arrays.asList("one", "two", "three");
//
//        List<String> valueList = Arrays.asList("1", "2", "3");
//
//        Map<String, String> resultMap = keyList.stream().collect(Collectors.toMap(key -> key, key -> valueList.get(keyList.indexOf(key))));
//
//        log.info("resultMap: {}", resultMap);
        List<Record> list1 = new ArrayList<>();
        Record[] records1 = {new Record(1), new Record(2), new Record(3)};
        for (Record record : records1) {
            list1.add(record);
        }
        List<Record> list2 = new ArrayList<>();
        Record[] records2 = {new Record(7), new Record(8), new Record(9)};
        for (Record record : records2) {
            list2.add(record);
        }
        list2.addAll(0, list1);
        list2.get(0).a = 0;
        for (Record record : list1) {
            System.out.println(record.a);
        }
    }
}
