package ro.dragos.predictor;

import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
	public Response getPredictedValues(@PathParam("website") String website, @QueryParam("callback") String callback) throws JSONException {
		RPredictor predictor = RPredictor.getInstance();

		boolean isScriptExecuted = predictor.executeRScript(website);
		if (isScriptExecuted == false) {
			String result = "{\"message\" : \"Failed executing script\"}";
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

		String jsonReturned = json.toString() + "";
		if (callback != null)
		{
			jsonReturned = callback + "(" + jsonReturned + ")";
		}
		
		return Response.status(200).entity(jsonReturned).build();
	}

	@Path("/websites")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWebsites(@QueryParam("callback") String callback) throws JSONException {
		String pathToFile = getClass().getClassLoader().getResource("websites").getFile();
		File file = new File(pathToFile);

		JSONArray json = new JSONArray();
		String[] websiteNames = file.list();
		for(String website : websiteNames)
		{
			json.put(website);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("model", json);
		String jsonReturned = jsonObj.toString() + "";
		if (callback != null)
		{
			jsonReturned = callback + "(" + jsonReturned + ")";
		}
		
		return Response.status(200).entity(jsonReturned).build();
	}
}