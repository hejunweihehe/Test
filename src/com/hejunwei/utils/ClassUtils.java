package com.hejunwei.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类处理工具
 * 
 * @author hejunwei
 *
 */
public class ClassUtils {
	public static String classToString(Object obj, String... stringName)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return classToString(0, obj, stringName);
	}

	private static String classToString(int depth, Object obj, String... stringName)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clz = obj.getClass();
		// 如果是基本类型就直接输出了
		if (isDefaultType(clz)) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < depth; i++) {
				builder.append("\t");
			}
			return builder.append(obj.toString() + "\n").toString();
		} else {
			ArrayList<Method> restMethods = new ArrayList<>();
			StringBuilder builder = new StringBuilder();
			for (Method m : clz.getMethods()) {
				if ((m.getName().startsWith("get") && m.getParameterCount() == 0
						&& Modifier.isPublic(m.getModifiers()))
						|| (containMethods(m.getName(), stringName) && (m.getParameterCount() == 0))) {
					// 如果返回了容器，那么就做额外的处理
					Class<?> returnType = m.getReturnType();
					Object returnObj = m.invoke(obj);
					if (isListType(returnObj)) {
						// TODO
						// 遇到内部容器，如何控制输出哪些方法？（弄成界面形式就可以，先列出所有方法，包括list，然后可以手动选定）。现在暂时先默认使用stringName
						for (int i = 0; i < depth; i++) {
							builder.append("\t");
						}
						builder.append(m.getName() + ":\n");
						// 如果list中的泛型类与obj的类相同，并且如果使用了同一个共享list，那么可能就会让递归无法停止而让栈溢出。暂时不知如何解决。可以设置一个最大深度，控制递归的次数
						for (Object listObj : (List<?>) returnObj) {
							builder.append(classToString(depth + 1, listObj, stringName));
						}
					} else if (returnType == Map.class) {
					} else {
						for (int i = 0; i < depth; i++) {
							builder.append("\t");
						}
						builder.append(m.getName() + ":" + m.invoke(obj) + "\n");
					}
				} else {
					// 将没有打印的方法保存下来
					restMethods.add(m);
				}
			}
			for (int i = 0; i < depth; i++) {
				builder.append("\t");
			}
			builder.append("rest methods:" + Arrays.toString(restMethods.toArray()) + "\n");
			for (int i = 0; i < depth; i++) {
				builder.append("\t");
			}
			builder.append("----------\n");
			return builder.toString();
		}
	}

	/**
	 * 判断clz是否为默认处理的类型，默认处理即直接将该类对象输出，不再遍历。
	 * 
	 * 
	 * 
	 * @param clz
	 * @return
	 */
	private static boolean isDefaultType(Class<?> clz) {
		// 基本类型直接输出
		if (clz.isPrimitive()) {
			return true;
		}
		// 包装类型并不是基本类型，是属于引用类型，这个要注意。
		if (clz == Boolean.class) {
			return true;
		} else if (clz == Character.class) {
			return true;
		} else if (clz == Byte.class) {
			return true;

		} else if (clz == Short.class) {
			return true;

		} else if (clz == Integer.class) {
			return true;

		} else if (clz == Long.class) {
			return true;

		} else if (clz == Float.class) {
			return true;

		} else if (clz == Double.class) {
			return true;

		} else if (clz == Void.class) {
			return true;

		} else if (clz == String.class) {
			return true;

		} else {
			return false;
		}
	}

	private static boolean containMethods(String m, String... strings) {
		for (String tmp : strings) {
			if (m.equals(tmp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断clz是否是List及其子类
	 * 
	 * 直接用等号也可以判断，但是等号是不会自动判断父类和子类的，使用等号的时候，ArrayList不等于List
	 * 
	 * @param clz
	 * @return
	 */
	private static boolean isListType(Object obj) {
		try {
			List tmp = (List) obj;
		} catch (ClassCastException e) {
			return false;
		}
		return true;
	}
}
