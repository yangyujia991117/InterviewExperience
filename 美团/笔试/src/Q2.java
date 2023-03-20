import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Q2 {
    /**
     * 给一行N个数字，每个数字都代表一种颜色，要从中取一段连续的数字，要求这段数字的颜色数≤K
     */
    public static void main(String[] args) {
        /**
         * 思路：双指针滑动窗口
         */
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        int K = scan.nextInt();
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = scan.nextInt();
        }

        HashSet<Integer> hashSet = new HashSet<>();
        /**
         * 这里要记住hashSet的特性：里面不会有重复的元素，所以在add的时候不需要判断是否存在这个元素，直接add就好
         */
        int max = 1;
        int l = 0, r = 0;
        while (l < N && r < N) {
            hashSet.add(nums[r]);
            if (hashSet.size() > K) {
                //从下一个l从新开始
                hashSet.remove(nums[l]);//注意把最左边那个元素给删掉，窗口从后一个元素开始
                l++;
                r = l;
            } else {
                max = Math.max(max, r - l + 1);
                r++;
            }
        }
        System.out.println(max);
    }

}
