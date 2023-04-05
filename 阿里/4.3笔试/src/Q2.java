import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q2 {
    static boolean[][] visited;
    static char[][] ma;
    static int n;
    static int m;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String[] sgs=scan.nextLine().split(" ");
        n=Integer.parseInt(sgs[0]);
        m=Integer.parseInt(sgs[1]);
        ma=new char[n][m];
        for(int i=0;i<n;i++){
            char[] chars=scan.nextLine().toCharArray();
            for(int j=0;j<m;j++){
                ma[i][j]=chars[j];
            }
        }
        visited=new boolean[n][m];
        int init_block=0;//初始红色连通块数目
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(ma[i][j]=='R'&&!visited[i][j]){
                    init_block++;
                    visit(i,j);
                }
            }
        }
        int[][] res=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(ma[i][j]=='W'){
                    res[i][j]=init_block;
                }
                else {
                    ma[i][j]='W';
                    List<int[]> neighbor_R=new ArrayList<>();//与这一格相邻的红色格的坐标
                    if(i>0&&ma[i-1][j]=='R'){
                        neighbor_R.add(new int[]{i-1,j});
                    }
                    //看下面是否能走
                    if(i<n-1&&ma[i+1][j]=='R'){
                        neighbor_R.add(new int[]{i+1,j});
                    }
                    //看左面是否能走
                    if(j>0&&ma[i][j-1]=='R'){
                        neighbor_R.add(new int[]{i,j-1});
                    }
                    //看右面是否能走
                    if(j<m-1&&ma[i][j+1]=='R'){
                        neighbor_R.add(new int[]{i,j+1});
                    }
                    if(neighbor_R.size()==0){
                        res[i][j]=init_block-1;//它是自己一个格子
                        continue;
                    }
                    int cut_num=0;//增多的连通块数目
                    for(int x=0;x<neighbor_R.size()-1;x++){
                        for(int y=x+1;y<neighbor_R.size();y++){
                            int i1=neighbor_R.get(x)[0];
                            int j1=neighbor_R.get(x)[1];
                            int i2=neighbor_R.get(y)[0];
                            int j2=neighbor_R.get(y)[1];
                            if(!canGo(i1,j1,i2,j2)){
                                cut_num++;
                            }
                        }
                    }
                    res[i][j]=init_block+cut_num;
                    ma[i][j]='R';
                }
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(res[i][j]);
                if(j<m-1){
                    System.out.print(" ");
                }
                else{
                    System.out.print("\n");
                }
            }
        }

    }
    static void visit(int i,int j){
        if(visited[i][j]){
            return;
        }
        visited[i][j]=true;
        //看上面是否能走
        if(i>0&&ma[i-1][j]=='R'){
            visit(i-1,j);
        }
        //看下面是否能走
        if(i<n-1&&ma[i+1][j]=='R'){
            visit(i+1,j);
        }
        //看左面是否能走
        if(j>0&&ma[i][j-1]=='R'){
            visit(i,j-1);
        }
        //看右面是否能走
        if(j<m-1&&ma[i][j+1]=='R'){
            visit(i,j+1);
        }
    }
    static boolean canGo(int i1,int j1,int i2,int j2){
        if(i1==i2&&j1==j2){
            return true;
        }
        //看上面是否能走
        if(i1>0&&ma[i1-1][j1]=='R'){
            if(canGo(i1-1,j1,i2,j2)){
                return true;
            }
        }
        //看下面是否能走
        if(i1<n-1&&ma[i1+1][j1]=='R'){
            if(canGo(i1+1,j1,i2,j2)){
                return true;
            }
        }
        //看左面是否能走
        if(j1>0&&ma[i1][j1-1]=='R'){
            if(canGo(i1,j1-1,i2,j2)){
                return true;
            }
        }
        //看右面是否能走
        if(j1<m-1&&ma[i1][j1+1]=='R'){
            if(canGo(i1,j1+1,i2,j2)){
                return true;
            }
        }
        return false;
    }
}
