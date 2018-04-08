package com.xiaoyu.lingdian.core.mybatis.mapper;

/**
 * simple mapper using element type of sql as the final value type
 *
 */
public abstract class SimpleMapMapper<K, V> implements MapMapper<V, K, V> {

	@Override
	public V mapValue(V object, int rowNum) {
		return object;
	}
}
