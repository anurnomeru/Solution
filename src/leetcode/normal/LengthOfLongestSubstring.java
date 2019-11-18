package leetcode.normal;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Anur IjuoKaruKas on 2019/11/9
 *
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * qwertyuioqwertyuio
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();

        int firstSame = 0;
        int max = 0;
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char aChar = chars[i];
            if (hashMap.containsKey(aChar)) {
                Integer samePosition = hashMap.get(aChar);
                for (int j = firstSame; j <= samePosition; j++) {
                    hashMap.remove(chars[j]);
                }
                firstSame = samePosition + 1;
            }
            hashMap.put(aChar, i);
            max = Math.max(hashMap.size(), max);
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("bpfbhmipx"));

        System.out.println(kmpTest("qweqwerreq", "qweqweqwer"));
    }

    public static boolean kmpTest(String str, String tar) {
        char[] chars = str.toCharArray();
        int strLength = chars.length;
        int[] onajiMatcher = new int[strLength];

        int onajiMarker = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = onajiMarker; j < i; j++) {
                if (chars[i] == chars[j]) {
                    onajiMarker = j;
                    onajiMatcher[i + 1] = j;
                    onajiMarker++;
                    break;
                } else {
                    onajiMarker = 0;
                    onajiMatcher[i + 1] = 0;
                }
            }
        }

        char[] tarChars = tar.toCharArray();
        int size = tarChars.length;
        int pointer = 0;

        for (int i = 0; i < size; i++) {
            char tarChar = tarChars[i];
            for (int j = pointer; j < i; j++) {
                char matcher = chars[j];
                if (matcher == tarChar) {
                    pointer++;
                    if (pointer == strLength) {
                        return true;
                    }
                } else {
                    pointer = onajiMatcher[j];
                }
                break;
            }
        }

        return false;
    }
}
