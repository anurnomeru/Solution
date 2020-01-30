package leetcode.simple;

/**
 * Created by Anur IjuoKaruKas on 2020/1/30
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * <p>
 * 输入: "{[]}"
 * 输出: true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IsValid {

    private static char l1 = '(';
    private static char r1 = ')';
    private static char l2 = '[';
    private static char r2 = ']';
    private static char l3 = '{';
    private static char r3 = '}';

    private static char sentinel = ' ';

    public static boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        char[] chars = s.toCharArray();
        int length = chars.length;
        int nowLength = length;

        int pointer = 0;
        while (true) {
            if (pointer == nowLength) {
                break;
            }

            char nowChar = chars[pointer];
            if (nowChar == sentinel) {
                break;
            }

            Character nextChar = null;
            if (nowChar == l1) {
                nextChar = r1;
            } else if (nowChar == l2) {
                nextChar = r2;
            } else if (nowChar == l3) {
                nextChar = r3;
            }

            if (nextChar != null && pointer + 1 < nowLength && chars[pointer + 1] == nextChar) {
                int srcFrom = pointer + 2;
                int tarFrom = pointer;
                int copyLength = length - srcFrom;

                System.arraycopy(chars, srcFrom, chars, tarFrom, copyLength);
                nowLength = nowLength - 2;
                chars[nowLength] = sentinel;
                chars[nowLength + 1] = sentinel;

                pointer = Math.max(0, pointer - 1);
            } else {
                pointer++;
            }
        }

        return chars[0] == sentinel;
    }
}
