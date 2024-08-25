/**
 * File created on 18:52 23.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import java.util.*;

public class ListTriple<K, F, S> implements Triple<K, F, S> {
    private final List<K> keysList;
    private final List<F> firstValuesList;
    private final List<S> secondValuesList;

    public ListTriple() {
        keysList = new ArrayList<>();
        firstValuesList = new ArrayList<>();
        secondValuesList = new ArrayList<>();
    }

    public int size() {
        return keysList.size();
    }

    public boolean isEmpty() {
        return keysList.isEmpty();
    }

    public K getKey(F firstValue, S secondValue) {
        int index = firstValuesList.indexOf(firstValue);
        return (index != -1 && secondValuesList.get(index).equals(secondValue)) ? keysList.get(index) : null;
    }

    public K getKey(int index) {
        return keysList.get(index);
    }

    public List<K> getKeys() {
        return keysList;
    }

    public F getFirstValue(K key, S secondValue) {
        int index = keysList.indexOf(key);
        if (index != -1 && secondValuesList.get(index).equals(secondValue))
            return firstValuesList.get(index);
        return null;
    }

    public F getFirstValue(int index) {
        return firstValuesList.get(index);
    }

    public List<F> getFirstValues() {
        return firstValuesList;
    }

    public S getSecondValue(K key, F firstValue) {
        int index = keysList.indexOf(key);
        return (index != -1 && firstValuesList.get(index).equals(firstValue)) ? secondValuesList.get(index) : null;
    }

    public S getSecondValue(int index) {
        return secondValuesList.get(index);
    }

    public List<S> getSecondValues() {
        return secondValuesList;
    }

    public void put(K key, F firstValue, S secondValue) {
        keysList.add(key);
        firstValuesList.add(firstValue);
        secondValuesList.add(secondValue);

        if (!validate()) {
            keysList.remove(keysList.size() - 1);
            firstValuesList.remove(firstValuesList.size() - 1);
            secondValuesList.remove(secondValuesList.size() - 1);
            throw new IllegalArgumentException("Tried to add already added key."); //TODO remove exception!
        }
    }

    public void put(K key, F firstValue, S secondValue, int index) {
        keysList.add(index, key);
        firstValuesList.add(index, firstValue);
        secondValuesList.add(index, secondValue);

        if (!validate()) {
            keysList.remove(index);
            firstValuesList.remove(index);
            secondValuesList.remove(index);
            throw new IllegalArgumentException("Tried to add already added key."); //TODO remove exception!
        }
    }

    public void remove(K key, F firstValue, S secondValue) {
        int index = keysList.indexOf(key);
        if (index != -1 && firstValuesList.get(index).equals(firstValue) && secondValuesList.get(index).equals(secondValue)) {
            keysList.remove(key);
            firstValuesList.remove(firstValue);
            secondValuesList.remove(secondValue);
        }
    }

    public void putAll(Triple<? extends K, ? extends F, ? extends S> t) {
        if (t instanceof ListTriple) {
            ListTriple<? extends K, ? extends F, ? extends S> triple = (ListTriple<? extends K, ? extends F, ? extends S>) t;

            for (int i = 0; i < triple.size(); i++) {
                K key = triple.getKey(i);
                F firstValue = triple.getFirstValue(i);
                S secondValue = triple.getSecondValue(i);

                put(key, firstValue, secondValue);
            }
        }
    }

    public void clear() {
        keysList.clear();
        firstValuesList.clear();
        secondValuesList.clear();
    }

    public boolean validate() {
        if (keysList.size() != firstValuesList.size() || keysList.size() != secondValuesList.size())
            return false;

        return true;
    }
}