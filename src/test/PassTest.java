package test;

/**
 * Created by Anur IjuoKaruKas on 2019/8/23
 */
public class PassTest {

    public static void main(String[] args) {
        Obj obj = new Obj(0, "Anur");
        fun1(obj);
        fun2(obj);
        System.out.println(obj);
    }

    static void fun1(Obj obj) {
        obj.str = "Changed";
        obj.i = 999;
    }

    static void fun2(Obj obj) {
        obj = new Obj(-1, "Runa");
    }

    static class Obj {
        int i;
        String str;
        Obj(int i, String str) { this.i = i;this.str = str; }

        @Override
        public String toString() {
            return "Obj{i=" + i + ", str='" + str + '\'' + '}';
        }
    }
}
