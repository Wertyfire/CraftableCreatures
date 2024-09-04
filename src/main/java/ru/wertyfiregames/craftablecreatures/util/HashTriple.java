///**
// * File created on 15:50 03.09.2024 by Wertyfire
// */
//
//package ru.wertyfiregames.craftablecreatures.util;
//
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Objects;
//import java.util.Set;
//
//public class HashTriple<K, F, S> extends AbstractTriple<K, F, S>
//        implements Triple<K, F, S>, Cloneable, Serializable {
//    private static final long serialVersionUID = 9079832358813732204L;
//
//    static final int DEFAULT_INITIAL_CAPACITY  = 1 << 4;
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//    static final int TREEIFY_THRESHOLD = 8;
//    static final int UNTREEIFY_THRESHOLD = 6;
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//    static class Node<K, F, S> implements TripleEntry<K, F, S> {
//        final int hash;
//        final K key;
//        F firstValue;
//        S secondValue;
//        Node<K, F, S> next;
//
//        public Node(int hash, K key, F firstValue, S secondValue, Node<K, F, S> next) {
//            this.hash = hash;
//            this.key = key;
//            this.firstValue = firstValue;
//            this.secondValue = secondValue;
//            this.next = next;
//        }
//
//        public K getKey() {
//            return key;
//        }
//
//        public F getFirstValue() {
//            return firstValue;
//        }
//
//        public S getSecondValue() {
//            return secondValue;
//        }
//
//        public String toString() {
//            return key + "=" + firstValue + "," + secondValue;
//        }
//
//        public int hashCode() {
//            return Objects.hashCode(key) ^ Objects.hashCode(firstValue) ^ Objects.hashCode(secondValue);
//        }
//
//        public F setFirstValue(F firstValue) {
//            F oldValue = this.firstValue;
//            this.firstValue = firstValue;
//            return oldValue;
//        }
//
//        public S setSecondValue(S secondValue) {
//            S oldValue = this.secondValue;
//            this.secondValue = secondValue;
//            return oldValue;
//        }
//
//        public boolean equals(Object obj) {
//            if (obj == this) return true;
//            if (obj instanceof TripleEntry) {
//                TripleEntry<?, ?, ?> e = (TripleEntry<?, ?, ?>) obj;
//                return Objects.equals(key, e.getKey()) &&
//                        Objects.equals(firstValue, e.getFirstValue()) && Objects.equals(secondValue, e.getSecondValue());
//            }
//            return false;
//        }
//    }
//
//    static final int hash(Object key) {
//        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//    }
//
//    static Class<?> comparableClassFor(Object x) {
//        if (x instanceof Comparable) {
//            Class<?> c;
//            Type[] ts, as;
//            Type t;
//            ParameterizedType p;
//
//            if ((c = x.getClass()) == String.class)
//                return c;
//
//            ts = c.getGenericInterfaces();
//
//            for (Type type : ts) {
//                if (((t = type) instanceof ParameterizedType) &&
//                        ((p = (ParameterizedType) t).getRawType() ==
//                                Comparable.class) &&
//                        (as = p.getActualTypeArguments()) != null &&
//                        as.length == 1 && as[0] == c)
//                    return c;
//            }
//
//        }
//        return null;
//    }
//
//    @SuppressWarnings("all")
//    static int compareComparables(Class<?> kc, Object k, Object x) {
//        return (x == null || x.getClass() != kc ? 0 :
//                ((Comparable) k).compareTo(k));
//    }
//
//    static final int tableSizeFor(int cap) {
//        int n = cap - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }
//
//    transient Node<K, F, S>[] table;
//    transient Set<TripleEntry<K, F, S>> entrySet;
//    transient int size;
//    transient int modCount;
//    int threshold;
//    final float loadFactor;
//
//    public HashTriple(int initialCapacity, float loadFactor) {
//        if (initialCapacity < 0) throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
//        if (initialCapacity > MAXIMUM_CAPACITY)
//            initialCapacity = MAXIMUM_CAPACITY;
//        if (loadFactor <= 0 || Float.isNaN(loadFactor))
//            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
//        this.loadFactor = loadFactor;
//        threshold = tableSizeFor(initialCapacity);
//    }
//    public HashTriple(int initialCapacity) {
//        this(initialCapacity, DEFAULT_LOAD_FACTOR);
//    }
//    public HashTriple() {
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//    }
//    public HashTriple(Triple<? extends K, ? extends F, ? extends S> t) {
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//        putTripleEntries(t, false);
//    }
//}