package rtree;

/**
 * the algorithm searches for the pair of rectangles that is the worst combination to have in
 * the same node, and puts them as initial objects into the two new groups. It then searches for the entry which has the
 * strongest preference for one of the groups (in terms of area increase) and assigns the object to this group until all
 * objects are assigned (satisfying the minimum fill).
 *
 * @param <T>
 */
final class QuadraticSplitLeaf<T> extends Leaf<T> {

    protected QuadraticSplitLeaf(final RectBuilder<T> builder, final int mMin, final int mMax) {
        super(builder, mMin, mMax, RTree.Split.QUADRATIC);
    }

    @Override
    protected Node<T> split(final T t) {

        final Branch<T> pNode = new Branch(builder, mMin, mMax, splitType);
        final Node<T> l1Node = create(builder, mMin, mMax, splitType);
        final Node<T> l2Node = create(builder, mMin, mMax, splitType);

        // find the two rectangles that are most wasteful
        double minCost = Double.MIN_VALUE;
        int r1Max = 0, r2Max = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                final HyperRect mbr = r[i].getMbr(r[j]);
                final double cost = mbr.cost() - (r[i].cost() + r[j].cost());
                if (cost > minCost) {
                    r1Max = i;
                    r2Max = j;
                    minCost = cost;
                }
            }
        }

        // two seeds
        l1Node.add(entry[r1Max]);
        l2Node.add(entry[r2Max]);

        for (int i = 0; i < size; i++) {
            if ((i != r1Max) && (i != r2Max)) {
                // classify with respect to nodes
                classify(l1Node, l2Node, entry[i]);
            }
        }

        classify(l1Node, l2Node, t);

        pNode.addChild(l1Node);
        pNode.addChild(l2Node);

        return pNode;
    }

}
