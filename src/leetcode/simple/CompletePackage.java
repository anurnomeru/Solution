package leetcode.simple;

/**
 * Created by Anur IjuoKaruKas on 2020/2/20
 */
public class CompletePackage {

    public static void main(String[] args) {
        Package[] packages = genPackage(new int[] {
            2,
            3,
            3,
            4,
            4,
            5,
            5,
            6
        });

        int i = maxValue(10, packages);

        System.out.println();
    }

    public static int maxValue(int cap, Package[] packages) {
        if (cap <= 0) {
            return 0;
        }

        int max = 0;
        for (Package aPackage : packages) {
            max = Math.max(maxValue(cap - aPackage.weight, packages) + aPackage.value, max);
        }
        return max;
    }

    public static Package[] genPackage(int[] ints) {
        if (ints.length % 2 != 0) {
            throw new RuntimeException();
        }

        Package[] packages = new Package[ints.length / 2];
        int j = 0;
        for (int i = 0; i < ints.length; i = i + 2) {
            packages[j] = (new Package(ints[i], ints[i + 1]));
            j++;
        }

        return packages;
    }

    public static class Package {

        int value;
        int weight;

        public Package(int weight, int value) {
            this.value = value;
            this.weight = weight;
        }
    }
}
