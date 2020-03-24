package rtree;

import java.util.Arrays;
import java.util.Comparator;

final class AxialSplitLeaf<T> extends Leaf<T> {

    protected AxialSplitLeaf(final RectBuilder<T> builder, final int mMin, final int mMax) {
        super(builder, mMin, mMax, RTree.Split.AXIAL);
    }

    @Override
    protected Node<T> split(final T t) {
        final Branch<T> pNode = new Branch(builder, mMin, mMax, splitType);
        final Node<T> l1Node = create(builder, mMin, mMax, splitType);
        final Node<T> l2Node = create(builder, mMin, mMax, splitType);
        final int nD = r[0].getNDim();

        // choose axis to split
        int axis = 0;
        double rangeD = mbr.getRange(0);
        for(int d=1; d<nD; d++) {
            // split along the greatest range extent
            final double dr = mbr.getRange(d);
            if(dr > rangeD) {
                axis = d;
                rangeD = dr;
            }
        }

        final int splitDimension = axis;

        // sort along split dimension
        final HyperRect[] sortedMbr = Arrays.copyOf(r, r.length);

        Arrays.sort(sortedMbr, new Comparator<HyperRect>() {
            public int compare(final HyperRect o1, final HyperRect o2) {
                final HyperPoint p1 = o1.getCentroid();
                final HyperPoint p2 = o2.getCentroid();

                return p1.getCoord(splitDimension).compareTo(p2.getCoord(splitDimension));
            }
        });

        // divide sorted leafs
        for(int i=0; i<size/2; i++) {
            outerLoop:
            for(int j=0; j<size; j++) {
                if(r[j] == sortedMbr[i]) {
                    l1Node.add(entry[j]);
                    break outerLoop;
                }
            }
        }

        for(int i=size/2; i<size; i++) {
            outerLoop:
            for(int j=0; j<size; j++) {
                if(r[j] == sortedMbr[i]) {
                    l2Node.add(entry[j]);
                    break outerLoop;
                }
            }
        }

        classify(l1Node, l2Node, t);

        pNode.addChild(l1Node);
        pNode.addChild(l2Node);

        return pNode;
    }

}
