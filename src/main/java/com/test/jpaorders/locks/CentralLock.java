package com.test.jpaorders.locks;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
public class CentralLock {
	private Set<Object> set = Collections.synchronizedSet(new HashSet<>());

	public synchronized boolean addlock(Object key) {
		if (set.contains(key))
			return false;
		return set.add(key);
	}

	public synchronized boolean removeLock(Object key) {
		return set.remove(key);
	}
}
