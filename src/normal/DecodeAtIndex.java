package normal;

import java.util.ArrayList;
import java.util.List;
import com.google.common.primitives.UnsignedLong;

/**
 * Created by Anur IjuoKaruKas on 4/21/2019
 *
 * 给定一个编码字符串 S。为了找出解码字符串并将其写入磁带，从编码字符串中每次读取一个字符，并采取以下步骤：
 *
 * 如果所读的字符是字母，则将该字母写在磁带上。
 * 如果所读的字符是数字（例如 d），则整个当前磁带总共会被重复写 d-1 次。
 * 现在，对于给定的编码字符串 S 和索引 K，查找并返回解码字符串中的第 K 个字母。
 */
public class DecodeAtIndex {

    public static List<Character> NumChar = new ArrayList<>();

    static {
        NumChar.add((char) 0x30);
        NumChar.add((char) 0x31);
        NumChar.add((char) 0x32);
        NumChar.add((char) 0x33);
        NumChar.add((char) 0x34);
        NumChar.add((char) 0x35);
        NumChar.add((char) 0x36);
        NumChar.add((char) 0x37);
        NumChar.add((char) 0x38);
        NumChar.add((char) 0x39);
    }

    public static void main(String[] args) {

        System.out.println(Long.MAX_VALUE);
                System.out.println(findChar("leet2code3".toCharArray(), 10));
                System.out.println(findChar("leet2code3".toCharArray(), 11));
                System.out.println(findChar("leet2code3".toCharArray(), 12));
                System.out.println(findChar("leet2code3".toCharArray(), 13));
                System.out.println(findChar("ha22".toCharArray(), 5));
                System.out.println(findChar("ha22".toCharArray(), 6));
    }

    public static char findChar(char[] chars, int k) {
        return find(chars, 0, UnsignedLong.ZERO, UnsignedLong.valueOf(k));
    }

    public static char find(char[] chars, int encodeIndex, UnsignedLong decodeStartIndex, UnsignedLong k) {
        CharSlot charSlot = getFirstCharSlot(chars, encodeIndex, decodeStartIndex);

        if (charSlot.decodeEndIndex.compareTo(k) >= 0) {
            UnsignedLong offset = k.minus(charSlot.decodeStartIndex)
                                   .plus(UnsignedLong.ONE);
            UnsignedLong trueOffset = offset.mod(UnsignedLong.valueOf(charSlot.string.length()));
            return charSlot.string.toCharArray()[trueOffset.intValue()];
        } else {
            return find(chars, charSlot.nextCharSlotIndex, charSlot.decodeEndIndex, k);
        }
    }

    public static CharSlot getFirstCharSlot(char[] chars, int encodeIndex, UnsignedLong decodeStartIndex) {
        char[] characters = null;
        char[] repeatCharacters = null;

        int start = encodeIndex;
        for (int i = encodeIndex; i < chars.length; i++) {
            char temp = chars[i];
            if (NumChar.contains(temp)) {
                characters = new char[i - start];
                System.arraycopy(chars, start, characters, 0, i - start);
                encodeIndex = i;
                break;
            }
        }

        start = encodeIndex;
        for (int i = encodeIndex; i < chars.length; i++) {
            char temp = chars[i];
            if (!NumChar.contains(temp) || i == chars.length - 1) {
                int length = i - start;
                if (i == chars.length - 1) {
                    length++;
                }

                repeatCharacters = new char[length];
                System.arraycopy(chars, start, repeatCharacters, 0, length);
                encodeIndex = i;
                break;
            }
        }

        CharSlot charSlot = new CharSlot();
        charSlot.nextCharSlotIndex = encodeIndex;
        charSlot.string = new String(characters, 0, characters.length);

        if (repeatCharacters == null) {
            charSlot.repeat = UnsignedLong.valueOf(1);
        } else {
            charSlot.repeat = UnsignedLong.valueOf(new String(repeatCharacters, 0, repeatCharacters.length));
        }
        charSlot.decodeStartIndex = decodeStartIndex;
        charSlot.decodeEndIndex = decodeStartIndex.plus(charSlot.repeat.times(UnsignedLong.valueOf(characters.length)));
        return charSlot;
    }

    public static class CharSlot {

        public int nextCharSlotIndex;

        public UnsignedLong decodeStartIndex;

        public UnsignedLong decodeEndIndex;

        public String string;

        public UnsignedLong repeat;
    }
}
