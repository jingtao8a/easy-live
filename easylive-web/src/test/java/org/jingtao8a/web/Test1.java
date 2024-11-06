package org.jingtao8a.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Test1 {
    @Test
    public void test() {
        List<String> keyList = Arrays.asList("one", "two", "three");

        List<String> valueList = Arrays.asList("1", "2", "3");

        Map<String, String> resultMap = keyList.stream().collect(Collectors.toMap(key -> key, key -> valueList.get(keyList.indexOf(key))));
        
        log.info("resultMap: {}", resultMap);
    }
}
