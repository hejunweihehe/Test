package com.hejunwei.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 在指定的多个文件中，以行为匹配单位，搜索出它们之间相同的字符串行。
 * 
 * 
 * @author hjw
 * 
 */
public class FindSameLinesInFiles {
	public static void main(String[] args) {
		File[] files = new File[] {
				new File("D:/Test1.txt"),
				new File("D:/Test2.txt"),
				new File("D:/Test3.txt"),
		};
		List<String> lines = listSameLines(files);
		System.out.println("======================================");
		for (String line : lines) {
			System.out.println(line);
		}
	}

	/**
	 * 只搜索存在的文件，把不存在的文件输出即可。
	 * 
	 * @param files
	 *            文件列表
	 */
	public static List<String> listSameLines(File[] files) {
		List<String> lines = new ArrayList<String>();
		List<File> existfiles = checkFiles(files);
		String line;
		for (int i = 0; i < existfiles.size(); i++) {
			File f = existfiles.get(i);
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			BufferedReader o_bf = null;
			try {
				fileReader = new FileReader(f);
				bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					int count = 1;
					for (int j = i + 1; j < existfiles.size(); j++) {
						File o_f = existfiles.get(j);
						o_bf = new BufferedReader(new FileReader(o_f));
						String o_line;
						while ((o_line = o_bf.readLine()) != null) {
							if (line.trim().equals(o_line.trim())) {
								count++;
							}
						}
					}
					if (count == existfiles.size()) {
						lines.add(line);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (o_bf != null) {
					try {
						o_bf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return lines;
	}

	private static List<File> checkFiles(File[] files) {
		List<File> existFiles = new ArrayList<File>();
		for (File f : files) {
			if (f.exists()) {
				existFiles.add(f);
			} else {
				System.out.println("文件 : " + f.getAbsolutePath() + " 不存在.");
			}
		}
		return existFiles;
	}
}