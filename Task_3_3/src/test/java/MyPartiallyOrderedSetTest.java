import static org.junit.Assert.*;

public class MyPartiallyOrderedSetTest {

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void addElement() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        set.addElement("a");
        set.addElement("a");
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void addRelation() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d", "o"}){
            set.addElement(s);
        }
        set.addRelation("o", "a");
        set.addRelation("a", "c");
        set.addRelation("c", "o");
    }

    @org.junit.Test
    public void isGreater() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d", "o"}){
            set.addElement(s);
        }
        set.addRelation("o", "a");
        set.addRelation("o", "c");
        set.addRelation("b", "c");
        set.addRelation("c", "d");
        assertTrue(set.isGreater("o", "d"));
        assertFalse(set.isGreater("o", "b"));
    }

    @org.junit.Test
    public void minimum() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d"}){
            set.addElement(s);
        }
        set.addRelation("a", "b");
        set.addRelation("a", "c");
        assertArrayEquals(set.minimum().toArray(), new Object[]{"b", "c", "d"});
    }

    @org.junit.Test
    public void maximum() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"Мария", "Василий", "Татьяна", "Дмитрий", "Вероника"}){
            set.addElement(s);
        }
        set.addRelation("Мария", "Василий");
        set.addRelation("Вероника", "Василий");
        set.addRelation("Василий", "Татьяна");
        assertArrayEquals(set.maximum().toArray(), new Object[]{"Мария", "Дмитрий", "Вероника"});
    }

    @org.junit.Test
    public void topologicalSort() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d", "e", "f"}){
            set.addElement(s);
        }
        set.addRelation("a", "b");
        set.addRelation("a", "c");
        set.addRelation("b", "d");
        set.addRelation("b", "e");
        set.addRelation("c", "d");
        set.addRelation("c", "e");
        set.addRelation("d", "f");
        set.addRelation("e", "f");
        assertArrayEquals(set.topologicalSort().toArray(), new Object[]{"a", "b", "c", "d", "e", "f"});
    }

    @org.junit.Test
    public void isLattice() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d", "e", "f"}){
            set.addElement(s);
        }
        set.addRelation("a", "b");
        set.addRelation("a", "c");
        set.addRelation("b", "d");
        set.addRelation("b", "e");
        set.addRelation("c", "d");
        set.addRelation("c", "e");
        set.addRelation("d", "f");
        set.addRelation("e", "f");

        assertFalse(set.isLattice());

        set.addRelation("b", "c");
        assertTrue(set.isLattice());
    }

    @org.junit.Test
    public void isLinearOrder() {
        MyPartiallyOrderedSet<String> set = new MyPartiallyOrderedSet<>();
        for(String s : new String[]{"a", "b", "c", "d", "e", "f"}){
            set.addElement(s);
        }
        set.addRelation("a", "b");
        set.addRelation("a", "c");
        set.addRelation("b", "d");
        set.addRelation("b", "e");
        set.addRelation("c", "d");
        set.addRelation("c", "e");
        set.addRelation("d", "f");
        set.addRelation("e", "f");

        assertFalse(set.isLinearOrder());

        set.addRelation("b", "c");
        assertFalse(set.isLinearOrder());
        set.addRelation("d", "e");
        assertTrue(set.isLinearOrder());
    }
}