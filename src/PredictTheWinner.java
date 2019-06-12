import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Anur IjuoKaruKas on 2018/10/13
 */
public class PredictTheWinner {

    public static void main(String[] args) {
        PredictTheWinner predictTheWinner = new PredictTheWinner();
        System.out.println(predictTheWinner.solution(new int[] {
            1,
            5,
            233,
            7,
            5
        }));

        new ReentrantReadWriteLock().readLock().lock();
    }

    int[] nums;

    /**
     * 给定一个表示分数的非负整数数组。 玩家1从数组任意一端拿取一个分数，随后玩家2继续从剩余数组任意一端拿取分数，然后玩家1拿，……。
     * 每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数可取时游戏结束。最终获得分数总和最多的玩家获胜。
     *
     * 给定一个表示分数的数组，预测玩家1是否会成为赢家。你可以假设每个玩家的玩法都会使他的分数最大化。
     */
    public boolean solution(int[] nums) {
        this.nums = nums;
        int sumA = 0;
        int sumB = 0;

        int count = 0;

        Integer result;
        result = pollBiggerOne();
        while (result != null) {
            if (count % 2 == 0) {
                sumA += result;
            } else {
                sumB = result;
            }

            count++;
            result = pollBiggerOne();
        }

        return sumA >= sumB;
    }

    public Integer pollBiggerOne() {
        int result;

        if (nums == null) {
            return null;
        }
        int size = nums.length;

        if (size == 0) {
            return null;
        }

        if (size == 1) {
            result = nums[0];
            nums = new int[] {};
            return result;
        }

        int head = 0;
        int tail = size - 1;

        int headNode = nums[head];
        int tailNode = nums[tail];

        int[] newNums = new int[size - 1];
        if (headNode > tailNode) {
            System.arraycopy(nums, 1, newNums, 0, size - 1);
            nums = newNums;
            return headNode;
        } else {
            System.arraycopy(nums, 0, newNums, 0, size - 1);
            nums = newNums;
            return tailNode;
        }
    }
}
