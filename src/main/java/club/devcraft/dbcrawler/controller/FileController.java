package club.devcraft.dbcrawler.controller;

import com.google.gson.Gson;
import club.devcraft.dbcrawler.variables.GeneratorAnswer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileController {
	private String fileName;
	private String outputPath;
	private Object item;

	public FileController(String outputPath, String fileName, Object item) {
		setItem(item);
		setFileName(fileName);
		setOutputPath(outputPath);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	private File getPath(String path, String fileName) {

		File folder = new File(path);
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				throw new RuntimeException("Невозможно создать папку: " + folder.getPath());
			}
		}
		File file = new File(String.format("%s/%s", path, fileName));
		try {
			if (!file.exists()) {
				if(!file.createNewFile()) {
					throw new RuntimeException("Невозможно создать файл: " + file.getPath());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return file;
	}

	public GeneratorAnswer exportJson() {
		File file = getPath(getOutputPath(), String.format("%s.json",  getFileName()));
		GeneratorAnswer answer = new GeneratorAnswer();

		try (FileWriter writer = new FileWriter(file, false); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
			Gson gson = new Gson();
			String json = gson.toJson(getItem());
			bufferedWriter.write(json);
			answer.addMessage("Файл " + file.getName() + " был создан и сохранен");
		} catch (IOException e) {
			answer.setSuccess(false);
			answer.addMessage("Невозможно сохранить файл: " + file.getName());
		}

		return answer;
	}

	public GeneratorAnswer exportFile() {
		File file = getPath(getOutputPath(), getFileName());
		GeneratorAnswer answer = new GeneratorAnswer();

		try (FileWriter writer = new FileWriter(file, false)) {
			writer.write(getItem().toString());
			answer.addMessage("Файл " + file.getName() + " был создан и сохранен");
		} catch (IOException e) {
			answer.setSuccess(false);
			answer.addMessage("Невозможно сохранить файл: " + file.getName());
		}

		return answer;
	}
}
