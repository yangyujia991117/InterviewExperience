package wxy;

public class Q1 {
    /**
     * 找1000以内的质数
     */
    public static void main(String[] args){
        for(int n=1;n<=1000;n++){
            if(isPrime(n)){
                System.out.println(n);
            }
        }
    }
    public static boolean isPrime(int n){
        //判断一个数是否是质数
        if(n==1){
            return false;//1默认不是质数
        }
        for(int i=2;i<=Math.sqrt(n);i++){
            if(n%i==0){
                return false;
            }
        }
        return true;
    }

}
