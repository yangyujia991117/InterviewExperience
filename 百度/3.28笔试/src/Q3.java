import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Author:yy
 * Description:
 **/
public class Q3 {
    static int res=0;
    static class Node{
        Node father;
        List<Node> sons;
        int id;
        char color;
        Node(char c,int i){
            color=c;
            sons=new ArrayList<>();
            id=i;
        }
        int blockNum;//以该节点为根的树的色块数
    }
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=Integer.parseInt(scan.nextLine());
        char[] color=scan.nextLine().toCharArray();
        HashMap<Integer,Node> hashMap=new HashMap<>();
        for(int i=0;i<n;i++){
            hashMap.put(i+1,new Node(color[i],i+1));
        }
        List<Node[]> bias=new ArrayList<>();
        for(int i=0;i<n-1;i++){
            int n1=scan.nextInt(),n2= scan.nextInt();
            if(n1>n2){//让小的当父亲
                int tmp=n1;
                n1=n2;
                n2=tmp;
            }
            Node father=hashMap.get(n1),son=hashMap.get(n2);
            father.sons.add(son);
            son.father=father;
            bias.add(new Node[]{father,son});
        }
        if(n==1){
            System.out.println(1);
            return;
        }
        Node root=hashMap.get(1);
        //计算以每个节点为根的子树的色块数目
        dfs(root);
        //计算每条边的权值和
        for(Node[] nodes:bias){
            Node father=nodes[0],son=nodes[1];
            int part1;//除了以son为根的子树之外其余部分的色块数;
            int part2=son.blockNum;//以son为根的子树的色块数
            if(father.color==son.color){
                //这里需要注意的是一定要用root的blockNum来减，因为剩余部分是以root为根的
                part1=root.blockNum-son.blockNum+1;
            }
            else{
                part1=root.blockNum-son.blockNum;
            }
            res+=Math.abs(part1-part2);
        }
        System.out.println(res);
    }
    static int dfs(Node node){
        int res=1;
        char color=node.color;
        for(Node son:node.sons){
            int sonRes=dfs(son);
            if(son.color==color){
                res=res-1+sonRes;
            }
            else{
                res=res+sonRes;
            }
        }
        node.blockNum=res;
        System.out.println(node.id+"的色块数为"+res);
        return res;
    }

}
