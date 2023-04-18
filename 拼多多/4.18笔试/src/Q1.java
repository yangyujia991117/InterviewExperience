import java.util.Scanner;

/**
 * 输入整数X和Y，问有多少个对角线平行于坐标轴并且四个坐标都在[0,X],[0,Y]围成的范围之内的菱形
 */
public class Q1 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int X= scan.nextInt();
        int Y= scan.nextInt();
        if(X==0||Y==0){
            System.out.println(0);
            return;
        }
        int res=0;
        //设菱形的中心点坐标为(x,y),该中心点不可能在坐标轴上,平行于x轴的半对角线长为len1，平行于y轴的半对角线长为len2
        //则菱形四个顶点的坐标为(x-len1,y),(x+len1,y),(x,y-len2),(x,y+len2)
        for(int x=1;x<=X-1;x++){
            for(int y=1;y<=Y-1;y++){
                //保证中心在这个范围之内且不在范围的边缘
                for(int len1=1;x-len1>=0&&x+len1<=X;len1++){
                    for(int len2=1;y-len2>=0&&y+len2<=Y;len2++){
                        res++;
                    }
                }
            }
        }
        System.out.println(res);
    }
}
