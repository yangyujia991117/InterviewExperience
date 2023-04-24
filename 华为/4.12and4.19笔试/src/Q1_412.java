import java.util.Arrays;
import java.util.Scanner;

/**
 * 交易系统的降级策略
 *
 * 有一个交易系统有N个上游系统，每个上游系统调用量[R1,R2,...,Rn]，核心交易系统能接受的最大调用量为cnt，
 * 设定一个limit值，对于每个上游系统来说若其的调用量<=limit则可以全部调用，否则只能调用limit，要求所有上游系统的调用量之和不超过cnt
 * 输出最大的limit
 */
public class Q1_412 {
    /**
     * 二分法：之前求出数组中的最大值max，先令[l,r]为[0,max]，limit为该区间中位数，看这个中位数是否符合要求，符合的话在右区间继续找，不符合的话在左区间继续找
     */
    static int[] nums;
    static int cnt;
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        String[] s=scan.nextLine().split(" ");
        nums=new int[s.length];
        int max=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            nums[i]=Integer.parseInt(s[i]);
            max=Math.max(nums[i],max);
        }
        cnt=Integer.parseInt(scan.nextLine());
        int r=max,l=0;
        int limit=(l+r)/2;
        while(l<=r){//注意二分法的区间一定是小于等于！只要数字个数>0即可
            limit=(l+r)/2;
            if(can(limit)){
                l=limit+1;
            }
            else{
                r=limit-1;
            }
        }
        System.out.println(limit);

    }
    static boolean can(int limit){
        int res=0;
        for(int n:nums){
            res+=Math.min(n,limit);
        }
        return res<=cnt;
    }
}
