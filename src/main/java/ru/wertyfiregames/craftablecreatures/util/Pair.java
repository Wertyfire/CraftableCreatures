/**
 * File created on 17:27 29.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(Pair<? extends F, ? extends S> p) {
        first = p.getFirst();
        second = p.getSecond();
    }
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }
    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }
}