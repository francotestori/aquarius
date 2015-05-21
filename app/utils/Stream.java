package utils;

import java.util.ArrayList;
import java.util.List;

public class Stream<T> {
    final private List<T> tList;

    private Stream(List<T> tList) {
        this.tList = tList;
    }

    public static <T> Stream<T> stream(List<T> list){
        return new Stream<>(list);
    }

    public Stream<T> filter (Function<T, Boolean> filter){
        final List<T> aux = new ArrayList<>();
        for (T t : tList) {
            if(filter.execute(t)){
                aux.add(t);
            }
        }
        return new Stream<>(aux);
    }

    public <W> Stream<W> map (Function<T, W> mapper){
        final List<W> aux = new ArrayList<>();
        for (T t : tList) {
            aux.add(mapper.execute(t));
        }
        return new Stream<>(aux);
    }

    public List<T> collect(){
        return tList;
    }
}
