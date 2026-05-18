package no.clueless.opencargo.domain.shared.graph;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UndirectedMatrix<T> {
    private final Map<T, Integer> indexMap;
    private final int[]           storage;

    private UndirectedMatrix(Set<T> vertices) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("vertices cannot be null or empty");
        }

        var iterator = vertices.iterator();
        var n        = vertices.size();
        var indexMap = new HashMap<T, Integer>();

        for (var i = 0; i < n; i++) {
            indexMap.put(iterator.next(), i);
        }

        this.indexMap = Map.copyOf(indexMap);
        this.storage  = new int[(n * (n - 1)) / 2];
    }

    private int getFlatIndex(int i, int j) {
        var min = Math.min(i, j);
        var max = Math.max(i, j);
        // Use 'min' inside the row-skipping logic, and subtract 1 for the column step
        return (min * (2 * indexMap.size() - min - 1)) / 2 + (max - min - 1);
    }

    public int getDistance(T from, T to) {
        if (from == null) {
            throw new IllegalArgumentException("from cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to cannot be null");
        }

        var i = Objects.requireNonNull(indexMap.get(from), "from vertex not found in the matrix");
        var j = Objects.requireNonNull(indexMap.get(to), "to vertex not found in the matrix");

        if (i.equals(j)) {
            return 0;
        }

        var f = getFlatIndex(i, j);
        return storage[f];
    }

    public static <T> UndirectedMatrix<T> of(Set<UndirectedEdge<T, Integer>> edges) {
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException("edges cannot be null or empty");
        }
        var vertices = edges.stream()
                .flatMap(edge -> Stream.of(edge.getFirst(), edge.getSecond()))
                .collect(Collectors.toSet());
        var undirectedMatrix = new UndirectedMatrix<>(vertices);

        for (var edge : edges) {
            var i = undirectedMatrix.indexMap.get(edge.getFirst());
            var j = undirectedMatrix.indexMap.get(edge.getSecond());
            if (Objects.equals(i, j)) {
                continue; // We don't store self-to-self.
            }

            var flatIndex = undirectedMatrix.getFlatIndex(i, j);
            undirectedMatrix.storage[flatIndex] = edge.getDistance();
        }

        return undirectedMatrix;
    }

    public static <T> UndirectedMatrix<T> fromLines(List<String> lines, Function<String, T> vertexMapper, boolean firstColumnIsVertexName) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("lines cannot be null or empty");
        }

        var headerTokens = lines.get(0).trim().split(",");
        var vertices     = new LinkedHashSet<T>();
        var startColumn  = firstColumnIsVertexName ? 1 : 0;

        // Start at 1 because the first column is the vertex name.
        for (var col = startColumn; col < headerTokens.length; col++) {
            vertices.add(vertexMapper.apply(headerTokens[col]));
        }

        var undirectedMatrix = new UndirectedMatrix<>(vertices);

        // Start at 1 because the first line is the header line.
        for (var row = 1; row < lines.size(); row++) {
            var line = lines.get(row).trim();
            if (line.isBlank()) {
                continue;
            }

            var rowTokens = line.split(",");
            // The vertex is always the first column.
            var vertex      = vertexMapper.apply(rowTokens[0]);
            var vertexIndex = undirectedMatrix.indexMap.get(vertex);

            for (var col = row + 1; col < rowTokens.length; col++) {
                var targetVertex = vertexMapper.apply(headerTokens[col]);
                var j            = undirectedMatrix.indexMap.get(targetVertex);
                var distance     = Integer.parseInt(rowTokens[col]);
                var flatIndex    = undirectedMatrix.getFlatIndex(vertexIndex, j);

                undirectedMatrix.storage[flatIndex] = distance;
            }
        }

        return undirectedMatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UndirectedMatrix<?> that = (UndirectedMatrix<?>) o;
        return Objects.equals(indexMap, that.indexMap) && Objects.deepEquals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexMap, Arrays.hashCode(storage));
    }

    @Override
    public String toString() {
        return "UndirectedMatrix{" +
                "indexMap=" + indexMap +
                ", storage=" + Arrays.toString(storage) +
                '}';
    }
}
