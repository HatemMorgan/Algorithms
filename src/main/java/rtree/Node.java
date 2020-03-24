package rtree;

import java.util.function.Consumer;

interface Node<T> {

    /**
     * @return boolean - true if this node is a leaf
     */
    boolean isLeaf();

    /**
     * @return Rect - the bounding rectangle for this node
     */
    HyperRect getBound();

    /**
     * Add t to the index
     *
     * @param t - value to add to index
     */
    Node<T> add(T t);

    /**
     * Remove t from the index
     *
     * @param t - value to remove from index
     */
    Node<T> remove(T t);

    /**
     * update an existing t in the index
     *
     * @param told - old index to be updated
     * @param tnew - value to update old index to
     */
    Node<T> update(T told, T tnew);

    /**
     * Search for rect within this node
     *
     * @param rect - HyperRect to search for
     * @param t - array of found results
     * @param n - total result count so far (from recursive call)
     * @return result count from search of this node
     */
    int search(HyperRect rect, T[] t, int n);

    /**
     * Visitor pattern:
     *
     * Consumer "accepts" every node contained by the given rect
     *
     * @param rect - limiting rect
     * @param consumer
     */
    void search(HyperRect rect, Consumer<T> consumer);

    /**
     * intersect rect with this node
     *
     * @param rect - HyperRect to search for
     * @param t - array of found results
     * @param n - total result count so far (from recursive call)
     * @return result count from search of this node
     */
    int intersects(HyperRect rect, T[] t, int n);

    /**
     * Visitor pattern:
     *
     * Consumer "accepts" every node intersecting the given rect
     *
     * @param rect - limiting rect
     * @param consumer
     */
    void intersects(HyperRect rect, Consumer<T> consumer);


    /**
     *
     * @param rect
     * @param t
     * @return boolean true if subtree contains t
     */
    boolean contains(HyperRect rect, T t);

    /**
     * The number of entries in the node
     *
     * @return int - entry count
     */
    int size();

    /**
     * The number of entries in the subtree
     *
     * @return int - entry count
     */
    int totalSize();

    /**
     * Consumer "accepts" every node in the entire index
     *
     * @param consumer
     */
    void forEach(Consumer<T> consumer);

    /**
     * Recurses over index collecting stats
     *
     * @param stats - Stats object being populated
     * @param depth - current depth in tree
     */
    void collectStats(Stats stats, int depth);

    /**
     * Visits node, wraps it in an instrumented node, (see CounterNode)
     *
     * @return instrumented node wrapper
     */
    Node<T> instrument();

}
