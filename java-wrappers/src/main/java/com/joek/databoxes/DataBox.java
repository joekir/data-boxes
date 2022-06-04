package com.joek.databoxes;

import manifold.ext.rt.api.ComparableUsing;

public class DataBox {
    public static class Box<T extends Number> implements ComparableUsing<Box<T>> {
        // Underlying raw data
        private T inner;

        private static Number addNumbers(Number a, Number b) {
            if (a instanceof Double || b instanceof Double) {
                return a.doubleValue() + b.doubleValue();
            } else if (a instanceof Float || b instanceof Float) {
                return a.floatValue() + b.floatValue();
            } else if (a instanceof Long || b instanceof Long) {
                return a.longValue() + b.longValue();
            } else {
                return a.intValue() + b.intValue();
            }
        }

        private static Number subtractNumbers(Number a, Number b) {
            if (a instanceof Double || b instanceof Double) {
                return a.doubleValue() - b.doubleValue();
            } else if (a instanceof Float || b instanceof Float) {
                return a.floatValue() - b.floatValue();
            } else if (a instanceof Long || b instanceof Long) {
                return a.longValue() - b.longValue();
            } else {
                return a.intValue() - b.intValue();
            }
        }

        public Box(T inner) {
            this.inner = inner;
        }

        public T getInner() {
            return this.inner;
        }

        // operator '+' overload
        // @SuppressWarnings("unused") TODO: re-add later
        public Box<T> plus(T that) {
            @SuppressWarnings("unchecked")
            Box<T> result = new Box<T>((T) addNumbers(this.inner, that));
            return result;
        }

        // operator '-' overload
        // @SuppressWarnings("unused") TODO: re-add later
        public Box<T> minus(int that) {
            @SuppressWarnings("unchecked")
            Box<T> result = new Box<T>((T) subtractNumbers(this.inner, that));
            return result;
        }

        @Override
        public int compareTo(Box that) {
            return (int) subtractNumbers(this.inner, that.inner);
        }

        @Override
        public EqualityMode equalityMode() {
            return EqualityMode.CompareTo;
        }
    }
}