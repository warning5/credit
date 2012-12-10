package com.credit.base;

import java.io.Serializable;

public final class Pair<First, Second> implements Serializable {

	private static final long serialVersionUID = 7327428791690795105L;
    private final First first;
    private final Second second;

	public Pair(final First first, final Second second) {
		this.first = first;
		this.second = second;
	}
	
	public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<K, V>(key, value);
    }

	public First getFirst() {
		return first;
	}

	public Second getSecond() {
		return second;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        Pair other = (Pair) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }

    public String toString() {
		return "Pair [" + first + ':' + second + ']';
	}
}
