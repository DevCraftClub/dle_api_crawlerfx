package de.maharder.dbcrawler.controller;

import com.google.gson.Gson;
import de.maharder.dbcrawler.variables.GeneratorAnswer;

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

	public FileController(String fileName, String outputPath, Object item) {
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

	public GeneratorAnswer exportJson() {
		try {
			File file = new File(new URL(String.format("%s/%s.json", getOutputPath(), getFileName())).getPath());
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
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public GeneratorAnswer exportFile() {
		try {
			File file = new File(new URL(String.format("%s/%s.json", getOutputPath(), getFileName())).getPath());
			GeneratorAnswer answer = new GeneratorAnswer();

			try (FileWriter writer = new FileWriter(file, false)) {
				writer.write(getItem().toString());
				answer.addMessage("Файл " + file.getName() + " был создан и сохранен");
			} catch (IOException e) {
				answer.setSuccess(false);
				answer.addMessage("Невозможно сохранить файл: " + file.getName());
			}

			return answer;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
