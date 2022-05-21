package com.joek.databoxes;

import manifold.ext.rt.api.ComparableUsing;

public class DataBox {
    public static class Box<T extends Number> implements ComparableUsing<Box<T>> {
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
        @SuppressWarnings("unused")
        public Box plus(T that) {
            return new Box(addNumbers(this.inner, that));
        }

        // operator '-' overload
        @SuppressWarnings("unused")
        public Box minus(int that) {
            return new Box(subtractNumbers(this.inner, that));
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