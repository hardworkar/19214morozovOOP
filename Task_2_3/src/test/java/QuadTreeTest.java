import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class QuadTreeTest {
    /* testing mostly add and get-by-point methods */
    @Test
    public void addObject() {
        QuadTree myTree = new QuadTree();
        Geometry.Sphere[] spheres = new Geometry.Sphere[3];
        spheres[0] = new Geometry.Sphere(new Point2D(10,10), 5);
        spheres[1] = new Geometry.Sphere(new Point2D(15,15), 10);
        spheres[2] = new Geometry.Sphere(new Point2D(0,0), 1);
        for(int i = 0 ; i < 3 ; i++) {
            myTree.addObject(spheres[i]);
        }
        LinkedList<Geometry.GeometryObject> res = myTree.getObjects(new Point2D(21,21));
        assertEquals(res.get(0).toString(), spheres[1].toString());
    }
    /* testing mostly get-by-rectangle method */
    @Test
    public void getObjects() {
        QuadTree tree = new QuadTree();
        Geometry.Sphere[] spheres = new Geometry.Sphere[3];
        spheres[0] = new Geometry.Sphere(new Point2D(10,10), 5);
        spheres[1] = new Geometry.Sphere(new Point2D(75,75), 10);
        spheres[2] = new Geometry.Sphere(new Point2D(11.01497,5.10791), 1);

        Geometry.Rectangle[] rectangles = new Geometry.Rectangle[2];

        rectangles[0] = new Geometry.Rectangle(new Point2D(14,14), new Point2D(20,18));
        rectangles[1] = new Geometry.Rectangle(new Point2D(10,4), new Point2D(12,6));

        for(int i = 0 ; i < 3 ; i++) {
            tree.addObject(spheres[i]);
        }
        for(int i = 0 ; i < 2 ; i++) {
            tree.addObject(rectangles[i]);
        }
        LinkedList<Geometry.GeometryObject> res = tree.getObjects(new Point2D(6,0), new Point2D(14, 8));
        assertEquals(res.get(0).toString(), spheres[2].toString());
        assertEquals(res.get(1).toString(), rectangles[1].toString());
        assertEquals(2, res.size());
    }

    @Test
    public void deleteObject() {
        QuadTree tree = new QuadTree();
        Geometry.Sphere[] spheres = new Geometry.Sphere[3];
        spheres[0] = new Geometry.Sphere(new Point2D(10,10), 5);
        spheres[1] = new Geometry.Sphere(new Point2D(75,75), 10);
        spheres[2] = new Geometry.Sphere(new Point2D(11.01497,5.10791), 1);
        for(int i = 0 ; i < 3 ; i++) {
            tree.addObject(spheres[i]);
        }
        tree.deleteObject(spheres[0]);
        tree.deleteObject(spheres[2]);
        LinkedList<Geometry.GeometryObject> res = tree.getObjects(new Point2D(-100,-100), new Point2D(100, 100));
        assertEquals(res.get(0).toString(), spheres[1].toString());
        assertEquals(1, res.size());
    }
    @Test(expected = IllegalStateException.class)
    public void unlistedObject() {
        QuadTree tree = new QuadTree();
        Geometry.Sphere sphere = new Geometry.Sphere(new Point2D(0,0), 5);
        tree.deleteObject(sphere);
    }
    @Test
    public void streamForFun(){
        QuadTree tree = new QuadTree();
        Geometry.Sphere[] spheres = new Geometry.Sphere[3];
        spheres[0] = new Geometry.Sphere(new Point2D(10,10), 5);
        spheres[1] = new Geometry.Sphere(new Point2D(75,75), 10);
        spheres[2] = new Geometry.Sphere(new Point2D(11.01497,5.10791), 1);
        for(int i = 0 ; i < 3 ; i++) {
            tree.addObject(spheres[i]);
        }
        tree.stream().forEach(s->System.out.println(s));
    }
}