package rtree;

public interface HyperPoint {

    /**
     * The number of dimensions represented by this point
     *
     * @return dimension count
     */
    int getNDim();

    /**
     * Get the value of this point in the given dimension
     *
     * @param d - dimension
     *
     * @param <D> - A comparable coordinate 
     *
     * @return D - value of this point in the dimension
     * @throws IllegalArgumentException if a non-existent dimension is requested
     */
    <D extends Comparable<D>> D getCoord(int d);

    /**
     * Calculate the distance from this point to the given point across all dimensions
     *
     * @param p - point to calculate distance to
     *
     * @return distance to the point
     * @throws IllegalArgumentException if a non-existent dimension is requested
     */
    double distance(HyperPoint p);

    /**
     * Calculate the distance from this point to the given point in a specific dimension
     *
     * @param p - point to calculate distance to
     * @param d - dimension to use in calculation
     *
     * @return distance to the point in the fiven dimension
     */
    double distance(HyperPoint p, int d);

}
