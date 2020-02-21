package leetcode.hard;


/**
 * 32. 最长有效括号
 *
 * Created by Anur IjuoKaruKas on 2020/2/20
 * <p>
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * 示例 2:
 * <p>
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestValidParentheses {

    public static char LEFT = '(';
    public static char RIGHT = ')';

    public static void main(String[] args) {
        //        System.out.println(longestValidParentheses("()()()((())())"));
        //        System.out.println(longestValidParentheses("(()"));
        //        System.out.println(longestValidParentheses(")()())"));
//                System.out.println(longestValidParentheses(")(((((()())()()))()(()))("));
//        System.out.println(longestValidParentheses("(()(())))))))"));
        System.out.println(longestValidParentheses("()(())"));
    }

    public static int longestValidParentheses(String s) {
        int length = s.length();
        if (length < 2) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int[] marked = new int[chars.length];

        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            char now = chars[i];
            if (now == RIGHT && i > 0 && chars[i - 1] == LEFT) {
                max = Math.max(max, validParentheses(chars, marked, i - 1));
            }
        }

        return max;
    }

    public static int validParentheses(char[] chars, int[] marked, int leftIndex) {
        if (marked[leftIndex] == 1) {
            return 0;
        }
        int rightIndex = leftIndex + 1;
        marked[leftIndex] = 1;
        marked[rightIndex] = 1;
        System.out.println(leftIndex + "和" + rightIndex);

        int result = 2;

        while (true) {

            while (true) {
                if (leftIndex - 1 >= 0 && marked[leftIndex - 1] == 1) {
                    leftIndex--;
                    result++;
                } else {
                    break;
                }
            }
            while (true) {
                if (rightIndex + 1 < chars.length && marked[rightIndex + 1] == 1) {
                    rightIndex++;
                    result++;
                } else {
                    break;
                }
            }

            if (leftIndex - 1 >= 0 && chars[leftIndex - 1] == LEFT && rightIndex + 1 <= chars.length - 1 && chars[rightIndex + 1] == RIGHT) {
                leftIndex--;
                rightIndex++;
                marked[leftIndex] = 1;
                marked[rightIndex] = 1;

                System.out.println(leftIndex + "和" + rightIndex);
            } else if (leftIndex - 2 >= 0 && chars[leftIndex - 2] == LEFT && chars[leftIndex - 1] == RIGHT) {
                leftIndex -= 2;
                marked[leftIndex] = 1;
                marked[leftIndex + 1] = 1;

                System.out.println(leftIndex + "和" + (leftIndex + 1));
            } else if (rightIndex + 2 <= chars.length - 1 && chars[rightIndex + 2] == RIGHT && chars[rightIndex + 1] == LEFT) {
                rightIndex += 2;
                marked[rightIndex] = 1;
                marked[rightIndex - 1] = 1;

                System.out.println(rightIndex - 1 + "和" + (rightIndex));
            } else {
                break;
            }
            result += 2;
        }

        return result;
    }
}
