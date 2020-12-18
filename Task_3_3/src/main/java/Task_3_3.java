public class Task_3_3 {
    public static void main(String[] args){
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        set.addElement("O");
        set.addElement("A");
        set.addElement("B");
        set.addElement("C");





        System.out.println(set.isGreater("O", "C"));
        System.out.println(set.isGreater("O", "B"));

        System.out.println(set.isLinearOrder());
    }
}
