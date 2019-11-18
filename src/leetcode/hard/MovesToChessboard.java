package leetcode.hard;


/**
 * Created by Anur IjuoKaruKas on 2019/11/12
 *
 * 一个 N x N的 board 仅由 0 和 1 组成 。每次移动，你能任意交换两列或是两行的位置。
 *
 * 输出将这个矩阵变为 “棋盘” 所需的最小移动次数。“棋盘” 是指任意一格的上下左右四个方向的值均与本身不同的矩阵。如果不存在可行的变换，输出 -1。
 *
 * 示例:
 * 输入: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
 * 输出: 2
 * 解释:
 * 一种可行的变换方式如下，从左到右：
 *
 * 0110     1010     1010
 * 0110 --> 1010 --> 0101
 * 1001     0101     1010
 * 1001     0101     0101
 *
 * 第一次移动交换了第一列和第二列。
 * 第二次移动交换了第二行和第三行。
 *
 *
 * 输入: board = [[0, 1], [1, 0]]
 * 输出: 0
 * 解释:
 * 注意左上角的格值为0时也是合法的棋盘，如：
 *
 * 01
 * 10
 *
 * 也是合法的棋盘.
 *
 * 输入: board = [[1, 0], [1, 0]]
 * 输出: -1
 * 解释:
 * 任意的变换都不能使这个输入变为合法的棋盘。
 *  
 *
 * 提示：
 *
 * board 是方阵，且行列数的范围是[2, 30]。
 * board[i][j] 将只包含 0或 1。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/transform-to-chessboard
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MovesToChessboard {
    private static int MASK = 1;
    private static int MASKER = 1431655765;
    public int movesToChessboard(int[][] board) {
        int[] ints = board[0];
        int size = ints.length;

        Integer rowVal = null;
        int columnVal = 0;
        int columnShift = 0;
        for (int i = 0; i < size; i++) {
            int[] row = board[i];
            int intVal = 0;
            int shift = 0;

            for (int i1 = row.length - 1; i1 >= 0; i1--) {
                if ((row[i1] & MASK) == MASK) {
                    intVal = intVal | (MASK << shift);

                    if (i1 == 0) {
                        columnVal = columnVal | (MASK << columnShift);
                    }
                }
                shift++;
            }
            columnShift++;

            if (rowVal == null) {
                rowVal = intVal;
            } else {

                /*
                 * 思路：
                 * 先判断是否能达到目标，其实很简单，要能达到目标，首先必须满足 任意列 i 与任意列 j 存在
                 * int[i] & int[j] == 0
                 * 或者
                 * int[i] ^ int[j] == 0
                 */
                if ((rowVal ^ intVal) != 0 && (rowVal & intVal) != 0) {
                    return -1;
                }
            }
        }

        /*
         * 满足上面的判断后，只需提取出第一列与第一行进行运算即可
         * 问题转化为了 给一串乱序随机0、1，能否排列为01间隔，且返回最少交换次数
         */
        int move1 = move(rowVal, size);
        if (move1 == -1) {
            return -1;
        }

        int move2 = move(columnVal, size);
        if (move2 == -1) {
            return -1;
        }

        return move1 + move2;
    }

    private int move(int intVal, int size) {
        /*
         * 任何顺序进来的 01 串，我们将其与预先设置好的串  MASKER（010101010101010101010101）进行匹配，结果为1代表不匹配，结果为0代表匹配
         */
        int result = MASKER ^ intVal;
        int matchCount = 0,unMatchCount = 0,matchOdd = 0,matchEven = 0,unMatchOdd = 0,unMatchEven = 0;

        for (int i = 0; i < size; i++) {
            int shiftMask = MASK << i;// 逐位匹配
            if ((result & shiftMask) != shiftMask) {
                matchCount++;
                if ((i & MASK) == MASK) { matchOdd++; } else { matchEven++; }// 记录不匹配个数中，奇偶数的比例
            } else {
                unMatchCount++;
                if ((i & MASK) == MASK) { unMatchOdd++; } else { unMatchEven++; }// 记录匹配个数中，奇偶数的比例
            }
        }

        boolean reserveUnMatch = matchCount < unMatchCount;// 因为进来的 01 串，可能与 MASKER 相反的更多，所以无论是匹配还是不匹配，我们只交换“少”的那个

        // 打个比方 1110010 和 MASKER 匹配后，得到结果  0100111 不匹配个数 4，大于匹配个数 3
        int odd = reserveUnMatch ? matchOdd : unMatchOdd;
        int even = reserveUnMatch ? matchEven : unMatchEven;

        if (odd == even) {// 如果要调转的奇数正好等于偶数，则直接将其两两交换
            return odd;
        } else {
            int balance = odd - even;
            if (Math.abs(balance) == 1 && (size & MASK) == MASK) {// 差1且棋盘是奇数是一种特殊情况，直接将所有数字反转即可
                return (size - 1) / 2 - Math.min(odd, even);
            }
            return -1;
        }
    }

    /**
     * [[1,1,0,0,1,0,0],[0,0,1,1,0,1,1],[1,1,0,0,1,0,0],[0,0,1,1,0,1,1],[1,1,0,0,1,0,0],[0,0,1,1,0,1,1],[0,0,1,1,0,1,1]]
     */
    public static void main(String[] args) {

        System.out.println(new MovesToChessboard().movesToChessboard(new int[][] {

            new int[] {
                0,
                1
            },
            new int[] {
                1,
                0
            },
            }));
    }
}
