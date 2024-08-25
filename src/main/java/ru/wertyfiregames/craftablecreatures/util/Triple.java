/**
    File created on 18:18 23.08.2024 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.util;

import java.util.List;

public interface Triple<K, F, S> {
    int size();

    boolean isEmpty();

    K getKey(F firstValue, S secondValue);

    K getKey(int index);

    List<K> getKeys();

    F getFirstValue(K key, S secondValue);

    F getFirstValue(int index);

    List<F> getFirstValues();

    S getSecondValue(K key, F firstValue);

    S getSecondValue(int index);

    List<S> getSecondValues();

    void put(K key, F firstValue, S secondValue);

    void put(K key, F firstValue, S secondValue, int index);

    void remove(K key, F firstValue, S secondValue);

    void putAll(Triple<? extends K, ? extends F, ? extends S> t);

    void clear();

    boolean validate();
}