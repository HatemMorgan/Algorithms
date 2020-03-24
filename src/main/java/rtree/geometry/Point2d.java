package rtree.geometry;

import rtree.HyperPoint;
import rtree.HyperRect;
import rtree.RTree;
import rtree.RectBuilder;

public final class Point2d implements HyperPoint {
    public static final int X = 0;
    public static final int Y = 1;

    final double x, y;

    public Point2d(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public int getNDim() {
        return 2;
    }

    public Double getCoord(final int d) {
        if(d==X) {
            return x;
        } else if(d==Y) {
            return y;
        } else {
            throw new IllegalArgumentException("Invalid dimension");
        }
    }

    public double distance(final HyperPoint p) {
        final Point2d p2 = (Point2d)p;

        final double dx = p2.x-x;
        final double dy = p2.y-y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double distance(final HyperPoint p, final int d) {
        final Point2d p2 = (Point2d)p;
        if(d == X) {
            return Math.abs(p2.x - x);
        } else if (d == Y) {
            return Math.abs(p2.y - y);
        } else {
            throw new IllegalArgumentException("Invalid dimension");
        }
    }

    public boolean equals(final Object o) {
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;

        final Point2d p = (Point2d) o;
        return RTree.isEqual(x, p.x) &&
                RTree.isEqual(y, p.y);
    }


    public int hashCode() {
        return Double.hashCode(x) ^ 31*Double.hashCode(y);
    }

    public final static class Builder implements RectBuilder<Point2d> {

        public HyperRect getBBox(final Point2d point) {
            return new Rect2d(point);
        }

        public HyperRect getMbr(final HyperPoint p1, final HyperPoint p2) {
            final Point2d point1 = (Point2d)p1;
            final Point2d point2 = (Point2d)p2;
            return new Rect2d(point1, point2);
        }
    }
}