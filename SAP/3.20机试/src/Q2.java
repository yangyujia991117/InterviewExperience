import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 分配给现在剩余工作量最少的任务
 * 如果剩余工作量相同则分配给编号最小的
 */
public class Q2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T=scan.nextInt();
        List<Integer> procNum=new ArrayList<>();//工作进程的数量
        List<Integer> workNum=new ArrayList<>();//任务总数
        List<List<Integer>> time=new ArrayList<>();//时间
        List<List<Integer>> size=new ArrayList<>();//工作量
        for(int i=0;i<T;i++){
            int n=scan.nextInt();
            procNum.add(n);
            int m=scan.nextInt();
            workNum.add(m);
            List<Integer> t=new ArrayList<>();
            for(int j=0;j<m;j++){
                t.add(scan.nextInt());
            }
            time.add(t);
            List<Integer> s=new ArrayList<>();
            for(int j=0;j<m;j++){
                s.add(scan.nextInt());
            }
            size.add(s);
        }
        System.out.println("n:");
        for(int n:procNum){
            System.out.print(n+" ");
        }
        System.out.print("\n");
        System.out.println("m:");
        for(int m:workNum){
            System.out.print(m+" ");
        }
        System.out.print("\n");
        System.out.println("到达时间：");
        for(List<Integer> t:time){
            for(int n:t){
                System.out.print(n+" ");
            }
            System.out.print("\n");
        }
        System.out.println("持续时间：");
        for(List<Integer> s:size){
            for(int n:s){
                System.out.print(n+" ");
            }
            System.out.print("\n");
        }
        for(int i=0;i<T;i++){
            int n=procNum.get(i),m=workNum.get(i);
            List<Integer> arriveTime=time.get(i);
            List<Integer> workSize=size.get(i);
            //0时刻：
            int[] now=new int[n];//当前时刻每个进程所有的工作量
            int[] resNUM=new int[n];
            int[] resSIZE=new int[n];
            int minProc=0;
            int wordIdx=0;
            for(int t=1;t<=arriveTime.get(m-1);t++){
                if(wordIdx>=m){
                    break;
                }
                //先把数组里的剩余时间更改，同时更改minProc
                for(int j=0;j<n;j++){
                    if(now[j]>0){
                        now[j]=now[j]-1;
                    }
                    if(now[j]<now[minProc]){
                        minProc=j;
                    }
                }
                System.out.print("t="+t+"时，minProc="+minProc+"  ");
                while(wordIdx<m&&t==arriveTime.get(wordIdx)){
                    //分配给minProc
                    now[minProc]=workSize.get(wordIdx);
                    resNUM[minProc]=resNUM[minProc]+1;
                    resSIZE[minProc]=resSIZE[minProc]+workSize.get(wordIdx);
                    System.out.print("第"+(wordIdx+1)+"个资源被分配给"+minProc+"   ");
                    wordIdx++;
                    minProc=getMinIdx(now);//得到新的minProc
                    System.out.println("分配后，minProc="+minProc);
                }
            }
            for(int x=0;x<n;x++){
                System.out.print(resNUM[x]);
                if(x<n-1){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
            for(int x=0;x<n;x++){
                System.out.print(resSIZE[x]);
                if(x<n-1){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
        }
    }
    static int getMinIdx(int[] nums){
        int minSize=Integer.MAX_VALUE;
        int minIdx=-1;
        for(int i=0;i<nums.length;i++){
            if(nums[i]<minSize){
                minSize=nums[i];
                minIdx=i;
            }
        }
        return minIdx;
    }
}
