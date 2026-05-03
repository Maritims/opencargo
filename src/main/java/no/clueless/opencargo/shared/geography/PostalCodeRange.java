package no.clueless.opencargo.shared.geography;

import no.clueless.opencargo.bindings.PostalCodeRangeSpecificationDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Objects;

public class PostalCodeRange implements PostalCodeSpecification {
    private final PostalCode start;
    private final PostalCode end;

    public PostalCodeRange(PostalCode start, PostalCode end) {
        if (start == null) {
            throw new IllegalArgumentException("start must not be null");
        }
        if (end == null) {
            throw new IllegalArgumentException("end must not be null");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("start must be less than end, but start was " + start + " and end was " + end);
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean contains(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode must not be null");
        }
        return postalCode.compareTo(start) >= 0 && postalCode.compareTo(end) <= 0;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        PostalCodeRange that = (PostalCodeRange) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "PostalCodeRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public static PostalCodeRange from(PostalCodeRangeSpecificationDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return new PostalCodeRange(new PostalCode(dto.getMinInclusive()), new PostalCode(dto.getMaxInclusive()));
    }
}
