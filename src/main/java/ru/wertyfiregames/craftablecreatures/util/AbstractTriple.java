/**
 * File created on 16:59 29.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractTriple<K, F, S> implements Triple<K, F, S> {
    protected AbstractTriple() {}

    public int size() {
        return entrySet().size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        Iterator<TripleEntry<K, F, S>> i = entrySet().iterator();
        if (key == null) {
            while (i.hasNext()) {
                TripleEntry<K, F, S> e = i.next();
                if (e.getKey() == null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                TripleEntry<K, F, S> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }

        return false;
    }

    //TODO getFirst and getSecond

    public Pair<F, S> put(K key, F firstValue, S secondValue) {
        throw new UnsupportedOperationException();
    }

    public Pair<F, S> remove(K key) {
        Iterator<TripleEntry<K, F, S>> i = entrySet().iterator();
        TripleEntry<K, F, S> correctEntry = null;
        if (key == null) {
            while (correctEntry == null && i.hasNext()) {
                TripleEntry<K, F, S> e = i.next();
                if (e.getKey() == null)
                    correctEntry = e;
            }
        } else {
            while (correctEntry == null && i.hasNext()) {
                TripleEntry<K, F, S> e = i.next();
                if (key.equals(e.getKey()))
                    correctEntry = e;
            }
        }

        F oldF = null;
        S oldS = null;
        if (correctEntry != null) {
            oldF = correctEntry.getFirstValue();
            oldS = correctEntry.getSecondValue();
            i.remove();
        }

        return new Pair<>(oldF, oldS);
    }

    public void putAll(Triple<? extends K, ? extends F, ? extends S> t) {
        for (TripleEntry<? extends K, ? extends F, ? extends S> e : t.entrySet())
            put(e.getKey(), e.getFirstValue(), e.getSecondValue());
    }

    public void clear() {
        entrySet().clear();
    }

    transient Set<K> keySet;
    transient Collection<Pair<F, S>> values;

    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<TripleEntry<K, F, S>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractTriple.this.size();
                }

                public boolean isEmpty() {
                    return AbstractTriple.this.isEmpty();
                }

                public void clear() {
                    AbstractTriple.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractTriple.this.containsKey(k);
                }
            };
            keySet = ks;
        }
        return ks;
    }

    public abstract Set<TripleEntry<K, F, S>> entrySet();

    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof Triple)) return false;
        Triple<?, ?, ?> t = (Triple<?, ?, ?>) obj;
        if (t.size() != size()) return false;

        try {
            for (TripleEntry<K, F, S> e : entrySet()) {
                K key = e.getKey();
                F first = e.getFirstValue();
                S second = e.getSecondValue();
                if (first != null && second != null) {
                    if ((!(t.getFirstValue(key, second) == null) ||
                            !(t.getSecondValue(key, first) == null)) && t.containsKey(key)) return false;
                    else {
                        if (!first.equals(t.getFirstValue(key, second)) || !second.equals(t.getSecondValue(key, first)))
                            return false;
                    }
                }
            }
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int h = 0;
        for (TripleEntry<K, F, S> entry : entrySet()) h += entry.hashCode();
        return h;
    }

    public String toString() {
        Iterator<TripleEntry<K, F, S>> i = entrySet().iterator();
        if (!i.hasNext()) return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ;) {
            TripleEntry<K, F, S> e = i.next();
            K key = e.getKey();
            F f = e.getFirstValue();
            S s = e.getSecondValue();
            sb.append(key == this ? "(this Triple)" : key);
            sb.append('=');
            sb.append(f == this ? "(this Triple)" : f);
            sb.append(",");
            sb.append(s == this ? "(this Triple)" : s);
            if (!i.hasNext()) return sb.append('}').toString();
            sb.append(", ");
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        AbstractTriple<?, ?, ?> result = (AbstractTriple<?, ?, ?>) super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }

    public static class SimpleTripleEntry<K, F, S> implements TripleEntry<K, F, S>, Serializable {
        private static final long serialVersionUID = 7241009617149547139L;

        private final K key;
        private F firstValue;
        private S secondValue;

        public SimpleTripleEntry(K key, F firstValue, S secondValue) {
            this.key = key;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }
        public SimpleTripleEntry(TripleEntry<? extends K, ? extends F, ? extends S> entry) {
            key = entry.getKey();
            firstValue = entry.getFirstValue();
            secondValue = entry.getSecondValue();
        }

        public K getKey() {
            return key;
        }
        public F getFirstValue() {
            return firstValue;
        }
        public S getSecondValue() {
            return secondValue;
        }

        public F setFirstValue(F firstValue) {
            F oldValue = this.firstValue;
            this.firstValue = firstValue;
            return oldValue;
        }
        public S setSecondValue(S secondValue) {
            S oldValue = this.secondValue;
            this.secondValue = secondValue;
            return oldValue;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TripleEntry)) return false;
            TripleEntry<?, ?, ?> e = (TripleEntry<?, ?, ?>) obj;
            return Objects.equals(key, e.getKey()) && Objects.equals(firstValue, e.getFirstValue()) && Objects.equals(secondValue, e.getSecondValue());
        }

        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^
                    (firstValue == null ? 0 : firstValue.hashCode()) ^
                    (secondValue == null ? 0 : secondValue.hashCode());
        }

        public String toString() {
            return key + "=" + firstValue + "," + secondValue;
        }
    }

    public static class SimpleImmutableTripleEntry<K, F, S> implements TripleEntry<K, F, S>, Serializable {
        private static final long serialVersionUID = 1326033304304688120L;

        private final K key;
        private final F firstValue;
        private final S secondValue;

        public SimpleImmutableTripleEntry(TripleEntry<K, F, S> entry) {
            key = entry.getKey();
            firstValue = entry.getFirstValue();
            secondValue = entry.getSecondValue();
        }
        public SimpleImmutableTripleEntry(K key, F firstValue, S secondValue) {
            this.key = key;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

        public K getKey() {
            return key;
        }
        public F getFirstValue() {
            return firstValue;
        }
        public S getSecondValue() {
            return secondValue;
        }

        public F setFirstValue(F f) {
            throw new UnsupportedOperationException();
        }
        public S setSecondValue(S s) {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TripleEntry)) return false;
            TripleEntry<?, ?, ?> e = (TripleEntry<?, ?, ?>) obj;
            return Objects.equals(key, e.getKey()) && Objects.equals(firstValue, e.getFirstValue()) && Objects.equals(secondValue, e.getSecondValue());
        }

        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^
                    (firstValue == null ? 0 : firstValue.hashCode()) ^
                    (secondValue == null ? 0 : secondValue.hashCode());
        }

        public String toString() {
            return key + "=" + firstValue + "," + secondValue;
        }
    }
}