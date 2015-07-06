package com.springdemo.learningMVC.common.src.main.java.com.common.tuple;

/**
 * A mutable triple consisting of three {@code Object} elements.
 * <p>
 * Not #ThreadSafe#
 *
 * @param <L> the left element type
 * @param <M> the middle element type
 * @param <R> the right element type
 * @version $Id: MutableTriple.java 290 2014-10-27 08:48:18Z  $
 */
public class MutableTriple<L, M, R> extends Triple<L, M, R> {
    /**
     * Serialization version
     */
    private static final long serialVersionUID = 1L;
    /**
     * Left object
     */
    public L left;
    /**
     * Middle object
     */
    public M middle;
    /**
     * Right object
     */
    public R right;

    /**
     * Obtains an mutable triple of three objects inferring the generic types.
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
    public static <L, M, R> MutableTriple<L, M, R> of(
            final L left, final M middle, final R right) {
        return new MutableTriple<>(left, middle, right);
    }

    /**
     * Create a new triple instance of three nulls.
     */
    public MutableTriple() {
        super();
    }

    /**
     * Create a new triple instance.
     *
     * @param left   the left value, may be null
     * @param middle the middle value, may be null
     * @param right  the right value, may be null
     */
    public MutableTriple(final L left, final M middle, final R right) {
        super();
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public L getLeft() {
        return left;
    }

    /**
     * Sets the left element of the triple.
     *
     * @param left the new value of the left element, may be null
     */
    public void setLeft(final L left) {
        this.left = left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M getMiddle() {
        return middle;
    }

    /**
     * Sets the middle element of the triple.
     *
     * @param middle the new value of the middle element, may be null
     */
    public void setMiddle(final M middle) {
        this.middle = middle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R getRight() {
        return right;
    }

    /**
     * Sets the right element of the triple.
     *
     * @param right the new value of the right element, may be null
     */
    public void setRight(final R right) {
        this.right = right;
    }
}