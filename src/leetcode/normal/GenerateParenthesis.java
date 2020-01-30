package leetcode.normal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Anur IjuoKaruKas on 2020/1/30
 * <p>
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 * <p>
 * 例如，给出 n = 3，生成结果为：
 * <p>
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 * <p>
 * ()()
 * (())
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class GenerateParenthesis {

    public static void main(String[] args) {
        List<String> strings = generateParenthesis(3);

        System.out.println();
    }

    public static List<String> generateParenthesis(int n) {
        List<StringBuilder> sb = new ArrayList<>();
        if (n == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(generateParenthesisRec(n));
    }

    public static Set<String> generateParenthesisRec(int i) {
        if (i == 1) {
            Set<String> strings = new HashSet<>();
            strings.add("()");
            return strings;
        } else {
            Set<String> neoBuilder = new HashSet<>();
            Set<String> strings = generateParenthesisRec(i - 1);
            for (String s : strings) {
                int length = s.length();
                for (int j = 0; j <= length; j++) {
                    String prev = s.substring(0, j);
                    String tail = s.substring(j,length);
                    neoBuilder.add(prev + "()" + tail);
                }
            }
            return neoBuilder;
        }
    }
}
