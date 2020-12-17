import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class MyPartiallyOrderedSet<T> {
    private final Map<T, Element> objects = new HashMap<>();
    private static class Element{
        public ArrayList<Element> greaterThan = new ArrayList<>();
        public ArrayList<Element> lessThan = new ArrayList<>();
    }

    /**
     * добавляет элемент в множество по значению
     * @param elem добавляемый элемент
     */
    public void addElement(@NotNull T elem){
        if(objects.containsKey(elem)){
            throw new IllegalArgumentException("Такой элемент уже есть в множестве");
        }
        objects.put(elem, new Element());
    }

    /**
     * always add relation elemGreater > elemLess
     * @param elementGreater element that will be greater in relation
     * @param elementLess less one
     */
    public void addRelation(@NotNull T elementGreater, @NotNull T elementLess){
        if(!objects.containsKey(elementGreater) || !objects.containsKey(elementLess) || elementGreater == elementLess){
            throw new IllegalArgumentException();
        }
        addRelation(objects.get(elementGreater), objects.get(elementLess));
    }

    private void addRelation(@NotNull Element elementGreater, @NotNull Element elementLess){
        if (isGreater(elementLess, elementGreater)){
            throw new IllegalArgumentException();
        }
        elementGreater.greaterThan.add(elementLess);
        elementLess.lessThan.add(elementGreater);
    }

    /**
     * return true, if elem1 and elem2 are both in the set, and elem1 > elem2
     * @param elem1 элемент который кажется большим
     * @param elem2 элемент который кажется меньше
     */
    public boolean isGreater(@NotNull T elem1, @NotNull T elem2){
        if(!objects.containsKey(elem1) || !objects.containsKey(elem2)){
            throw new IllegalArgumentException();
        }
        return isGreater(objects.get(elem1), objects.get(elem2));
    }

    private boolean isGreater(@NotNull Element elem1, @NotNull Element elem2){
        if(elem1 == elem2){
            return false;
        }
        /* по сути, это просто обход графа, конкретно у меня - дфс */
        /* условие выхода из рекурсии - если в списке элементов, которые больше чем текущий, есть интересующий нас "большой" - true */
        if(elem2.lessThan.contains(elem1)){
            return true;
        }
        /* иначе проходим по списку элементов, которые больше чем текущий маленький */
        for(Element elem : elem2.lessThan){
            if(isGreater(elem1, elem)){
                return true;
            }
        }
        return false;
    }

    /**
     * возвращает все минимумы из данного множества
     * @return список минимумов
     */
    public ArrayList<T> minimum(){
        ArrayList<T> answer = new ArrayList<>();
        for(T elem : objects.keySet()){
            if(objects.get(elem).greaterThan.size() == 0){
                answer.add(elem);
            }
        }
        return answer;
    }

    /**
     * возвращает все максимумы из заданного множества
     * @return список максимумов
     */
    public ArrayList<T> maximum(){
        ArrayList<T> answer = new ArrayList<>();
        for(T elem : objects.keySet()){
            if(objects.get(elem).lessThan.size() == 0){
                answer.add(elem);
            }
        }
        return answer;
    }

    /**
     * разрушающая топологическая сортировка текущего множества - множество остается пустым (по алгоритму топосорта)
     * @return список элементов, (текущий элемент отсортированного массива больше или не сравним чем все предыдущие)
     */
    public ArrayList<T> topologicalSort(){
        ArrayList<T> answer = new ArrayList<>();
        while(!objects.isEmpty()){
            for(T elem : objects.keySet()){
                if(objects.get(elem).lessThan.size() == 0){
                    answer.add(elem);
                    deleteElement(elem);
                }
            }
            for(T toDelete : answer){
                objects.remove(toDelete);
            }
        }
        return answer;
    }

    private void deleteElement(@NotNull T elem) {
        if(!objects.containsKey(elem)){
            return;
        }
        Element toDelete = objects.get(elem);
        for(Element i : objects.values()){
            i.lessThan.remove(toDelete);
            i.greaterThan.remove(toDelete);
        }
    }

    /**
     * проверка на РУМ
     * @return true если текущее состояние множества - РУМ
     */
    public boolean isLattice() {
        for(Element a : objects.values()){
            for(Element b : objects.values()){

                /* нахождение верхнего+нижнего конуса для (a,b)
                *  это такие элементы множества, что:
                *  topCone: (x >= a && x >= b)
                *  bottomCone: (x <= a && x <= b) */

                Set<Element> topCone = objects.values().stream().filter(x -> ((isGreater(x, a) || x == a) && (isGreater(x, b) || x == b))).collect(Collectors.toSet());
                Set<Element> bottomCone = objects.values().stream().filter(x -> ((isGreater(a, x) || x == a) && (isGreater(b, x) || x == b))).collect(Collectors.toSet());

                /* пытаемся достать наименьший элемент из topCone: это такой элемент x, для которого x <= elem, для любого elem из topCone */
                Set<Element> sup = topCone.stream().filter(x -> {
                    for(Element elem : topCone){
                        if(!(isGreater(elem, x) || elem == x)){
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toSet());

                if(sup.size() != 1){
                    return false;
                }

                /* пытаемся достать наибольший элемент из bottomCone: это такой элемент x, для которого x >= elem, для любого elem из bottomCone */
                Set<Element> inf = bottomCone.stream().filter(x -> {
                    for(Element elem : bottomCone){
                        if(!(isGreater(x, elem) || elem == x)){
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toSet());

                if(inf.size() != 1){
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * проверка на ЛУМ
     * @return true если текущее состояние множества - ЛУМ
     */
    public boolean isLinearOrder(){
        for(Element elem1 : objects.values()){
            for(Element elem2 : objects.values()){
                /* линейно упорядоченное множество - это такое, где для любых a, b выполняется (a < b || a > b || a == b) */
                if(!(isGreater(elem1, elem2) || isGreater(elem2, elem1) || elem1 == elem2)){
                    return false;
                }
            }
        }
        return true;
    }

}
