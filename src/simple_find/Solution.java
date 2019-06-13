package simple_find;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Anur IjuoKaruKas on 2019/6/12
 */
public class Solution {

    public static void main(String[] args) {

    }


    private ConcurrentSkipListMap<Integer, FeeNode> feeMap = new ConcurrentSkipListMap<>();

    private Map<Integer, FeeNode> noMap = new ConcurrentHashMap<>();

    private static class FeeNode {

        public FeeNode() {
            this.count = 1;
        }

        public Integer count;
    }
}
