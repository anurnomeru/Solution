package normal;

/**
 * Created by Anur IjuoKaruKas on 4/22/2019
 *
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列。
 *
 * 示例 1:
 *
 * 输入: [1,3,5,4,7]
 * 输出: 3
 * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
 * 示例 2:
 *
 * 输入: [2,2,2,2,2]
 * 输出: 1
 * 解释: 最长连续递增序列是 [2], 长度为1。
 * 注意：数组长度不会超过10000。
 */
public class FindLengthOfLCIS {

    public static void main(String[] args) {
        System.out.println(getMaxLength(new int[] {
            2,
            2,
            2,
            2,
            2
        }));
    }

    public static int getMaxLength(int[] nums) {
        return getMaxLength(nums, 0, 0);
    }

    public static int getMaxLength(int[] nums, int startIndex, int maxLength) {
        if (nums.length == 0) {
            return 0;
        }

        LCISMetaData lcisMetaData = iter(nums, startIndex);

        int maxIndex = nums.length - 1;
        int remain = maxIndex - lcisMetaData.endIndex;
        maxLength = Math.max(maxLength, lcisMetaData.length);

        if (remain < maxLength) {
            return maxLength;
        } else {
            return getMaxLength(nums, lcisMetaData.endIndex, maxLength);
        }
    }

    public static LCISMetaData iter(int[] nums, int startIndex) {
        int length = 0;

        Integer current = null;

        LCISMetaData lcisMetaData = new LCISMetaData();
        lcisMetaData.startIndex = startIndex;

        for (int i = startIndex; i < nums.length; i++) {
            int temp = nums[i];

            if (current != null) {
                if (temp - current <= 0) {
                    break;
                }
            }
            length++;

            current = temp;
        }

        lcisMetaData.length = length;
        lcisMetaData.endIndex = startIndex + length;
        lcisMetaData.nums = new int[length];
        System.arraycopy(nums, lcisMetaData.startIndex, lcisMetaData.nums, 0, length);

        return lcisMetaData;
    }

    public static class LCISMetaData {

        public int[] nums;

        public Integer startIndex;

        public Integer endIndex;

        public Integer length;
    }
}
