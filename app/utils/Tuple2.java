package utils;

public class Tuple2<T1, T2> {
    private T1 t1;
    private T2 t2;

    private Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public static <K, V> Tuple2<K, V> apply(K k, V v){
        return new Tuple2<>(k, v);
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }
}
