public class Task_2_1 {
    public static void main(String[] args){
        MyStack<Integer> st = new MyStack<>();
        for(int i = 0 ; i < 30 ; i++){
            st.push(i);
        }
        for(Integer i : st){
            System.out.println(i);
        }
    }

}
