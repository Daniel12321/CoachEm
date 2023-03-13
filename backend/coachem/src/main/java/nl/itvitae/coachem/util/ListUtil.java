package nl.itvitae.coachem.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
