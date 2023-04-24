import java.util.Arrays;
import java.util.Scanner;

/**
 * 获取最多食物
 *
 * 一个图结构，节点之间有一些边，每个节点有相应的食物量，从任意一个节点出发沿着边走（题目所给的条件保证不会来到相同的节点两次），可以随时停下，问能够拿到的最大食物量
 */
public class Q2_412 {
    static int[] dp;
    static int[][] nodes;
    static boolean[] visited;//是否遍历过了这个点
    /**
     * 树形dp，dp[i]表示以节点i结尾，可以获取到的最大食物的数量
     * 对于每个dp[i]，都可以走到父节点也可以不走到父节点，取最大的即可：dp[i]=max(节点i的食物量,dp[parent_id]）
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        nodes=new int[n][2];
        for(int i=0;i<n;i++){
            int id=scan.nextInt();
            nodes[id][0]=scan.nextInt();
            nodes[id][1]=scan.nextInt();
        }
        visited=new boolean[n];
        dp=new int[n];
        for(int i=0;i<n;i++){
            if(!visited[i]){
                dfs(i);
            }
        }
        System.out.println(Arrays.stream(dp).max().getAsInt());
    }
    static int dfs(int i){
        //求dp[i]
        if(visited[i]){
            //这个节点已经遍历过了
            return dp[i];
        }
        visited[i]=true;
        if(nodes[i][0]==-1){
            //该节点没有父节点
            dp[i]=nodes[i][1];
        }
        else{
            //该节点有父节点
            int parent_id=nodes[i][0];
            int parent_dp=dfs(parent_id);
            if(parent_dp<0){
                dp[i]=nodes[i][1];
            }
            else{
                dp[i]=nodes[i][1]+parent_dp;
            }
        }
        return dp[i];
    }
}
