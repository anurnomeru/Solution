package leetcode.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.Result;
import org.apache.log4j.net.SyslogAppender;

/**
 * Created by Anur IjuoKaruKas on 2020/2/15
 * <p>
 * 30. 串联所有单词的子串
 * <p>
 * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * <p>
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
 * <p>
 * 示例 1：
 * <p>
 * 输入：
 * s = "barfoothefoobarman",
 * words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 * <p>
 * 输入：
 * s = "wordgoodgoodgoodbestword",
 * <p>
 * words = ["word","good","best","word"]
 * 输出：[]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindSubstring {

    //"barfoofoobarthefoobarman"
    //["bar","foo","the"]
    public static void main(String[] args) {
//        for (Integer i : findSubstring(
//            "pjzkrkevzztxductzzxmxsvwjkxpvukmfjywwetvfnujhweiybwvvsrfequzkhossmootkmyxgjgfordrpapjuunmqnxxdrqrfgkrsjqbszgiqlcfnrpjlcwdrvbumtotzylshdvccdmsqoadfrpsvnwpizlwszrtyclhgilklydbmfhuywotjmktnwrfvizvnmfvvqfiokkdprznnnjycttprkxpuykhmpchiksyucbmtabiqkisgbhxngmhezrrqvayfsxauampdpxtafniiwfvdufhtwajrbkxtjzqjnfocdhekumttuqwovfjrgulhekcpjszyynadxhnttgmnxkduqmmyhzfnjhducesctufqbumxbamalqudeibljgbspeotkgvddcwgxidaiqcvgwykhbysjzlzfbupkqunuqtraxrlptivshhbihtsigtpipguhbhctcvubnhqipncyxfjebdnjyetnlnvmuxhzsdahkrscewabejifmxombiamxvauuitoltyymsarqcuuoezcbqpdaprxmsrickwpgwpsoplhugbikbkotzrtqkscekkgwjycfnvwfgdzogjzjvpcvixnsqsxacfwndzvrwrycwxrcismdhqapoojegggkocyrdtkzmiekhxoppctytvphjynrhtcvxcobxbcjjivtfjiwmduhzjokkbctweqtigwfhzorjlkpuuliaipbtfldinyetoybvugevwvhhhweejogrghllsouipabfafcxnhukcbtmxzshoyyufjhzadhrelweszbfgwpkzlwxkogyogutscvuhcllphshivnoteztpxsaoaacgxyaztuixhunrowzljqfqrahosheukhahhbiaxqzfmmwcjxountkevsvpbzjnilwpoermxrtlfroqoclexxisrdhvfsindffslyekrzwzqkpeocilatftymodgztjgybtyheqgcpwogdcjlnlesefgvimwbxcbzvaibspdjnrpqtyeilkcspknyylbwndvkffmzuriilxagyerjptbgeqgebiaqnvdubrtxibhvakcyotkfonmseszhczapxdlauexehhaireihxsplgdgmxfvaevrbadbwjbdrkfbbjjkgcztkcbwagtcnrtqryuqixtzhaakjlurnumzyovawrcjiwabuwretmdamfkxrgqgcdgbrdbnugzecbgyxxdqmisaqcyjkqrntxqmdrczxbebemcblftxplafnyoxqimkhcykwamvdsxjezkpgdpvopddptdfbprjustquhlazkjfluxrzopqdstulybnqvyknrchbphcarknnhhovweaqawdyxsqsqahkepluypwrzjegqtdoxfgzdkydeoxvrfhxusrujnmjzqrrlxglcmkiykldbiasnhrjbjekystzilrwkzhontwmehrfsrzfaqrbbxncphbzuuxeteshyrveamjsfiaharkcqxefghgceeixkdgkuboupxnwhnfigpkwnqdvzlydpidcljmflbccarbiegsmweklwngvygbqpescpeichmfidgsjmkvkofvkuehsmkkbocgejoiqcnafvuokelwuqsgkyoekaroptuvekfvmtxtqshcwsztkrzwrpabqrrhnlerxjojemcxel",
//            new String[] {
//                "dhvf",
//                "sind",
//                "ffsl",
//                "yekr",
//                "zwzq",
//                "kpeo",
//                "cila",
//                "tfty",
//                "modg",
//                "ztjg",
//                "ybty",
//                "heqg",
//                "cpwo",
//                "gdcj",
//                "lnle",
//                "sefg",
//                "vimw",
//                "bxcb"
//            })) {
//            System.out.println(i);
//        }

        for (Integer i : findSubstring(
            "aaa",
            new String[] {
                "a",
                "a"
            })) {
            System.out.println(i);
        }
                System.out.println(kmpDispatcher("barfoofoobarthefoobarman", "foobarthe"));
                System.out.println(kmpDispatcher("goodgoodgoodbestword", "goodgoodbestword"));
                System.out.println(kmpDispatcher("wgggbw", "ggbw"));
                System.out.println(kmpDispatcher("aaa", "aa"));
    }

    //    public static List<Integer> findSubstring(String s, String[] words) {
    //        if (s == null || s.length() == 0 || words == null || words.length == 0) {
    //            return new ArrayList<>();
    //        }
    //
    //        Set<List<String>> union = new HashSet<>();
    //        Set<List<String>> unionPatch = new HashSet<>();
    //
    //        List<String> init = new ArrayList<>();
    //        init.add(words[0]);
    //        union.add(init);
    //        for (int i = 1; i < words.length; i++) {
    //            String thisWord = words[i];
    //            for (List<String> strings : union) {
    //                for (int i1 = 0; i1 <= strings.size(); i1++) {
    //                    List<String> l = new ArrayList<>(strings);
    //                    l.add(i1, thisWord);
    //                    unionPatch.add(l);
    //                }
    //            }
    //
    //            union = unionPatch;
    //            unionPatch = new HashSet<>();
    //        }
    //
    //        Set<String> allDispatcher = new HashSet<>();
    //        for (List<String> strings : union) {
    //            StringBuilder sb = new StringBuilder();
    //            for (String string : strings) {
    //                sb.append(string);
    //            }
    //            allDispatcher.add(sb.toString());
    //        }
    //
    //        Set<Integer> integers = new HashSet<>();
    //        for (String s1 : allDispatcher) {
    //            integers.addAll(kmpDispatcher(s, s1));
    //        }
    //        return new ArrayList<>(integers);
    //    }
    public static List<Integer> findSubstring(String s, String[] words) {

        List<List<Integer>> results = new ArrayList<>();
        Map<Integer, Integer> indexAndWordsDiffMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            List<Integer> indexs = kmpDispatcher(s, words[i]);
            for (Integer index : indexs) {
                indexAndWordsDiffMap.put(index, i);
            }

            if (indexs.size() != 0) {
                results.add(indexs);
            }
        }

        if (results.size() != words.length) {
            return new ArrayList<>();
        }

        return null;
    }

    public static List<Integer> kmpDispatcher(String source, String dispatcher) {
        char[] dispatcherArr = dispatcher.toCharArray();
        int[] commonArr = new int[dispatcherArr.length];

        char firstDispatcher = dispatcherArr[0];

        int commonNum = 0;
        for (int i = 1; i < dispatcherArr.length; i++) {

            char dispatcherChar = dispatcherArr[i];
            char commonChar = dispatcherArr[commonNum];
            if (dispatcherChar != commonChar) {
                commonChar = firstDispatcher;
                commonNum = 0;
            }
            if (dispatcherChar == commonChar) {
                commonNum++;
            }
            commonArr[i] = commonNum;
        }

        System.arraycopy(commonArr, 0, commonArr, 1, commonArr.length - 1);

        char[] sourceCharArr = source.toCharArray();
        int dispatcherPointer = 0;
        int sourcePointer = 0;

        List<Integer> result = new ArrayList<>();

        while (sourcePointer < sourceCharArr.length) {
            char sourceChar = sourceCharArr[sourcePointer];
            char dispatcherChar = dispatcherArr[dispatcherPointer];

            boolean back = false;
            if (sourceChar == dispatcherChar) {
                if (dispatcherPointer == dispatcherArr.length - 1) {
                    int index = sourcePointer - (dispatcherArr.length - 1);
                    result.add(index);
                    back = true;
                } else {
                    dispatcherPointer++;
                    sourcePointer++;
                }
            } else {
                back = true;
            }
            if (back) {
                dispatcherPointer = commonArr[dispatcherPointer];
                if (dispatcherPointer == 0) {
                    if (sourceChar == firstDispatcher) {
                        dispatcherPointer++;
                    }
                    sourcePointer++;
                }
            }
        }

        return result;
    }

    // 00010
    // ASASD
    // ASASASD
}
