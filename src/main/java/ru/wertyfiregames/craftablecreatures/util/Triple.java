/**
    File created on 18:18 23.08.2024 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.util;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Triple<K, F, S> {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    K getKey(F firstValue, S secondValue);

    List<K> getKeys();

    F getFirstValue(Object key, Object secondValue);

    List<F> getFirstValues();

    S getSecondValue(Object key, Object firstValue);

    List<S> getSecondValues();

    Pair<F, S> put(K key, F firstValue, S secondValue);

    Pair<F, S> remove(K key);

    default boolean remove(K key, F firstValue, S secondValue) {
        Object curFValue = getFirstValue(key, secondValue);
        Object curSValue = getSecondValue(key, firstValue);
        if (!Objects.equals(curFValue, firstValue) || !Objects.equals(curSValue, secondValue) ||
                (curFValue == null && !containsKey(key)) || (curSValue == null && !containsKey(key))) return false;
        remove(key);
        return true;
    }

    void putAll(Triple<? extends K, ? extends F, ? extends S> t);

    void clear();

    boolean validate();

    Set<K> keySet();

    Set<TripleEntry<K, F, S>> entrySet();

    interface TripleEntry<K, F, S> {
        K getKey();

        F getFirstValue();

        S getSecondValue();

        F setFirstValue(F f);

        S setSecondValue(S s);

        boolean equals(Object obj);

        int hashCode();
    }

    boolean equals(Object obj);

    int hashCode();

    default void forEach(BiConsumer<? super K, ? super Pair<F, S>> action) {
        Objects.requireNonNull(action);
        for (TripleEntry<K, F, S> entry : entrySet()) {
            K k;
            F f;
            S s;
            try {
                k = entry.getKey();
                f = entry.getFirstValue();
                s = entry.getSecondValue();
            } catch (IllegalStateException e) {
                throw new ConcurrentModificationException(e);
            }
            action.accept(k, new Pair<>(f, s));
        }
    }

    default void replaceAll(BiFunction<? super K, ? super Pair<F, S>, ? extends Pair<F, S>> function) {
        Objects.requireNonNull(function);
        for (TripleEntry<K, F, S> entry : entrySet()) {
            K k;
            F f;
            S s;
            try {
                k = entry.getKey();
                f = entry.getFirstValue();
                s = entry.getSecondValue();
            } catch (IllegalStateException e) {
                throw new ConcurrentModificationException(e);
            }

            Pair<F, S> pair = function.apply(k, new Pair<>(f, s));

            try {
                entry.setFirstValue(pair.getFirst());
                entry.setSecondValue(pair.getSecond());
            } catch (IllegalStateException e) {
                throw new ConcurrentModificationException(e);
            }
        }
    }

    default Pair<F, S> putIfAbsent(K key, F firstValue, S secondValue) {
        Pair<F, S> p = new Pair<>(null, null);
        F f = getFirstValue(key, secondValue);
        S s = getSecondValue(key, firstValue);
        if (f == null && s == null) {
            p = new Pair<>(firstValue, secondValue);
        }

        return p;
    }

    default boolean replace(K key, Pair<F, S> oldPair, Pair<F, S> newPair) {
        Object curFValue = getFirstValue(key, oldPair.getSecond());
        Object curSValue = getSecondValue(key, oldPair.getFirst());
        if (!Objects.equals(curFValue, oldPair.getFirst()) || !Objects.equals(curSValue, oldPair.getSecond()) ||
                (curFValue == null && !containsKey(key)) || (curSValue == null && !containsKey(key))) return false;
        put(key, newPair.getFirst(), newPair.getSecond());
        return true;
    }

    default Pair<F, S> replace(K key, F firstValue, S secondValue) {
        F curFValue;
        S curSValue;
        Pair<F, S> p = null;
        if (((curFValue = getFirstValue(key, secondValue)) != null || (curSValue = getSecondValue(key, firstValue)) != null
                || containsKey(key)))
            p = put(key, firstValue, secondValue);
        return p;
    }

    default Pair<F, S> computeIfAbsent(K key, F firstValue, S secondValue, Function<? super K, ? extends Pair<F, S>>
            mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        F f = getFirstValue(key, secondValue);
        S s = getSecondValue(key, firstValue);
        if (f == null && s == null) {
            F newF;
            S newS;
            Pair<F, S> p = mappingFunction.apply(key);
            if ((newF = p.getFirst()) != null &&
                    (newS = p.getSecond()) != null) {
                put(key, newF, newS);
                return new Pair<>(newF, newS);
            }
        }

        return new Pair<>(f, s);
    }

    default Pair<F, S> computeIfPresent(K key, F firstValue, S secondValues,
                                        BiFunction<? super K, ? super Pair<F, S>, ? extends Pair<F, S>> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        F oldF = getFirstValue(key, secondValues);
        S oldS = getSecondValue(key, firstValue);
        if (oldF != null && oldS != null) {
            Pair<F, S> p = remappingFunction.apply(key, new Pair<>(oldF, oldS));
            if (p.getFirst() != null && p.getSecond() != null) {
                put(key, p.getFirst(), p.getSecond());
                return p;
            } else {
                remove(key);
                return null;
            }
        } else return null;
    }

    default Pair<F, S> compute(K key, F firstValue, S secondValue, BiFunction<? super K, ? super Pair<F, S>,
            ? extends Pair<F, S>> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        F oldF = getFirstValue(key, secondValue);
        S oldS = getSecondValue(key, firstValue);

        Pair<F, S> p = remappingFunction.apply(key, new Pair<>(oldF, oldS));
        F newF = p.getFirst();
        S newS = p.getSecond();
        if (newF == null && newS == null) {
            if (oldF != null || oldS != null || containsKey(key)) remove(key);
            return null;
        } else {
            put(key, newF, newS);
            return new Pair<>(newF, newS);
        }
    }

    default Pair<F, S> merge(K key, F firstValue, S secondValue,
                             BiFunction<? super Pair<F, S>, ? super Pair<F, S>, ? extends Pair<F, S>> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(secondValue);
        F oldF = getFirstValue(key, secondValue);
        S oldS = getSecondValue(key, firstValue);
        Pair<F, S> p = remappingFunction.apply(new Pair<>(oldF, oldS), new Pair<>(firstValue, secondValue));
        F newF = oldF == null ? firstValue : p.getFirst();
        S newS = oldS == null ? secondValue : p.getSecond();
        if (newF == null  || newS == null) remove(key);
        else put(key, newF, newS);
        return new Pair<>(newF, newS);
    }
}