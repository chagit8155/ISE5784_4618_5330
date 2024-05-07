package primitives;

public class Ray {
    final private Point head;
    final private Vector direction;

    public Ray(Point head, Vector direction) {
        this.head = head;
        // Normalize the direction vector before storing it
        this.direction = direction.normalize();
    }
}
