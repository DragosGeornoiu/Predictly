package ro.dragos.predictor;

import java.io.BufferedReader;
import java.io.File;
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
		String modelName = "w" + fileName + ".csv";
		String pathToTrainFolder = "";
		try
		{
			String pathToFile = getClass().getClassLoader().getResource(RPredictor.SCRIPT_NAME).getFile();
			File file = new File(pathToFile);

			pathToTrainFolder = file.getParent() + File.separator + "websites" + File.separator + "w" + fileName
					+ File.separator;
		} catch(Exception e)
		{
			//ignore exception
		}
		
		List<Pair<Integer, Integer>> parsedCsvItems = new ArrayList<Pair<Integer, Integer>>();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		String modelName = "w" + fileName + "test.csv";
		String pathToTrainFolder = "";
		try
		{
			String pathToFile = getClass().getClassLoader().getResource(RPredictor.SCRIPT_NAME).getFile();
			File file = new File(pathToFile);

			pathToTrainFolder = file.getParent() + File.separator + "websites" + File.separator + "w" + fileName
					+ File.separator;
		} catch(Exception e)
		{
			//ignore exception
		}
		
		List<Pair<Integer, Integer>> parsedCsvItems = new ArrayList<Pair<Integer, Integer>>();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		String parsedName = "w" + fileName + "pred.csv";
		String pathToParsedtFolder = "";
		try
		{
			String pathToFile = getClass().getClassLoader().getResource(RPredictor.SCRIPT_NAME).getFile();
			File file = new File(pathToFile);

			pathToParsedtFolder = file.getParent() + File.separator + "websites" + File.separator + "w" + fileName
					+ File.separator;
		} catch(Exception e)
		{
		}
		
		List<Integer> parsedCsvItems = new ArrayList<Integer>();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

	public static void main(String[] args) {

		Parser obj = Parser.getInstance();
		System.out.println(obj.parseTrainModel("41"));
		System.out.println(obj.parseTestModel("41"));
//		System.out.println(obj.parsePredictedValues("41"));
	}
}
