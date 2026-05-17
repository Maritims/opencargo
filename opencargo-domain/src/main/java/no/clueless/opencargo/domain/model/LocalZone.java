package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.shared.PostalCode;

import java.util.Objects;

public class LocalZone {
    private final PostalCode postalCode;
    private final int        inboundZones;
    private final int        outboundZones;

    public LocalZone(PostalCode postalCode, int inboundZones, int outboundZones) {
        if(inboundZones < 0) {
            throw new IllegalArgumentException("inboundZones cannot be negative");
        }
        if(outboundZones < 0) {
            throw new IllegalArgumentException("outboundZones cannot be negative");
        }
        this.postalCode    = Objects.requireNonNull(postalCode, "postalCode cannot be null");
        this.inboundZones  = inboundZones;
        this.outboundZones = outboundZones;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public int getInboundZones() {
        return inboundZones;
    }

    public int getOutboundZones() {
        return outboundZones;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocalZone localZone = (LocalZone) o;
        return inboundZones == localZone.inboundZones && outboundZones == localZone.outboundZones && Objects.equals(postalCode, localZone.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, inboundZones, outboundZones);
    }

    @Override
    public String toString() {
        return "LocalZone{" +
                "postalCode=" + postalCode +
                ", inboundZones=" + inboundZones +
                ", outboundZones=" + outboundZones +
                '}';
    }
}
