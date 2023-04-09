import java.util.*;

public class Q2 {
    /**
     * 成绩单有语文、数学、英语3科成绩，及总成绩，但一些地方模糊不清，要求帮助小红还原
     * 第一行输入正整数n表示学生人数
     * 接下来n行每行输入若干字符串和整数，从左到右为学生姓名、语文成绩、数学成绩、英语成绩、总成绩
     * 如果某地方看不清会用'?'表示
     * 输出要求按每个人的总成绩进行降序排序，总成绩相同的按名字字典序升序进行排序，若名字无法确认则排在总成绩的最下面，有多个无法确认时按输入的顺序排
     * 总成绩无法确认则排在最下面，按名字字典序升序进行排序，若名字无法确认则排在总成绩的最下面，有多个无法确认时按输入的顺序排
     */

    /**
     * 例：
     * 输入如下：
     * 8
     * ranko 100 100 100 ?
     * kotori 90 ? 80 ?
     * ? ? 22 ? ?
     * ayya ? 80 ? 240
     * ruby 59 100 ? 200
     * ? 80 90 ? ?
     * ? 80 90 70 240
     * aya 80 80 80 ?
     *
     * 输出：
     * ranko 100 100 100 300
     * aya 80 80 80 240
     * ayya ? 80 ? 240
     * ? 80 90 70 240
     * ruby 59 100 41 200
     * kotori 90 ? 80 ?
     * ? ? 22 ? ?
     * ? 80 90 ? ?
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n= Integer.parseInt(scan.nextLine());
        String[][] input=new String[n][6];
        for (int i=0;i<n;i++){
            String[] ss=scan.nextLine().split(" ");
            for(int j=0;j<5;j++){
                input[i][j]=ss[j];
            }
            input[i][5]=String.valueOf(i);//最后这位是用来记录输入顺序的
        }

        //首先处理能算出来的成绩
        int canSum=0;//能算出总成绩的
        int notSum=0;//算不出总成绩的
        for(String[] ss:input){
            if(ss[4].equals("?")&&!ss[1].equals("?")&&!ss[2].equals("?")&&!ss[3].equals("?")){
                //能算出总成绩
                ss[4]=String.valueOf(Integer.parseInt(ss[1])+Integer.parseInt(ss[2])+Integer.parseInt(ss[3]));
            }
            else {
                if(ss[1].equals("?")&&!ss[2].equals("?")&&!ss[3].equals("?")&&!ss[4].equals("?")){
                    //能算出语文成绩
                    ss[1]=String.valueOf(Integer.parseInt(ss[4])-Integer.parseInt(ss[2])-Integer.parseInt(ss[3]));
                }
                else if(ss[2].equals("?")&&!ss[1].equals("?")&&!ss[3].equals("?")&&!ss[4].equals("?")){
                    //能算出数学成绩
                    ss[2]=String.valueOf(Integer.parseInt(ss[4])-Integer.parseInt(ss[1])-Integer.parseInt(ss[3]));
                }
                else if(ss[3].equals("?")&&!ss[1].equals("?")&&!ss[2].equals("?")&&!ss[4].equals("?")){
                    //能算出英语成绩
                    ss[3]=String.valueOf(Integer.parseInt(ss[4])-Integer.parseInt(ss[1])-Integer.parseInt(ss[2]));
                }
            }

            if(!ss[4].equals("?")){
                canSum++;
            }
            else{
                notSum++;
            }
        }
        //处理一下
        String[][] sum=new String[canSum][6];
        String[][] not=new String[notSum][6];
        int index1=0,index2=0;
        for(int i=0;i<n;i++){
            if(!input[i][4].equals("?")){
                sum[index1++]=input[i];
            }
            else{
                not[index2++]=input[i];
            }
        }
        //将有成绩的进行排序
        Arrays.sort(sum, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if(!o1[4].equals(o2[4])){
                    return Integer.parseInt(o2[4])-Integer.parseInt(o1[4]);//降序
                }
                else{
                    //两个成绩相同，则按名字排
                    if(!o1[0].equals("?")&&!o2[0].equals("?")){
                        return o1[0].compareTo(o2[0]);//升序排
                    }
                    if(o1[0].equals("?")&&o2[0].equals("?")){
                        return Integer.parseInt(o1[5])-Integer.parseInt(o2[5]);//按输入顺序排
                    }
                    if(!o1[0].equals("?")){
                        return -1;//o1排前面
                    }
                    return 1;//o2排前面
                }
            }
        });
        //将无成绩的进行排序
        Arrays.sort(not, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if(!o1[0].equals("?")&&!o2[0].equals("?")){
                    return o1[0].compareTo(o2[0]);//都有名字，则按升序排
                }
                if(o1[0].equals("?")&&o2[0].equals("?")){
                    return Integer.parseInt(o1[5])-Integer.parseInt(o2[5]);//按输入顺序排
                }
                if(!o1[0].equals("?")){
                    return -1;
                }
                return 1;
            }
        });
        //输出结果
        for(String[] s:sum){
            for(int i=0;i<5;i++){
                System.out.print(s[i]);
                if(i<4){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
        }
        for(String[] s:not){
            for(int i=0;i<5;i++){
                System.out.print(s[i]);
                if(i<4){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
        }
    }
}
