import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuadTree implements Iterable<Geometry.GeometryObject> {
    private final TreeNode root;
    private static class TreeNode {
        private final TreeNode parent;
        private TreeNode[] children;
        /* квадрат bound-квадрат, задающий промежуток данной ноды */
        private final Geometry.Square square;
        private final LinkedList<Geometry.GeometryObject> objects;

        /* constructor for root node */
        private TreeNode() {
            parent = null;
            children = null;
            objects = new LinkedList<>();
            this.square = new Geometry.Square(new Point2D(-Double.MAX_VALUE / 2.0, -Double.MAX_VALUE / 2.0), Double.MAX_VALUE);
        }
        /* default constructor */
        private TreeNode(TreeNode parent, int childIdx) {
            this.parent = parent;
            this.children = null;
            this.square = parent.createSubSquare(childIdx);
            this.objects = new LinkedList<>();
        }
        /* создает сабквадрат из квадрата текущей ноды на основе childIdx */
        private Geometry.Square createSubSquare(int childIdx) {
            Point2D corner = new Point2D(this.square.leftDown.x, this.square.leftDown.y);
            double len = (square.right.x-square.left.x)/2.0;
            if((childIdx & 1) == 1){
                corner.y += len;
            }
            if(((childIdx >>> 1) & 1) == 1) {
                corner.x += len;
            }
            return new Geometry.Square(corner, len);
        }
        private boolean isEmpty() {
            if(!objects.isEmpty()) {
                return false;
            }
            if(children != null) {
                for (TreeNode child : children) {
                    if (child != null && !child.isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        }
        private boolean isRoot(){
            return parent == null;
        }
    }
    /**
     * default constructor for tree, use 2 points for setting tree size
     * */
    public QuadTree() {
        root = new TreeNode();
    }
    /** общая схема индексации потомков
     * 1 | 3
     * --C--
     * 0 | 2
     **/
    private int getChildIdx(TreeNode node, Point2D point) {
        final byte RIGHT_UP_SQ = 3;
        final byte RIGHT_DOWN_SQ = 2;
        final byte LEFT_UP_SQ = 1;
        final byte LEFT_DOWN_SQ = 0;
        int idx;
        if(point.x >= node.square.center.x) {
            if(point.y >= node.square.center.y) {
                idx = RIGHT_UP_SQ;
            }
            else {
                idx = RIGHT_DOWN_SQ;
            }
        }
        else {
            if(point.y >= node.square.center.y) {
                idx = LEFT_UP_SQ;
            }
            else {
                idx = LEFT_DOWN_SQ;
            }
        }
        return idx;
    }
    public void addObject(Geometry.GeometryObject obj) {
        addObject(obj, root, obj.bound());
    }
    /* логика: если объект не удалось вставить в подходяший сабквадрат, то придется вставлять в текущий
    *  если же объект целиком помещается в сабквадрат, рекурсивно вставляем в оный */
    private void addObject(Geometry.GeometryObject obj, QuadTree.TreeNode currentNode, Geometry.Rectangle bound) {
        int childIdx = getChildIdx(currentNode, obj.center);
        Geometry.Square tmpChildSquare = currentNode.createSubSquare(childIdx);
        if(!tmpChildSquare.contains(bound)) {
            /* если не смогли вместить фигуру в подходящий сабквадрат -> вставляем в текущую ноду */
            if(currentNode.square.contains(bound)) {
                currentNode.objects.add(obj);
            }
            /* просто дебажный принтф */
            //System.out.println("Inserted " + obj.toString() + " into square: " + currentNode.square.leftDown.toString() + "^" + currentNode.square.rightUp.toString());
        }
        else {
            /* фигура вошла в нужный сабквадрат -> инициализируем, если нужно + рекурсивно делаем вставку в него */
            if(currentNode.children == null) {
                currentNode.children = new QuadTree.TreeNode[4];
            }
            if(currentNode.children[childIdx] == null) {
                currentNode.children[childIdx] = new TreeNode(currentNode, childIdx);
            }
            addObject(obj, currentNode.children[childIdx], bound);
        }
    }
    /* возвращает все объекты содержащие в себе point */
    public LinkedList<Geometry.GeometryObject> getObjects(Point2D point) {
        LinkedList<Geometry.GeometryObject> result = new LinkedList<>();
        getObjects(point, root, result);
        return result;
    }
    private void getObjects(Point2D point, TreeNode currentNode, LinkedList<Geometry.GeometryObject> result) {
        currentNode.objects.stream().
                filter(s -> s.contains(point)).
                forEach(result::add);
        if(currentNode.children != null) {
            int idx = getChildIdx(currentNode, point);
            if(currentNode.children[idx] != null) {
                getObjects(point, currentNode.children[idx], result);
            }
        }
    }
    /* возвращает объекты из заданного двумя точками прямоугольника
       по сути, надо вернуть все объекты, чей bound содержится в данном прямоугольнике (даже для круга это нормально работает, т.к. все наши прямоугольники расположены вдоль осей) */
    public LinkedList<Geometry.GeometryObject> getObjects(Point2D leftDown, Point2D rightUp) {
        LinkedList<Geometry.GeometryObject> result = new LinkedList<>();
        Geometry.Rectangle interest = new Geometry.Rectangle(leftDown, rightUp);
        getObjects(root, interest, result);
        return result;
    }
    private void getObjects(TreeNode currentNode, Geometry.Rectangle interest, LinkedList<Geometry.GeometryObject> result) {
        currentNode.objects.stream().filter(s -> interest.contains(s.bound())).forEach(result::add);
        if(currentNode.children != null) {
            for(int i = 0 ; i < 4 ; i++) {
                if(currentNode.children[i] != null) {
                    if(currentNode.children[i].square.intersect(interest)){
                        getObjects(currentNode.children[i], interest, result);
                    }
                }
            }
        }
    }
    /* deletes object from tree by ref */
    public void deleteObject(Geometry.GeometryObject obj) {
        deleteObject(obj, root);
    }
    /* логика: смотрим совпадения ссылок в текущей ноде, не находим -> переходим в подходящего потомка */
    private void deleteObject(Geometry.GeometryObject obj, TreeNode currentNode) {
        Optional<Geometry.GeometryObject> res = currentNode.objects.stream().filter(s -> s.equals(obj)).findFirst();
        if (res.isPresent()) {
            currentNode.objects.removeIf(s -> s.equals(obj));
            /* при удалении можем получить пустые ячейки, надо рекурсивно пройтись наверх и почистить ссылки */
            freeNode(currentNode);
        } else {
            int idx = getChildIdx(currentNode, obj.center);
            if(currentNode.children!=null && currentNode.children[idx] != null){
                deleteObject(obj, currentNode.children[idx]);
            }
            else {
                throw new IllegalStateException("Такого объекта кажется нет");
            }
        }
    }
    /* логика: смотрим, пустые ли мы и все наши дети -> если да, просматриваем детей родителя и затираем там себя */
    private void freeNode(TreeNode currentNode) {
        if(currentNode.isEmpty() && !currentNode.isRoot()) {
            for(int i = 0 ; i < 4 ; i++) {
                /* не очень понятно к чему тут ассерт, ide просто не смогла распарсить проверку на рутовость ноды двумя строчками выше */
                assert currentNode.parent != null;
                if(currentNode.parent.children[i] == currentNode) {
                    currentNode.parent.children[i] = null;
                }
            }
        freeNode(currentNode.parent);
        }
    }

    @Override
    public Iterator<Geometry.GeometryObject> iterator() {
        LinkedList<Geometry.GeometryObject> allObjects = getObjects(root.square.leftDown, root.square.rightUp);
        return allObjects.iterator();
    }
    /* реализация стрима через итератора, т.к. я делал собственный сплитератор в очереди с приоритетами */
    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator)
    {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false);
    }
    Stream<Geometry.GeometryObject> stream() {
        return getStreamFromIterator(iterator());
    }



}
