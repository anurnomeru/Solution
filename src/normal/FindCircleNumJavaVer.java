package normal;

/**
 * Created by Anur IjuoKaruKas on 2019/11/1
 */
public class FindCircleNumJavaVer {

    public int findCircleNum(int[][] M) {
        int color = 2;
        int[] hasMarked = new int[M.length];
        for (int i = 0; i < M.length; i++) {
            if (colorRecursive(M, i, hasMarked, color)) {
                color++;
            }
        }
        return color - 2;
    }

    /**
     * 心得：审题很重要！！不要想当然！
     *
     * index 来表示当前为第几个学生
     * hasMarked 来进行排重，避免重复扫描
     * color 用于染色
     */
    private boolean colorRecursive(int[][] M, int index, int[] hasMarked, int color) {
        if (hasMarked[index] != 0) {
            return false;
        }

        hasMarked[index] = 1;
        int[] friendShip = M[index];
        for (int i = 0; i < friendShip.length; i++) {
            if (hasMarked[i] == 0 && friendShip[i] == 1) {// 需要同时满足未被标记过，下标为1
                colorRecursive(M, i, hasMarked, color);
            }
        }
        return true;
    }
}
