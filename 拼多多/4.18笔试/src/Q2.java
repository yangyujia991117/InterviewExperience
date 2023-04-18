import java.util.Scanner;

/**
 * 输入多个点的坐标，且这些点的横坐标都不一样
 * 现在能交换其中的两个点，可以交换也可以不交换，求交换或不交换之后这些点和横坐标围成的投影面积的最大值
 */
public class Q2 {
    public static void main(String[] args) {
        //尽量将纵坐标比较接近的几个点放在一起，尽量减少峰谷
        Scanner scan=new Scanner(System.in);
        int n= scan.nextInt();
        int[][] nums=new int[n][2];
        for(int i=0;i<n;i++){
            nums[i][0]= scan.nextInt();
            nums[i][1]= scan.nextInt();
        }

    }
}
