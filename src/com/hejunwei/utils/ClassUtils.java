package com.hejunwei.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
		StringBuilder builder = new StringBuilder();
		for (Method m : clz.getMethods()) {
			if (m.getName().startsWith("get") && m.getParameterCount() == 0 && Modifier.isPublic(m.getModifiers())) {
				// 如果返回了容器，那么就做额外的处理
				Class<?> returnType = m.getReturnType();
				if (returnType == List.class) {
					// TODO
					// 遇到内部容器，如何控制输出哪些方法？（弄成界面形式就可以，先列出所有方法，包括list，然后可以手动选定）。现在暂时先默认使用stringName
					List<?> list = (List<?>) m.invoke(obj);
					String listContent = m.getName() + ":\n";
					// 如果list中的泛型类与obj的类相同，并且如果使用了同一个共享list，那么可能就会让递归无法停止而让栈溢出。暂时不知如何解决。可以设置一个最大深度，控制递归的次数
					for (Object listObj : list) {
						if(listObj.getClass().isPrimitive()) {
							
						}
						listContent += classToString(depth + 1, listObj, stringName);
						builder.append(listContent);
					}
				} else if (returnType == Map.class) {
				} else if (returnType == String.class) {
					for (int i = 0; i < depth; i++) {
						builder.append("\t");
					}
					builder.append(m.getName() + ":" + m.invoke(obj) + "\n");
				} else {
					for (int i = 0; i < depth; i++) {
						builder.append("\t");
					}
					builder.append(m.getName() + ":" + m.invoke(obj) + "\n");
				}
			}
		}
		return builder.toString();
	}

}
