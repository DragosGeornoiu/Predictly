package ro.dragos.predictor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.util.common.base.Pair;

public class Parser {
	private static Parser parser;

	private Parser() {
	}

	public static Parser getInstance() {
		if (parser == null) {
			parser = new Parser();
		}
		return parser;
	}

	public List<Pair<Integer, Integer>> parseTrainModel(String fileName) {
		List<Pair<Integer, Integer>> parsedCsvItems = new ArrayList<Pair<Integer, Integer>>();
		
		String modelName = fileName + ".csv";
		String pathToTrainFolder = getClass().getClassLoader()
				.getResource("websites" + "/" + fileName + "/").getFile();

		if(pathToTrainFolder == null || pathToTrainFolder.trim().isEmpty())
			return parsedCsvItems;
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(pathToTrainFolder + modelName));
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(cvsSplitBy);
				try {
					Integer date = new Integer(csv[1].replace("\"", ""));
					Integer value = new Integer(csv[9].replace("\"", ""));
					parsedCsvItems.add(new Pair<Integer, Integer>(date, value));
				} catch (Exception e) {
					// ignore exception
				}
			}
		} catch (FileNotFoundException e) {
			// ignore exception
		} catch (IOException e) {
			// ignore exception
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// ignore exception
				}
			}
		}
		return parsedCsvItems;
	}

	public List<Pair<Integer, Integer>> parseTestModel(String fileName) {
		List<Pair<Integer, Integer>> parsedCsvItems = new ArrayList<Pair<Integer, Integer>>();
		
		String modelName = fileName + "test.csv";
		String pathToTrainFolder = getClass().getClassLoader()
				.getResource("websites" + "/" + fileName + "/").getFile();

		if(pathToTrainFolder == null || pathToTrainFolder.trim().isEmpty())
			return parsedCsvItems;
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(pathToTrainFolder + modelName));
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(cvsSplitBy);
				try {
					Integer date = new Integer(csv[1].replace("\"", ""));
					Integer value = new Integer(csv[9].replace("\"", ""));
					parsedCsvItems.add(new Pair<Integer, Integer>(date, value));
				} catch (Exception e) {
					// ignore exception
				}
			}
		} catch (FileNotFoundException e) {
			// ignore exception
		} catch (IOException e) {
			// ignore exception
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// ignore exception
				}
			}
		}
		return parsedCsvItems;
	}

	public List<Integer> parsePredictedValues(String fileName) {
		List<Integer> parsedCsvItems = new ArrayList<Integer>();
		
		String parsedName = fileName + "pred.csv";
		String pathToParsedtFolder = getClass().getClassLoader()
				.getResource("websites" + "/" + fileName + "/").getFile();

		if(pathToParsedtFolder == null || pathToParsedtFolder.trim().isEmpty())
			return parsedCsvItems; 
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(pathToParsedtFolder + parsedName));
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(cvsSplitBy);
				try {
					Integer value = new Integer(csv[1].replace("\"", ""));
					parsedCsvItems.add(value);
				} catch (Exception e) {
					// ignore exception
				}
			}
		} catch (FileNotFoundException e) {
			// ignore exception
		} catch (IOException e) {
			// ignore exception
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// ignore exception
				}
			}
		}

		return parsedCsvItems;
	}
}
