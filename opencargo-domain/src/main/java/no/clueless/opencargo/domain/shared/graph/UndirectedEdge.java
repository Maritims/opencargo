package no.clueless.opencargo.domain.shared.graph;

import java.util.Objects;

public class UndirectedEdge<T, N extends Number> {
    private final T first;
    private final T second;
    private final N distance;

    public UndirectedEdge(T first, T second, N distance) {
        this.first    = Objects.requireNonNull(first, "first cannot be null");
        this.second   = Objects.requireNonNull(second, "second cannot be null");
        this.distance = Objects.requireNonNull(distance, "distance cannot be null");
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public N getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UndirectedEdge<?, ?> edge = (UndirectedEdge<?, ?>) o;

        // Distance must match exactly
        if (!Objects.equals(distance, edge.distance)) return false;

        // Nodes can match either (first == edge.first && second == edge.second)
        // OR (first == edge.second && second == edge.first)
        return (Objects.equals(first, edge.first) && Objects.equals(second, edge.second)) ||
                (Objects.equals(first, edge.second) && Objects.equals(second, edge.first));
    }

    @Override
    public int hashCode() {
        // Combining the hash codes of first and second using addition (+)
        // makes the hashing operation commutative: H(A) + H(B) == H(B) + H(A)
        int nodesHash = Objects.hashCode(first) + Objects.hashCode(second);
        return Objects.hash(nodesHash, distance);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "first=" + first +
                ", second=" + second +
                ", distance=" + distance +
                '}';
    }
}
