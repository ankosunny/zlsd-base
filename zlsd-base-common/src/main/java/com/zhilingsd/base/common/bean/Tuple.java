package com.mujin.entity.consigner;

/**
 * 二元对象
 * @author Administrator
 *
 * @param <T>
 * @param <K>
 */
public class Tuple<T,K> {

	private T t;
	
	private K k;

	public Tuple() {
	}

	public Tuple(T t, K k) {
		this.t = t;
		this.k = k;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}
	
}
