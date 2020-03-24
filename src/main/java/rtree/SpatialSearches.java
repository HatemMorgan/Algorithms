package rtree;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create instances of SpatialSearch implementations
 *
 */
public class SpatialSearches {

    private static final int DEFAULT_MIN_M = 2;
    private static final int DEFAULT_MAX_M = 8;
    private static final RTree.Split DEFAULT_SPLIT_TYPE = RTree.Split.AXIAL;

    private SpatialSearches() {}

    /**
     * Create an R-Tree with default values for m, M, and split type
     *
     * @param builder - Builder implementation used to create HyperRects out of T's
     * @param <T> - The store type of the bound
     *
     * @return SpatialSearch - The spatial search and index structure
     */
    public static <T> SpatialSearch<T> rTree(final RectBuilder<T> builder) {
        return new RTree<>(builder, DEFAULT_MIN_M, DEFAULT_MAX_M, DEFAULT_SPLIT_TYPE);
    }

    /**
     * Create an R-Tree with specified values for m, M, and split type
     *
     * @param builder - Builder implementation used to create HyperRects out of T's
     * @param minM - minimum number of entries per node of this tree
     * @param maxM - maximum number of entries per node of this tree (exceeding this causes node split)
     * @param splitType - type of split to use when M+1 entries are added to a node
     * @param <T> - The store type of the bound
     *
     * @return SpatialSearch - The spatial search and index structure
     */
    public static <T> SpatialSearch<T> rTree(final RectBuilder<T> builder, final int minM, final int maxM, final RTree.Split splitType) {
        return new RTree<>(builder, minM, maxM, splitType);
    }

    /**
     * Create a protected R-Tree with default values for m, M, and split type
     *
     * @param builder - Builder implementation used to create HyperRects out of T's
     * @param <T> - The store type of the bound
     *
     * @return SpatialSearch - The spatial search and index structure
     */
    public static <T> SpatialSearch<T> lockingRTree(final RectBuilder<T> builder) {
        return new ConcurrentRTree<>(rTree(builder), new ReentrantReadWriteLock(true));
    }

    /**
     * Create a protected R-Tree with specified values for m, M, and split type
     *
     * @param builder - Builder implementation used to create HyperRects out of T's
     * @param minM - minimum number of entries per node of this tree
     * @param maxM - maximum number of entries per node of this tree (exceeding this causes node split)
     * @param splitType - type of split to use when M+1 entries are added to a node
     * @param <T> - The store type of the bound
     *
     * @return SpatialSearch - The spatial search and index structure
     */
    public static <T> SpatialSearch<T> lockingRTree(final RectBuilder<T> builder, final int minM, final int maxM, final RTree.Split splitType) {
        return new ConcurrentRTree<>(rTree(builder, minM, maxM, splitType), new ReentrantReadWriteLock(true));
    }

}
