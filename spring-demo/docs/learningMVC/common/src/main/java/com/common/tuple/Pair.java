package com.springdemo.learningMVC.common.src.main.java.com.common.tuple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

/**
 * A pair consisting of two elements.
 * <p>
 * This class is an abstract implementation defining the basic API. It refers to the
 * elements as 'left' and 'right'. It also implements the {@code Map.Entry} interface
 * where the key is 'left' and the value is 'right'.
 * <p>
 * Subclass implementations may be mutable or immutable. However, there is no restriction
 * on the type of the stored objects that may be stored. If mutable objects are stored in
 * the pair, then the pair itself effectively becomes mutable.
 * <p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 * @author bayard
 * @version $Id: Pair.java 290 2014-10-27 08:48:18Z fuchun $
 * @since Lang 3.0
 */
public abstract class Pair<L, R> implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable {

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 4954918890077093841L;

    /**
     * Obtains an immutable pair of from two objects inferring the generic types.
     * <p>
     * This factory allows the pair to be created using inference to obtain the generic
     * types.
     *
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @param left  the left element, may be null
     * @param right the right element, may be null
     * @return a pair formed from the two parameters, not null
     */
    @JsonCreator
    public static <L, R> Pair<L, R> of(
            @JsonProperty("l") L left,
            @JsonProperty("r") R right) {
        return new ImmutablePair<L, R>(left, right);
    }

    // -----------------------------------------------------------------------

    /**
     * Gets the left element from this pair.
     * <p>
     * When treated as a key-value pair, this is the key.
     *
     * @return the left element, may be null
     */
    @JsonProperty("l")
    public abstract L getLeft();

    /**
     * Gets the right element from this pair.
     * <p>
     * When treated as a key-value pair, this is the value.
     *
     * @return the right element, may be null
     */
    @JsonProperty("r")
    public abstract R getRight();

    /**
     * Gets the key from this pair.
     * <p>
     * This method implements the {@code Map.Entry} interface returning the left element
     * as the key.
     *
     * @return the left element as the key, may be null
     */
    @JsonIgnore
    public final L getKey() {
        return getLeft();
    }

    /**
     * Gets the value from this pair.
     * <p>
     * This method implements the {@code Map.Entry} interface returning the right element
     * as the value.
     *
     * @return the right element as the value, may be null
     */
    @JsonIgnore
    public R getValue() {
        return getRight();
    }

    // -----------------------------------------------------------------------

    /**
     * Compares the pair based on the left element followed by the right element. The
     * types must be {@code Comparable}.
     *
     * @param other the other pair, not null
     * @return negative if this is less, zero if equal, positive if greater
     */
    @Override
    public int compareTo(@Nonnull Pair<L, R> other) {
        return ComparisonChain.start()
                .compare((Comparable) getLeft(), (Comparable) other.getLeft())
                .compare((Comparable) getRight(), (Comparable) other.getRight())
                .result();
    }

    /**
     * Compares this pair to another based on the two elements.
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the pair are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map.Entry<?, ?>) {
            Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
            return Objects.equal(getKey(), other.getKey()) && Objects.equal(getValue(), other.getValue());
        }
        return false;
    }

    /**
     * Returns a suitable hash code. The hash code follows the definition in
     * {@code Map.Entry}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        // see Map.Entry API specification
        return (getKey() == null ? 0 : getKey().hashCode())
                ^ (getValue() == null ? 0 : getValue().hashCode());
    }

    /**
     * Returns a String representation of this pair using the format
     * {@code ($left, $right)}.
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return toString("(%s,%s)");
    }

    /**
     * Formats the receiver using the given format.
     * <p>
     * This uses {@link java.util.Formattable} to perform the formatting. Two variables
     * may be used to embed the left and right elements. Use {@code %1$s} for the left
     * element (key) and {@code %2$s} for the right element (value). The default format
     * used by {@code toString()} is {@code (%1$s,%2$s)}.
     *
     * @param format the format string, optionally containing {@code %1$s} and
     *               {@code %2$s}, not null
     * @return the formatted string, not null
     */
    public String toString(String format) {
        return String.format(format, getLeft(), getRight());
    }

}