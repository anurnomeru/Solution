import org.junit.Test;

/**
 * Created by Anur IjuoKaruKas on 2019/6/27
 */
public class TestFuck {

    public static void main(String[] args) {
        TestFuck testFuck = new TestFuck();
        testFuck.test();
    }

    @Test
    public void test() {
        if (1 == 1) {
            int i = 1 / 0;
        }
    }
}
