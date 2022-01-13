package com.laokema.javaweb.model;

import java.lang.reflect.Method;
import java.util.*;

public class BaseModel {
	public Map<String, Object> property = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		try {
			String getterName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
			Method getter = this.getClass().getMethod(getterName);
			return (T) getter.invoke(this);
		} catch (Exception e) {
			return (T) this.property.get(name);
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String name, Class<?> retType) {
		T ret = get(name);
		if (ret == null) {
			if (retType == Integer.class) {
				return (T) Integer.valueOf("0");
			} else if (retType == Long.class) {
				return (T) Long.valueOf("0");
			} else if (retType == Float.class) {
				return (T) Float.valueOf("0");
			} else if (retType == Double.class) {
				return (T) Double.valueOf("0");
			}
		}
		return ret;
	}

	public void set(String name, Object value) {
		if (value == null) {
			this.property.remove(name);
		} else {
			this.property.put(name, value);
		}
	}
}
