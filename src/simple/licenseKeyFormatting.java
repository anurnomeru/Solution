package simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Created by Anur IjuoKaruKas on 2018/12/14
 * 给定一个密钥字符串S，只包含字母，数字以及 '-'（破折号）。N 个 '-' 将字符串分成了 N+1 组。给定一个数字 K，重新格式化字符串，除了第一个分组以外，每个分组要包含 K 个字符，第一个分组至少要包含 1 个字符。两个分组之间用 '-'（破折号）隔开，并且将所有的小写字母转换为大写字母。
 *
 * 给定非空字符串 S 和数字 K，按照上面描述的规则进行格式化。
 *
 * 示例 1：
 *
 * 输入：S = "5F3Z-2e-9-w", K = 4
 *
 * 输出："5F3Z-2E9W"
 *
 * 解释：字符串 S 被分成了两个部分，每部分 4 个字符；
 * 注意，两个额外的破折号需要删掉。
 * 示例 2：
 *
 * 输入：S = "2-5g-3-J", K = 2
 *
 * 输出："2-5G-3J"
 *
 * 解释：字符串 S 被分成了 3 个部分，按照前面的规则描述，第一部分的字符可以少于给定的数量，其余部分皆为 2 个字符。
 *
 *
 * 提示:
 *
 * S 的长度不超过 12,000，K 为正整数
 * S 只包含字母数字（a-z，A-Z，0-9）以及破折号'-'
 * S 非空
 */
public class licenseKeyFormatting {

    public static char c = "-".toCharArray()[0];

    public static void main(String[] args) {
        System.out.println(solution("111-222-333", 2));
    }

    public static String solution(String s, int k) {
        if (k < 0) {
            throw new RuntimeException();
        }

        char[] chars = s.toCharArray();
        List<Integer> needToRemoveIndex = new ArrayList<>();
        int effectChar = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                needToRemoveIndex.add(i);
            } else {
                effectChar++;
            }
        }

        char[] chars1 = remove(chars, needToRemoveIndex);

        if (k == 0) {
            return new String(chars1);
        }

        int needToAdd = (effectChar - 1) / k;

        List<Integer> needToAddIndex = new ArrayList<>();
        for (int i = 1; i <= needToAdd; i++) {
            needToAddIndex.add(i * k - 1);
        }

        if (needToAddIndex.size() == 0) {
            return new String(chars1);
        }

        return new String(add(chars1, needToAddIndex));
    }

    public static char[] add(char[] chars, List<Integer> indexs) {
        indexs = indexs.stream()
                       .sorted()
                       .collect(Collectors.toList());

        int length = chars.length;
        char[] newChars = new char[length + indexs.size()];
        int newCharsLength = newChars.length;

        int addCount = 0;

        System.arraycopy(chars, 0, newChars, 0, length);
        for (Integer index : indexs) {
            int trueIndex = index + addCount;
            System.arraycopy(newChars, trueIndex, newChars, trueIndex+1, newCharsLength - trueIndex - 1);
            newChars[trueIndex + 1] = c;
            addCount++;
        }
        return newChars;
    }

    public static char[] remove(char[] chars, List<Integer> indexs) {
        indexs = indexs.stream()
                       .sorted(Comparator.reverseOrder())
                       .collect(Collectors.toList());

        int length = chars.length;
        for (Integer index : indexs) {
            System.arraycopy(chars, index + 1, chars, index, length - index - 1);
        }

        int newLength = chars.length - indexs.size();
        char[] newChars = new char[newLength];

        System.arraycopy(chars, 0, newChars, 0, newLength);
        return newChars;
    }
}
