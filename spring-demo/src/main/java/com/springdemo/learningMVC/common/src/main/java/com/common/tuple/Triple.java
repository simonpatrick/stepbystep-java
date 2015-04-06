package com.springdemo.learningMVC.common.src.main.java.com.common.tuple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ComparisonChain;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A triple consisting of three elements.
 * <p>
 * This class is an abstract implementation defining the basic API.
 * It refers to the elements as 'left', 'middle' and 'right'.
 * <p>
 * Subclass implementations may be mutable or immutable.
 * However, there is no restriction on the type of the stored objects that may be stored.
 * If mutable objects are stored in the triple, then the triple itself effectively becomes mutable.
 *
 * @param <L> the left element type
 * @param <M> the middle element type
 * @param <R> the right element type
 * @version $Id: Triple.java 290 2014-10-27 08:48:18Z fuchun $
 */
public abstract class Triple<L, M, R> implements Comparable<Triple<L, M, R>>, Serializable {

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Obtains an immutable triple of from three objects inferring the generic types.
     * <p>
     * This factory allows the triple to be created using inference to
     * obtain the generic types.
     *
     * @param <L>    the left element type
     * @param <M>    the middle element type
     * @param <R>    the right element type
     * @param left   the left element, may be null
     * @param middle the middle element, may be null
     * @param right  the right element, may be null
     * @return a triple formed from the three parameters, not null
     */
    @JsonCreator
    public static <L, M, R> Triple<L, M, R> of(
            @JsonProperty("l") final L left,
            @JsonProperty("m") final M middle,
            @JsonProperty("r") final R right) {
        return new ImmutableTriple<>(left, middle, right);
    }
    //-----------------------------------------------------------------------

    /**
     * Gets the left element from this triple.
     *
     * @return the left element, may be null
     */
    @JsonProperty("l")
    public abstract L getLeft();

    /**
     * Gets the middle element from this triple.
     *
     * @return the middle element, may be null
     */
    @JsonProperty("m")
    public abstract M getMiddle();

    /**
     * Gets the right element from this triple.
     *
     * @return the right element, may be null
     */
    @JsonProperty("r")
    public abstract R getRight();
    //-----------------------------------------------------------------------

    /**
     * Compares the triple based on the left element, followed by the middle element,
     * finally the right element.
     * The types must be {@code Comparable}.
     *
     * @param other the other triple, not null
     * @return negative if this is less, zero if equal, positive if greater
     * @throws NullPointerException     if {@code other} is {@literal null}.
     * @throws IllegalArgumentException if {@code other} L, M, R is not Comparable,
     *                                  or this triple L, M, R is not Comparable.
     */
    @Override
    public int compareTo(@Nonnull final Triple<L, M, R> other) {
        checkNotNull(other, "other");
        checkArgument(other.getLeft() instanceof Comparable &&
                        other.getMiddle() instanceof Comparable &&
                        other.getRight() instanceof Comparable,
                "This triple left, middle, right must be implements Comparable");
        checkArgument(getLeft() instanceof Comparable &&
                        getMiddle() instanceof Comparable &&
                        getRight() instanceof Comparable,
                "Other triple left, middle, right must be implements Comparable");
        return ComparisonChain.start()
                .compare((Comparable<?>) getLeft(), (Comparable<?>) other.getLeft())
                .compare((Comparable<?>) getMiddle(), (Comparable<?>) other.getMiddle())
                .compare((Comparable<?>) getRight(), (Comparable<?>) other.getRight())
                .result();
    }

    /**
     * Compares this triple to another based on the three elements.
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the triple are equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Triple<?, ?, ?>) {
            final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getMiddle(), other.getMiddle())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    /**
     * Returns a suitable hash code.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (getLeft() == null ? 0 : getLeft().hashCode()) ^
                (getMiddle() == null ? 0 : getMiddle().hashCode()) ^
                (getRight() == null ? 0 : getRight().hashCode());
    }

    /**
     * Returns a String representation of this triple using the format {@code ($left, $middle, $right)}.
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return toString("(%s,%s,%s)");
    }

    /**
     * Formats the receiver using the given format.
     * <p>
     * This uses {@link java.util.Formattable} to perform the formatting. Three variables may
     * be used to embed the left and right elements. Use {@code %1$s} for the left
     * element, {@code %2$s} for the middle and {@code %3$s} for the right element.
     * The default format used by {@code toString()} is <code>(%1s,%2s,%3s)</code>.
     *
     * @param format the format string, optionally containing {@code %1$s}, {@code %2$s} and {@code %3$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getLeft(), getMiddle(), getRight());
    }
}
