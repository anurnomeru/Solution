package structure.hash;

public class HashTest {

    public static void main(String[] args) {
        String key = "";

        int h;
        int i = (h = key.hashCode()) ^ h >>> 16;

        System.out.println(i);
    }
}
