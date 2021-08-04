import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SpotifyRequester {
	private String URL;
	private String accessToken;
	private HashMap<String, String> trackInfo;
	
	
	public SpotifyRequester(String URL, String token)
	{
		this.URL = URL;
		this.accessToken = token;
		trackInfo = new HashMap<String, String>();
	}
	
	public HashMap<String, String> getTrackInfo()
	{
		trackInfo.clear();
	
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(URL))
			.setHeader("Authorization", "Bearer " + accessToken)
			.build();
		String response = null;
		try {
			response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
					.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isJSONValid(response))
		{
			JSONObject object = new JSONObject(response);
			
			trackInfo.put("id", object.getJSONObject("item").getString("id"));
			trackInfo.put("track_name", object.getJSONObject("item").getString("name"));
			trackInfo.put("link", object.getJSONObject("item").getJSONObject("external_urls").getString("spotify"));
			trackInfo.put("duration", Long.toString(object.getJSONObject("item").getLong("duration_ms")));
			
			
			
			JSONArray jArtistArray = object.getJSONObject("item").getJSONArray("artists");
			String[] artists = new String[jArtistArray.length()];
			for (int i = 0; i < artists.length; i++)
			{
				artists[i] = jArtistArray.getJSONObject(i).getString("name");
			}
			String names = String.join(", ", artists);
			trackInfo.put("artists", names);
					
	
			return trackInfo;
		}
		else
		{
			return null;
		}
	}
	
	private boolean isJSONValid(String test)
	{
		try
		{
			new JSONObject(test);
		}
		catch (JSONException ex)
		{
			try {
				new JSONArray(test);
			}
			catch (JSONException ex2)
			{
				return false;
			}
		}
		
		return true;
	}

}
