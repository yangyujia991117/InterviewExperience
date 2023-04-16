/**
 * 禁忌魔法书中有一道威力强大的禁咒，名为大湮灭术。该禁咒所到之处寸草不生，万物归0。 世间充斥着正负两种能量，正能量对人体有益，而负能量对人体是有害的。已知地图上有n个排成一列的地域，每个地域的能量都不一样，可以用一个数字来代表某个地域中正负能量的总数，正数代表正能量比负能量多，反之亦然。
 *
 *        现在大湮灭术的卷轴只剩下了两个，你可以对任何一个连续的区域使用大湮灭术，使用之后，无论该连续区域中能量有多少，都会清0。你希望天地间的正能量最多，请问能使得正能量最多为多少。（如果天地间都是正能量，不使用卷轴也是可以的）
 *
 *
 *
 * 输入描述
 * 输入第一行仅包含一个正整数n(1<=n<=100000)，表示地域数量。
 *
 * 输入第二行包含n个整数，每个整数代表一个地域的能量总和,保证这个数值的绝对值不大于100000。
 *
 * 输出描述
 * 输出仅包含一个整数，即正能量最多为多少。
 *
 *
 * 样例输入
 * 10
 * -32 23 -93 -21 30 9 27 -88 93 61
 * 样例输出
 * 220
 *
 * 提示
 * 数据范围和说明
 *
 * 显然把{-32 23 -93 -21}和{-88}删掉之后，余下的和为220。
 */
import java.util.*;
public class Q2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int[] nums=new int[n];
        boolean hasNegative=false;
        List<int[]> boundary=new ArrayList<>();
        List<Integer> nege=new ArrayList<>();
        int start=0,end=0;
        boolean inNegative=false;
        int sum=0;
        int negaSum=0;
        for(int i=0;i<n;i++){
            nums[i]=scan.nextInt();
            sum+=nums[i];
            if(nums[i]<0){
                hasNegative=true;//有负能量
                if(!inNegative){
                    inNegative=true;
                    start=i;
                    negaSum=nums[i];
                    end=i;
                }
                else{
                    negaSum+=nums[i];
                    end=i;
                }
                if(i==n-1){
                    negaSum+=nums[i];
                    boundary.add(new int[]{start,end});
                    nege.add(negaSum);
                }
            }
            else{
                if(inNegative){//之前是在负区间内，现在出来了
                    inNegative=false;
                    boundary.add(new int[]{start,end});
                    nege.add(negaSum);
                }
            }
        }
        if(!hasNegative){
            System.out.println(sum);
            return;
        }
        if(boundary.size()<=2){
            //直接删掉那两个连续负区间即可
            for(int[] b:boundary){
                for(int i=b[0];i<=b[1];i++){
                    sum=sum-nums[i];
                }
            }
            System.out.println(sum);
            return;
        }
        //多个区间需要讨论，有一些区间可能删或不删，也可能多个连起来删
        List<int[]> positive_boundary=new ArrayList<>();
        List<Integer> posi=new ArrayList<>();
        for(int i=0;i<boundary.size()-1;i++){
            int l=boundary.get(i)[1]+1;
            int r=boundary.get(i+1)[0]-1;
            positive_boundary.add(new int[]{l,r});
            int p=0;
            for(int x=l;x<=r;x++){
                p+=nums[x];
            }
            posi.add(p);
        }
        for(int i=0;i<nege.size()-1;i++){
            if(i<nege.size()-2) {
                //不是最后一个负区间
                if (posi.get(i) + nege.get(i + 1) < 0) {
                    //posi.get(i)也删掉
                    sum -= nege.get(i) + posi.get(i)+posi.get(i+1);
                }
                else{
                    sum-=nege.get(i);
                }
            }
            else{
                sum-=nege.get(i);
            }
        }
        System.out.println(sum);


    }
}
