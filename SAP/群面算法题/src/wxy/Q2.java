package wxy;

public class Q2 {
    /**
     * 输入一个数x和一个数组，判断一个数组里面有没有两个数之和等于x
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(judge(new int[]{1,2,3,4,5,6,7},8));
        System.out.println(judge(new int[]{1,2,3,4,5,6,7,8},18));
    }
    public static boolean judge(int[] nums,int x){
        for (int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==x){
                    return true;
                }
            }
        }
        return false;
    }
}
