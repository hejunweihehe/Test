package com.hejunwei.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	private int id = 0;
	private static int count = 0;
	private ArrayList<String> strings = new ArrayList<>();
	private ArrayList<Integer> integers = new ArrayList<>();
	private ArrayList<Main> mains = new ArrayList<>();

	public Main() {
		count++;
		id = count;
	}

	public int getId() {
		return id;
	}

	public void setMains(ArrayList<Main> mains) {
		this.mains = mains;
	}

	public ArrayList<Main> getMains() {
		return mains;
	}

	public void setIntegers(ArrayList<Integer> integers) {
		this.integers = integers;
	}

	public List<Integer> getIntegers() {
		return integers;
	}

	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}

	public List<String> getStrings() {
		return strings;
	}

	public String f() {
		return "method f";
	}

	public String f2(int a) {
		return "method f2";
	}

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println(Arrays.toString(
				"toString,errorRaised,processingOver,getElementsAnnotatedWith,getElementsAnnotatedWith,getElementsAnnotatedWithAny,getElementsAnnotatedWithAny"
						.split(",")));
		Main main = new Main();
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("str1");
		arrayList.add("str2");
		arrayList.add("str3");
		main.setStrings(arrayList);

		ArrayList<Integer> integers = new ArrayList<>();
		integers.add(2);
		integers.add(200);
		integers.add(323);
		main.setIntegers(integers);

		ArrayList<Main> mains = new ArrayList<>();
		Main main2 = new Main();
		main2.setIntegers(integers);
		mains.add(main2);
		mains.add(new Main());
		mains.add(new Main());
		main.setMains(mains);
		System.out.println(ClassUtils.classToString(main, new String[] { "f", "f2" }));
	}

}
