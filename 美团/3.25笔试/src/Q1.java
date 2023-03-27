import java.util.Scanner;
import java.util.Stack;

public class Q1 {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        int T=scan.nextInt();
        for(int t=0;t<T;t++){
            boolean res=true;
            int n=scan.nextInt();//火车数量
            int[] in_arr=new int[n];//用于初始化的数组
            for(int j=0;j<n;j++){
                in_arr[j]=scan.nextInt();
            }
            int[] out_arr=new int[n];
            for(int j=0;j<n;j++){
                out_arr[j]=scan.nextInt();
            }
            //下面判断这个是否符合要求
            Stack<Integer> stack=new Stack<>();//模拟战
            int idx=0;//下一个要进去的车所在的下标
            for(int num:out_arr){
                if(stack.empty()||(!stack.empty()&&stack.peek()!=num)){
                    if(idx>=n){
                        res=false;
                        break;
                    }
                    //当栈为空时，车要先进去
                    for(int i=idx;i<n;i++){
                        stack.push(in_arr[i]);
                        idx++;
                        if(in_arr[i]==num){
                            break;
                        }
                    }
                }
                if(stack.peek()!=num){
                    res=false;
                    break;
                }
                stack.pop();
            }
            if(res){
                System.out.println("Yes");
            }
            else{
                System.out.println("No");
            }
        }
    }
}
