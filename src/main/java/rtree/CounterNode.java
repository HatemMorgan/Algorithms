package rtree;

import java.util.function.Consumer;

final class CounterNode<T> implements Node<T> {
    private final Node<T> node;

    static int searchCount = 0;
    static int bboxEvalCount = 0;

    CounterNode(final Node<T> node) {
        this.node = node;
    }

    @Override
    public boolean isLeaf() {
        return node.isLeaf();
    }

    @Override
    public HyperRect getBound() {
        return node.getBound();
    }

    @Override
    public Node<T> add(T t) {
        return node.add(t);
    }

    @Override
    public Node<T> remove(T t) { return node.remove(t); }

    @Override
    public Node<T> update(T told, T tnew) { return node.update(told, tnew); }

    @Override
    public int search(HyperRect rect, T[] t, int n) {
        searchCount++;
        bboxEvalCount += node.size();
        return node.search(rect, t, n);
    }

    @Override
    public int size() {
        return node.size();
    }

    @Override
    public int totalSize() {
        return node.totalSize();
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        node.forEach(consumer);
    }

    @Override
    public void search(HyperRect rect, Consumer<T> consumer) {
        node.search(rect, consumer);
    }

    @Override
    public int intersects(HyperRect rect, T[] t, int n) {
        return node.intersects(rect, t, n);
    }

    @Override
    public void intersects(HyperRect rect, Consumer<T> consumer) {
        node.intersects(rect, consumer);
    }

    @Override
    public boolean contains(HyperRect rect, T t) {
        return node.contains(rect, t);
    }

    @Override
    public void collectStats(Stats stats, int depth) {
        node.collectStats(stats, depth);
    }

    @Override
    public Node<T> instrument() {
        return this;
    }
}
