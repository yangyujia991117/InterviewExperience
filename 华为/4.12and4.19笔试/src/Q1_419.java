import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 服务器能耗统计
 *
 * 服务器有三种运行状态：空载、单任务、多任务，每个时间片的能耗为1、3、4
 * 输入一系列任务的起止时间，求服务器的总能耗
 */
public class Q1_419 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int[][] nums=new int[n][2];
        for(int i=0;i<n;i++){
            nums[i][0]= scan.nextInt();
            nums[i][1]= scan.nextInt();
        }
        Arrays.sort(nums, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0]){
                    return o1[1]-o2[1];
                }
                return o1[0]-o2[0];
            }
        });
        //遍历排序后的nums的每一个元素时:
        //1.维护一个现已知只有一个任务在运行的区间[l,r]
        //2.当下一个任务的start<=r时，说明之前所认为的只有一个任务在运行的区域[l,r]中的一部分实际上是有多个任务在运行的，
        // 2.1当start<=l的时候，若end>=r，则[l,r]这一整段都是有多个程序在运行，把4*(r+1-l)加进结果里，并令l=r+1,r=end；若end<r，则[l,end]这一段有多个程序，把4*(end+1-l)加进结果里，并令l=end+1
        // 2.2当start>l的时候，确定[l,start-1]这段区域是只有一个元素在运行的，把3*(start-l)加进结果里，若end>=r，则[start,r]这一段有多个程序在运行，把4*(r+1-start)加进结果里，l=r+1,r=end;
        //    若end<r，则[start,end]这一段有多个程序在运行，把4*(end+1-start)加进结果里，l=end+1
        //3.当下一个任务的start>r时，说明前面那段[l,r]才真的是只有一个任务在执行，把3*(r+1-l)加进结果里，[r+1,start-1]这段是空载，把(start-r-1)加进结果里，并令l=start,r=end
        //
        int res=0;
        int l=nums[0][0],r=nums[0][1];
        int start,end;
        for(int i=1;i<n;i++){
            start=nums[i][0];
            end=nums[i][1];
            if(l>r){
                //这种情况下说明前一个end和r是相等的，这时候需要取新的l和r了
                if(end<=r){
                    l=end+1;
                }
                else if(start<=r){
                    l=r+1;
                    r=end;
                }
                else{
                    l=start;
                    r=end;
                }
                continue;
            }
            if(start<=r) {
                if (start <= l) {
                    //这里可以保证start在l左边的那一部分肯定已经按照多任务的情况算过了，因为按照正常情况下start一定会>=l，如果有小于的话只能说明前面就已经出现了重叠
                    if(end>=r){
                        //[l,r]这一整段确定有多个任务在执行，[r+1,end]不知道
                        res+=4*(r+1-l);
                        l=r+1;
                        r=end;
                    }
                    else{
                        //[l,end]这一段确定有多个任务在执行，[end+1,r]不知道
                        res+=4*(end+1-l);
                        l=end+1;
                    }
                }
                else{
                    //[l,start-1]这一段确定只有一个任务在执行
                    res+=3*(start-l);
                    if(end>=r){
                        //[start,r]这一整段确定有多个任务在执行，[r+1,end]不知道
                        res+=4*(r+1-start);
                        l=r+1;
                        r=end;
                    }
                    else{
                        //[start,end]这一段确定有多个任务在执行，[end+1,r]不知道
                        res+=4*(end+1-start);
                        l=end+1;
                    }

                }
            }
            else{
                res+=3*(r+1-l)+(start-r-1);
                l=start;
                r=end;
            }
        }
        if(r>=l){
            //看最后如果还有没算进去的区间，那加进去
            res+=3*(r+1-l);
        }
        System.out.println(res);
    }
}
