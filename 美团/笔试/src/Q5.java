import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Q5 {
    static int[] maxLen;
    static int[][] edges;
    static int[] res;
    //static int num;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int N=scan.nextInt();
        maxLen=new int[N];
        for(int i=0;i<N;i++){
            maxLen[i]=scan.nextInt();
        }
        int a,b;
        edges=new int[N][N];//如果那一位为1的话说明这两个点之间有一条边
        res=new int[N];
        for(int i=0;i<N-1;i++){
            a=scan.nextInt();
            b=scan.nextInt();
            edges[a-1][b-1]=1;
            edges[b-1][a-1]=1;
        }
        for(int i=0;i<N;i++) {
            boolean[] visited=new boolean[N];
            dfs(i,maxLen[i], visited);//看节点i能给谁供能
        }
        for(int n:res){
            System.out.print(n+" ");
        }
    }
    static void dfs(int i,int lastLen,boolean[] visited){
        visited[i]=true;
        res[i]++;//这个节点可以被供能
        //num++;
        if(lastLen==0){
            return;
        }
        for(int j=0;j<res.length;j++){
            if(edges[i][j]==1) {
                if (!visited[j]) {
                    dfs(j, lastLen - 1, visited);
                }
            }
        }
    }
}
