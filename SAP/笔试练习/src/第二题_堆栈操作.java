import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 第二题_堆栈操作 {
    /**
     * 0：入堆
     * 1：弹堆
     * 2：入栈
     * 3：弹栈
     *
     * 栈的地址变化情况：从低到高，
     * 堆的地址变化情况：从高到低
     * 两者都是后进先出
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();//内存大小
        int m=scan.nextInt();//行动次数
        int[] cache=new int[n];//内存，下标从0到n-1是从高地址到低地址
        int heapTop=0;//下一个进堆的位置
        int stackTop=n-1;//下一个进栈的位置
        boolean success=true;
        for(int i=0;i<m;i++){
            int op=scan.nextInt();
            if(op==0){
                int num=scan.nextInt();
                if(heapTop>stackTop){//已经使用完内存了
                    System.out.println("Memory allocation failure");
                    success=false;
                    break;
                }
                //内存还没用完
                cache[heapTop]=num;
                heapTop++;
            }
            else if(op==1){
                if(heapTop==0){//没有可供弹出来的数字
                    System.out.println("Invalid heap address");
                    success=false;
                    break;
                }
                System.out.println(cache[heapTop-1]);
                cache[heapTop-1]=0;//把这个堆顶的数字弹出来
                heapTop--;
            }
            else if(op==2){
                int num=scan.nextInt();
                if(heapTop>stackTop){//已经使用完内存了
                    System.out.println("Memory allocation failure");
                    success=false;
                    break;
                }
                cache[stackTop]=num;
                stackTop--;
            }
            else if(op==3){
                if(stackTop==n-1){//没有可供弹出来的数字
                    System.out.println("Invalid stack address");
                    success=false;
                    break;
                }
                System.out.println(cache[stackTop+1]);
                cache[stackTop+1]=0;//把这个堆顶的数字弹出来
                stackTop++;
            }
        }
        if(!success){
            System.out.println("Done");
        }
        for(int i=0;i<n;i++){
            System.out.print(cache[i]);
            if(i<n-1){
                System.out.print(" ");
            }
            else{
                System.out.print("\n");
            }
        }
    }
}
