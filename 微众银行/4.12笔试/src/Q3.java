

/**
 * 小涛现在手上有从 1 到 4095 编号的数字方块，每个数字方块所具有的特征我们将其定义为其编号在二进制下为 1 的位置所具有的特征。 例如 3 号数字方块我们就认为其具有 1 号特征和 2号特征，而 4 号数字方块我们认为其只具有 3 号特征。
 *
 *
 *
 * 但每天小涛都会收到一堆新方块。由于新方块全部堆在门口，小涛想直接分类显然是很麻烦的一件事，因此小涛想用魔法磁铁来对这些方块进行初分类。
 *
 *
 *
 * 魔法磁铁的具体用法如下：先使用手上的数字方块进行附魔，然后磁铁就会自动吸附上含有全部这些对应特征的方块，例如如果我们拿 5 号数字方块对魔法磁铁进行附魔，那么磁铁就可以吸附上编号为5(0101)2,7(0111)2,15(1111)2的方块，但编号1(0001)2,10(1010)2,14(1110)2的方块则无法被吸附（这些方块没有同时包含特征1，3类）。
 *
 *
 *
 * 但使用魔法磁铁是一个很累的事，因此小涛只想用魔法磁铁先把这些堆在门口的方块收回家里，因此小涛想知道，最少使用几个数字方块对魔法磁铁附魔才能使得所有堆在门口的方块都能搬到房内。
 *
 *
 *
 * (注意：没有0号方块和0号数字方块，附魔是用你手中有的编号从1到4095的数字方块而不是你当天收到的方块，即用来附魔的数字方块不需要是当天有的)
 *
 *
 *
 * 输入描述
 * 第一行一个T(T≤10)，表示有T天，每天输入格式如下：
 *
 * 第一行一个n(n≤105)表示有个n方块，接下来一行n个整数ai(ai∈[1,4095])，表示每个方块的编号。
 *
 * 所有天数的方块加起来总数不会超过106。
 *
 * 对于所有的数据，1≤n≤105
 *
 * 输出描述
 * 对于每一天输出格式如下：
 *
 *
 *
 * 输出第一行一个数字m，表示最少需要m种数字方块，接下来一行m个数字，从小到大依次给出数字方块编号，用空格隔开，如果有多种合法最小方案，输出字典序最小的方案。
 *
 *
 *
 * A字典序比B小是指，A序列第一个与B序列数字不同的地方，A的数字小于B，例如2,3,4,9小于2,3,8,9，1,2,3,4小于4,5,6,7。
 *
 *
 * 样例输入
 * 2
 * 3
 * 1 2 3
 * 1
 * 1024
 * 样例输出
 * 2
 * 1 2
 * 1
 * 1024
 *
 * 提示
 * 对于样例第一组数据，用1号数字方块附魔后可以吸附上1,3号方块，用2号数字方块附魔后可以吸附上2,3号方块，此时刚好全部搬完。
 *
 *
 *
 * 对于样例第二组数据，只有一种1024号方块，因此用1024号数字方块附魔即可全部搬完。
 */

import java.util.*;

public class Q3 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T=scan.nextInt();
        for(int t=0;t<T;t++){
            int n=scan.nextInt();
            List<Integer> nums=new ArrayList<>();
            List<String> nums_sg=new ArrayList<>();
            for(int i=0;i<n;i++){
                int num=scan.nextInt();
                nums.add(num);
                String sg=Integer.toBinaryString(num);
                while(sg.length()<32){
                    sg="0"+sg;
                }
                nums_sg.add(sg);
            }
            if(n==1){
                System.out.println(1);
                System.out.println(nums.get(0));
                continue;
            }
            Collections.sort(nums);
            int res=0;
            List<Integer> res_num=new ArrayList<>();
            while(nums.size()>0){
                int num=nums.get(0);
                res++;
                res_num.add(num);
                int i=1;
                String s1=nums_sg.get(0);
                while(i<nums.size()){
                    String s2=nums_sg.get(i);
                    for(int j=31;j>=0;j--){
                        if(s1.charAt(j)=='1'&&s2.charAt(j)=='1'){
                            nums.remove(i);
                            nums_sg.remove(i);
                            i--;
                            break;
                        }
                    }
                    i++;
                }
                nums.remove(0);
                nums_sg.remove(0);
            }
            System.out.println(res);
            for(int i=0;i<res_num.size();i++){
                System.out.print(res_num.get(i));
                if(i<res_num.size()-1){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
        }

    }
}
