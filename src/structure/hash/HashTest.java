package structure.hash;

public class HashTest {

    public static void main(String[] args) {
        String key = "9999999999";
        int h;
        int i = (h = key.hashCode()) ^ h >>> 16;

        printLong(h);
        printLong(h >>> 16);
        printLong(i);
    }

    public static void printLong(long l) {
        System.out.println(byte2Str(long2byte(l)));
    }

    public static byte[] long2byte(long l) {
        byte[] bytes = new byte[8];
        for (int i = bytes.length - 1; i >= 0; i--) {
            bytes[i] = (byte) l;
            l = l >> 8;
        }

        return bytes;
    }

    public static String byte2Str(byte[] bs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            byte b = bs[i];
            stringBuilder.append((b & 1 << 7) == 1 << 7 ? "1" : "0");
            stringBuilder.append((b & 1 << 6) == 1 << 6 ? "1" : "0");
            stringBuilder.append((b & 1 << 5) == 1 << 5 ? "1" : "0");
            stringBuilder.append((b & 1 << 4) == 1 << 4 ? "1" : "0");
            stringBuilder.append((b & 1 << 3) == 1 << 3 ? "1" : "0");
            stringBuilder.append((b & 1 << 2) == 1 << 2 ? "1" : "0");
            stringBuilder.append((b & 1 << 1) == 1 << 1 ? "1" : "0");
            stringBuilder.append((b & 1 << 0) == 1 << 0 ? "1" : "0");
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
