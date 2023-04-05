import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class 求数字连续子数组之和等于n {
    /**
     * 题目：一个从1-n的数组，求所有连续和为n的子数组
     * 如n=6时：1+2+3、6
     * n=15时：1+2+3+4+5、7+8、15
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        List<int[]> res=cal(n);
        for(int[] arr:res){
            for(int i:arr){
                System.out.print(i+" ");
            }
            System.out.print("\n");
        }
    }

    public static List<int[]> cal(int n){
        int[] nums=new int[n];
        for(int i=0;i<n;i++){
            nums[i]=i+1;
        }
        List<int[]> res=new ArrayList<>();
        int l=0,r=1;
        int sum=nums[0]+nums[1];

        while(l<r){//这里弄成两个的，因为只有一个元素的在最后加进去了
            System.out.println("l:"+l+",r:"+r+",sum:"+sum);
            if(sum==n){
                res.add(Arrays.copyOfRange(nums,l,r+1));
                sum-=nums[l];
                l++;
            }
            else if(sum>n){
                sum-=nums[l];
                l++;
            }
            else{
                r++;
                sum+=nums[r];
            }
        }
        res.add(new int[]{n});
        return res;
    }
}
