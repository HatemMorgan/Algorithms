package rtree;

import java.util.function.Consumer;
import java.util.Collection;


public interface SpatialSearch<T> {
    /**
     * Search for entries intersecting given bounding rect
     *
     * @param rect - Bounding rectangle to use for querying
     * @param t - Array to store found entries
     *
     * @return Number of results found
     */
    int intersects(HyperRect rect, T[] t);

    /**
     * Search for entries intersecting given bounding rect
     *
     * @param rect - Bounding rectangle to use for querying
     * @param consumer - callback to receive intersecting objects
     *
     */
    void intersects(HyperRect rect, Consumer<T> consumer);

    /**
     * Search for entries contained by the given bounding rect
     *
     * @param rect - Bounding rectangle to use for querying
     * @param t - Array to store found entries
     *
     * @return Number of results found
     */
    int search(HyperRect rect, T[] t);

    /**
     * Search for entries contained by the given bounding rect
     *
     * @param rect - Bounding rectangle to use for querying
     * @param consumer - callback to receive intersecting objects
     *
     */
    void search(HyperRect rect, Consumer<T> consumer);

    /**
     * Search for entries contained by the given bounding rect
     *
     * @param rect - Bounding rectangle to use for querying
     * @param collection - collection to receive results
     *
     */
    void search(HyperRect rect, Collection<T> collection);

    /**
     * returns whether or not the HyperRect will enclose all of the data entries in t
     *
     * @param t - entry
     *
     * @return boolean - Whether or not all entries lie inside rect
     */
    boolean contains(T t);

    /**
     * Add the data entry to the SpatialSearch structure
     *
     * @param t Data entry to be added
     */
    void add(T t);

    /**
     * Remove the data entry from the SpatialSearch structure
     *
     * @param t Data entry to be removed
     */
    void remove(T t);

    /**
     * Update entry in tree
     *
     * @param told - Entry to update
     * @param tnew - Entry to update it to
     */
    void update(T told, T tnew);

    /**
     * Get the number of entries in the tree
     *
     * @return entry count
     */
    int getEntryCount();

    /**
     * Iterate over all entries in the tree
     *
     * @param consumer - callback for each element
     */
    void forEach(Consumer<T> consumer);

    Stats collectStats();

}
