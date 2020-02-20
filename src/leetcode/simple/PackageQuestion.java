package leetcode.simple;

import java.util.HashMap;

/**
 * Created by Anur IjuoKaruKas on 2020/2/9
 */
public class PackageQuestion {

    static HashMap<Integer, HashMap<Integer, Integer>> valueMapping;

    static HashMap<Integer, Integer> NONE = new HashMap<>();

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

        valueMapping = new HashMap<>();
        System.out.println(maxValue(packages.length - 1, 8, packages));
    }

    public static int maxValue(int i, int capacity, Package[] packages) {
        if (i < 0 || capacity <= 0) {
            return 0;
        }

        Package thisPack = packages[i];

        if (thisPack.weight > capacity) {
            return maxValue(i-1,capacity,packages);
        }

        int notPut = maxValue(i - 1, capacity, packages);
        System.out.println(String.format("容 %s 不放 %s 价值%s", capacity, i + 1, notPut));

        int put = maxValue(i - 1, capacity - thisPack.weight, packages) + thisPack.value;
        System.out.println(String.format("容 %s 最大价值为 %s，放 %s 价值 %s", capacity - thisPack.weight, put - thisPack.value, i + 1, put));
        System.out.println();

        int max = Math.max(
            notPut,
            put
        );

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
