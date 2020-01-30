package leetcode.normal;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Anur IjuoKaruKas on 2020/1/6
 * 17. 电话号码的字母组合
 *
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 */
public class LetterCombinations {

    public static Map<Character, List<Character>> MAPPING = new HashMap<>();

    static {
        List<Character> list = new ArrayList();
        list.add('a');
        list.add('b');
        list.add('c');
        MAPPING.put('2', list);

        list = new ArrayList();
        list.add('d');
        list.add('e');
        list.add('f');
        MAPPING.put('3', list);

        list = new ArrayList();
        list.add('g');
        list.add('h');
        list.add('i');
        MAPPING.put('4', list);

        list = new ArrayList();
        list.add('j');
        list.add('k');
        list.add('l');
        MAPPING.put('5', list);

        list = new ArrayList();
        list.add('m');
        list.add('n');
        list.add('o');
        MAPPING.put('6', list);

        list = new ArrayList();
        list.add('p');
        list.add('q');
        list.add('r');
        list.add('s');
        MAPPING.put('7', list);

        list = new ArrayList();
        list.add('t');
        list.add('u');
        list.add('v');
        MAPPING.put('8', list);

        list = new ArrayList();
        list.add('w');
        list.add('x');
        list.add('y');
        list.add('z');
        MAPPING.put('9', list);
    }

    public static void main(String[] args) {
        List<String> strings = letterCombinations("234");

        System.out.println();
    }

    public static List<String> letterCombinations(String digits) {
        char[] chars = digits.toCharArray();

        int totalPossible = 0;
        for (char c : chars) {
            int size = MAPPING.get(c)
                              .size();
            totalPossible = totalPossible == 0 ? size : totalPossible * size;
        }

        Map<Integer, StringBuilder> result = new HashMap<>();

        int nowIndex = 0;
        for (char c : chars) {
            List<Character> characters = MAPPING.get(c);
            int size = characters.size();
            int internal = totalPossible / size;

            for (int i = 0; i < characters.size(); i++) {
                charReplacement(result, i * internal, (i + 1) * internal, nowIndex, characters.get(i));
            }
            nowIndex++;
        }

        return result.values()
                     .stream()
                     .map(StringBuilder::toString)
                     .collect(Collectors.toList());
    }

    public static void charReplacement(Map<Integer, StringBuilder> strings, int from, int to, int index, Character c) {
        System.out.println();
        for (int i = from; i < to; i++) {

            strings.compute(i, (integer, stringBuilder) -> {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.replace(index, index + 1, String.valueOf(c));
                return stringBuilder;
            });
        }
    }
}
