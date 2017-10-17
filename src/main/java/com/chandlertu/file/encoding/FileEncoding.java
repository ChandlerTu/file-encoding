package com.chandlertu.file.encoding;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileEncoding {

	public static void main(String[] args) {
		Path inPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\ts-task-processing\\src\\test\\java");
		Path outPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\ts-task-processing\\src\\test\\java-utf8");

		try (Stream<Path> paths = Files.walk(inPath)) {
			paths.parallel().filter(path -> {
				return path.toString().endsWith(".java");
			}).forEach(filePath -> {
				try {
					System.out.println(filePath);

					byte[] bytes = Files.readAllBytes(filePath);
					String content = new String(bytes, "GBK");

					Path subPath = inPath.relativize(filePath);
					Path outFilePath = outPath.resolve(subPath);

					Files.createDirectories(outFilePath.getParent());
					Files.write(outFilePath, content.getBytes(StandardCharsets.UTF_8));
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
