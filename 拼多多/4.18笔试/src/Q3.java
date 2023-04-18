import java.util.*;

/**
 * 输入一个表，问能不能合并其中的几行（即把其中一些属性设为ALL），使得合并之后表的行数最少
 */
public class Q3 {
    static List<List<String>> all=new ArrayList<>();
    static List<String> pre;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int M=Integer.parseInt(scan.nextLine());//维度个数
        List<List<String>> quzhi=new ArrayList<>();
        for(int i=0;i<M;i++){
            String[] ss=scan.nextLine().split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(ss).subList(1, ss.length));
            quzhi.add(list);
        }
        //下面得到所有属性的所有排列组合

        int T=Integer.parseInt(scan.nextLine());//参数的配置行数
        HashMap<String,Integer> table=new HashMap<>();
        for(int i=0;i<T;i++){
            String[] ss=scan.nextLine().split(" ");
            StringBuilder sb=new StringBuilder();
            for(int j=0;j<ss.length-1;j++){
                sb.append(ss[j]);
            }
            table.put(sb.toString(),Integer.parseInt(ss[ss.length-1]));
        }
        //下面求所有元素的排列组合
        getAll(0,M,quzhi);

    }
    //得到了所有属性的排列组合
    static void getAll(int start,int M,List<List<String>> quzhi){
        if(start==M){
            all.add(new ArrayList<>(pre));
            return;
        }
        for(String qu:quzhi.get(start)){
            pre.add(qu);
            getAll(start+1,M,quzhi);
            pre.remove(pre.size()-1);
        }
    }
    static void simply(HashMap<String,Integer> table,int M,List<List<String>> quzhi){
        //可以简化为All的情况：当这个属性固定时，其他属性的所有排列组合都是一样的取值
        for(int i=0;i<M;i++){
            List<String> list=quzhi.get(i);

        }
    }
}
