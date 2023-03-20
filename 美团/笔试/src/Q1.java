import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q1 {
    /**
     * 输入N行敌人坐标数组(x,y)
     * 小美有一种全屏技能，能够一次性将若干敌人捕获
     * 要求捕获的所有敌人的横坐标最大差值≤A，纵坐标最大差值≤B
     * 计算小美一次性使用技能能捕获多少敌人
     */
    public static void main(String[] args){
        /**
         * 思路：二维前缀和
         */
        Scanner scan=new Scanner(System.in);
        int N=scan.nextInt();//N个敌人
        int A=scan.nextInt();//全屏技能参数A
        int B=scan.nextInt();//全屏技能参数B
        int[][] nums=new int[1000][1000];//表示每个坐标位置的敌人数量
        int maxX=1,maxY=1;
        for(int i=0;i<N;i++){
            int a=scan.nextInt();
            int b=scan.nextInt();
            nums[a-1][b-1]++;
            maxX=Math.max(maxX,a);
            maxY=Math.max(maxY,b);
        }
        int[][] sum=new int[maxX][maxY];//sum[i][j]表示二维数组前缀和（也就是这个坐标位置及其左上角一共有多少个敌人）
        //二维前缀数组的初始化：
        //先初始化左、上两条边缘
        sum[0][0]=nums[0][0];
        for(int j=1;j<sum[0].length;j++){
            sum[0][j]=sum[0][j-1]+nums[0][j];
        }
        for(int i=1;i<sum.length;i++){
            sum[i][0]=sum[i-1][0]+nums[i][0];
        }
        for(int i=1;i<sum.length;i++){
            for(int j=1;j<sum[0].length;j++){
                sum[i][j]=sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1]+nums[i][j];
            }
        }
        for(int i=0;i<maxX;i++){
            for(int j=0;j<maxY;j++){
                System.out.print(sum[i][j]+" ");
            }
            System.out.print("\n");
        }
        /**
         * 以row1,col1为左上角起点，实际上是求以[row1，col1]为左上角起点，[row+A,col+B]为右下角点的矩阵部分的敌人数量
         */
        int max=1,r;
        int row2,col2;
        for(int row1=0;row1<sum.length;row1++){
            for(int col1=0;col1<sum[0].length-B;col1++){
                row2=Math.min(row1+A,sum.length-1);
                col2=Math.min(col1+B,sum[0].length-1);
                r=getRes(row1,col1,row2,col2,sum);
                max=Math.max(r,max);
            }
        }
        System.out.println(max);
    }

    private static int getRes(int row1, int col1, int row2, int col2,int[][] sum) {
        if(row1==0&&col1==0){
            return sum[row2][col2];
        }
        if(row1==0){
            return sum[row2][col2]-sum[row2][col1-1];
        }
        if(col1==0){
            return sum[row2][col2]-sum[row1-1][col2];
        }
        return sum[row2][col2]-sum[row2][col1-1]-sum[row1-1][col2]+sum[row1-1][col1-1];
    }
}
