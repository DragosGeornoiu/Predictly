package ro.dragos.predictor;

import java.io.File;

public class RPredictor {
	// R must be installed on machine and path to Rscript.exe must be specified here
	private final String SCRIPT_EXEC_PATH = "C:/Program Files/R/R-3.2.3/bin/Rscript.exe";
	// R script used for predictions
	public static final String SCRIPT_NAME = "imp_predict.R";

	private static RPredictor predictor;

	private RPredictor() {
	}

	public static RPredictor getInstance() {
		if (predictor == null) {
			predictor = new RPredictor();
		}

		return predictor;
	}

	public boolean executeRScript(String website) {
		try {
			String modelFileName = website + ".csv";
			String testFileName = website + "test.csv";
			String predictionsFileName = website + "pred.csv";

			String pathToFile = getClass().getClassLoader().getResource(SCRIPT_NAME).getFile();
			File file = new File(pathToFile);

			String pathToModel = file.getParent() + "/" + "websites" + "/" + website
					+ "/";
			pathToModel = pathToModel.replace("\\", "/");
			Process child = Runtime.getRuntime().exec(SCRIPT_EXEC_PATH + " " + file.getAbsolutePath() + " "
					+ pathToModel + " " + modelFileName + " " + testFileName + " " + predictionsFileName);
			int code = child.waitFor();

			if (code == 0) {
				return true;
			}

		} catch (Exception e) {
			// ignore exception
		}

		return false;
	}

	public static void main(String[] args) {
		RPredictor predictor = RPredictor.getInstance();
		System.out.println(predictor.executeRScript("41"));
		
		Parser obj = Parser.getInstance();
		System.out.println(obj.parseTrainModel("41"));
		System.out.println(obj.parseTestModel("41"));
		System.out.println(obj.parsePredictedValues("41"));
	}
}