package primitives;

public class Point {

    protected final  Double3 xyz ;
    public static final Point ZERO = new Point(0, 0, 0);

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x,y,z);
    }
    Point(Double3 xyz) {
        this.xyz = xyz;
    }
    public Vector subtract(Point p2) {
        return new Vector(this.xyz.subtract(p2.xyz));//לבדוק שהוספתי את הראשון לשני בצורה נכונה
    }
    public Point add(Vector vec) {
       return new Point(this.xyz.add(vec.xyz)); //לבדוק שהוספתי את הראשון לשני בצורה נכונה
    }
    public double distanceSquared(Point other) {
        Double3 tmp = xyz.subtract(other.xyz).product(xyz.subtract(other.xyz));
        return tmp.d1 + tmp.d2 + tmp.d3;
    }

    public  double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return xyz.toString();
    }
}
