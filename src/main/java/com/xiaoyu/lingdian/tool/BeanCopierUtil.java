package com.xiaoyu.lingdian.tool;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.sf.cglib.beans.BeanCopier;

/**
 * utility class to copy objects by cglib
 */
public class BeanCopierUtil {

	/**
	 * bean copier cache using guava api
	 */
	private static final Cache<String, BeanCopier> BEAN_COPIER_CACHE = CacheBuilder
			.newBuilder()
			.maximumSize(1000)
			.expireAfterAccess(1, TimeUnit.DAYS)
			.build();

	/**
	 * cache key generator
	 * @param source source type
	 * @param target target type
	 * @return
	 */
	private static String generateKey(Class<?> source, Class<?> target) {

		return new StringBuilder().append(source).append(target).toString();

	}

	/**
	 * copy properties from source to target
	 * @param source source instance
	 * @param target target instance
	 */
	public static void copyProperties(Object target, Object source) {
		if (source == null || target == null) {
			throw new IllegalArgumentException("arguments required");
		}
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = BEAN_COPIER_CACHE.getIfPresent(beanKey);
		if (copier == null) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			BEAN_COPIER_CACHE.put(beanKey, copier);
		}

		copier.copy(source, target, null);
	}
}
