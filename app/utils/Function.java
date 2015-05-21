package utils;

/**
 * Given any A function return R
 * @param <A> argument type
 * @param <R> response type
 */
public interface Function<A, R> {
    R execute(A a);
}
