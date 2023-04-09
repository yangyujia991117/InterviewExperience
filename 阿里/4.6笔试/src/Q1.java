import java.util.LinkedList;

public class Q1 {
    /**
     * 约瑟夫环，编号1-100成一个环，每次报到3的出局，打印出每次出局的和最后一个剩下的
     */
    public static void main(String[] args) {
        LinkedList<Integer> list=new LinkedList<>();
        for(int i=1;i<=100;i++){
            list.add(i);
        }
        int index=0,out;
        while(list.size()>1){
            out=(index+2)%list.size();
            System.out.println(list.get(out));
            list.remove(out);
            index=out;
        }
        System.out.println(list.get(0));
    }
}
