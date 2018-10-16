package simple;

/**
 * Created by Anur IjuoKaruKas on 2018/10/14
 */
public class RotateString {

    public static void main(String[] args) {
        RotateString rotateString = new RotateString();
        System.out.println(rotateString.solution("vcuszhlbtpmksjleuchmjffufrwpiddgyynfujnqblngzoogzg",
            "fufrwpiddgyynfujnqblngzoogzgvcuszhlbtpmksjleuchmjf"));
    }

    private int length;

    private char[] aChars;

    private char[] bChars;

    public boolean solution(String A, String B) {

        if (A == null || B == null) {
            return false;
        }

        if ("".equals(A) && "".equals(B)) {
            return true;
        }

        int aLength = A.length();
        int bLength = B.length();

        length = aLength;

        if (aLength != bLength) {
            return false;
        }

        aChars = A.toCharArray();
        bChars = B.toCharArray();
        return compare(0);
    }

    public boolean compare(int startIndex) {

        int aIndex = startIndex;

        for (int i = 0; i < length; i++) {
            if (aChars[aIndex] == bChars[i]) {
                aIndex = (aIndex + 1) % length;
            } else {
                startIndex++;
                if (startIndex == length) {
                    return false;
                }
                return compare(startIndex);
            }
        }

        return true;
    }
}
