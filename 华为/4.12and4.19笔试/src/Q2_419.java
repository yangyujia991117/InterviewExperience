import java.util.*;

/**
 * 树上逃离
 *
 * 给一棵树，一个猴子初始在0号根节点，一次可以沿着边到另一个节点，如果某个节点有障碍物那就不能走与该节点所连接的边，问该小猴子是否能走到叶节点，如果能的话输出最短的路径，如果有长度相同的路径，那就输出顺序较小的，不能的话输出NULL
 */

public class Q2_419 {
    public static class Node{
        int id;
        List<Node> sons;
        Node(int v){
            id=v;
            sons=new ArrayList<>();
        }
    }
    static List<Node> nodes=new ArrayList<>();
    static Set<Integer> blocks=new HashSet<>();
    static LinkedList<Integer> pre=new LinkedList<>();
    static List<List<Integer>> res=new ArrayList<>();
    static int len=Integer.MAX_VALUE;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int node_num=scan.nextInt();
        for(int i=0;i<node_num;i++){
            nodes.add(new Node(i));
        }
        int edge_num= scan.nextInt();
        for(int i=0;i<edge_num;i++){
            int id1= scan.nextInt();
            int id2= scan.nextInt();
            Node node1=nodes.get(id1);
            Node node2=nodes.get(id2);
            node1.sons.add(node2);
        }
        for(Node n:nodes){
            //对每个节点的子节点按照id进行排序，保证后面也是按顺序进行遍历
            n.sons.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.id - o2.id;
                }
            });
        }
        int block_num=scan.nextInt();
        for(int i=0;i<block_num;i++){
            blocks.add(scan.nextInt());
        }
        pre.add(0);
        dfs(nodes.get(0));
        if(len==Integer.MAX_VALUE){
            System.out.println("NULL");
        }
        else{
            List<Integer> list=res.get(res.size()-1);//最后一个一定是满足要求的结果
            for(int i=0;i<list.size()-1;i++){
                System.out.print(list.get(i)+"->");
            }
            System.out.println(list.get(list.size()-1));
        }

    }
    static void dfs(Node node){
        List<Node> sons=node.sons;
        if(sons.size()==0){
            //达到叶子节点了
            if(pre.size()<len) {
                res.add(new ArrayList<>(pre));
                len=pre.size();
            }
            return;
        }
        for(Node n:sons){
            if(!blocks.contains(n.id)) {
                pre.add(n.id);
                dfs(n);
                pre.removeLast();
            }
        }
    }
}
