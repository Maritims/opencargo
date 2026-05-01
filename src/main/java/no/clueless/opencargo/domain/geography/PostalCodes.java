package no.clueless.opencargo.domain.geography;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Set;

public class PostalCodes implements Iterable<PostalCode> {
    private final Set<PostalCode> postalCodes;

    public PostalCodes(Set<PostalCode> postalCodes) {
        this.postalCodes = ArgumentExceptionHelper.throwIfNullOrEmpty(postalCodes, "postalCodes");
        if (this.postalCodes.contains(null)) {
            throw new IllegalArgumentException("postalCodes cannot contain null values");
        }
    }

    public boolean contains(PostalCode postalCode) {
        return postalCodes.contains(ArgumentExceptionHelper.throwIfNull(postalCode, "postalCode"));
    }

    @Override
    public Iterator<PostalCode> iterator() {
        return postalCodes.iterator();
    }
}
