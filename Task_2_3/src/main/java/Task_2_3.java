public class Task_2_3 {
    public static void main(String[] args) {
        QuadTree tree = new QuadTree(new Point2D(-100, -100),
                                     new Point2D(+100, +100));
        tree.addObject(new Geometry.Sphere(new Point2D(10,10), 5));                     // (0.0;0.0)^(25.0;25.0)
        Geometry.GeometryObject bigSphere = new Geometry.Sphere(new Point2D(0,0), 50);
        tree.addObject(bigSphere);                                                                  // (-100.0;-100.0)^(100.0;100.0)
        tree.addObject(new Geometry.Sphere(new Point2D(75,75), 10));                    // (50.0;50.0)^(100.0;100.0)
        Geometry.GeometryObject smallSphere = new Geometry.Sphere(new Point2D(11.01497,5.10791), 1);
        tree.addObject(smallSphere);                                                      // (9.375;3.125)^(12.5;6.25)
        tree.addObject(new Geometry.Rectangle(new Point2D(14,14), new Point2D(20,18))); // (12.5;12.5)^(25.0;25.0)
        tree.addObject(new Geometry.Rectangle(new Point2D(10,4), new Point2D(12,6)));   // (9.375;3.125)^(12.5;6.25)

       // tree.getObjects(new Point2D(10.1313,5.84741)).forEach(s->System.out.println(s.toString()));
        System.out.println("Result:");
        tree.getObjects(new Point2D(0,0), new Point2D(21, 19)).forEach(s->System.out.println(s.toString()));
    }
}
