package ro.dragos.predictor;

import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gdata.util.common.base.Pair;

@Path("/get")
public class PredictlyRestService {

	@Path("/predicted/{website}")
	@GET
	@Produces("application/json")
	public Response getPredictedValues(@PathParam("website") String website) throws JSONException {
		RPredictor predictor = RPredictor.getInstance();

		boolean isScriptExecuted = predictor.executeRScript(website);
		if (isScriptExecuted == false) {
			String result = "@Produces(\"application/json\")" + "{\"message\" : \"Failed executing script\"}";
			return Response.status(200).entity(result).build();
		}

		Parser parser = Parser.getInstance();

		List<Pair<Integer, Integer>> modelData = parser.parseTrainModel(website);
		JSONObject jsonModel = new JSONObject();
		for (Pair<Integer, Integer> model : modelData) {
			jsonModel.put(model.getFirst() + "", model.getSecond() + "");
		}

		List<Integer> predictions = parser.parsePredictedValues(website);
		List<Pair<Integer, Integer>> testData = parser.parseTestModel(website);
		JSONObject jsonTest = new JSONObject();
		JSONObject jsonPredictions = new JSONObject();
		for (int i = 0; i < testData.size(); i++) {
			jsonTest.put(testData.get(i).getFirst() + "", testData.get(i).getSecond() + "");
			jsonPredictions.put(testData.get(i).getFirst() + "", predictions.get(i) + "");
		}

		JSONObject json = new JSONObject();
		json.put("model", jsonModel);
		json.put("test", jsonTest);
		json.put("predictions", jsonPredictions);

		String result = "@Produces(\"application/json\")" + json;
		return Response.status(200).entity(result).build();
	}

	@Path("/websites")
	@GET
	@Produces("application/json")
	public Response getWebsites() throws JSONException {
		String pathToFile = getClass().getClassLoader().getResource("websites").getFile();
		File file = new File(pathToFile);

		JSONArray json = new JSONArray();
		String[] websiteNames = file.list();
		for(String website : websiteNames)
		{
			json.put(website);
		}
		
		String result = "@Produces(\"application/json\")" + json;
		return Response.status(200).entity(result).build();
	}
}