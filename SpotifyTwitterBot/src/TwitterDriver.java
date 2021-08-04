/**
 * Twitter Driver and Client
 * 
 * @author Ria Galanos
 * @author Tony Potter
 * Original idea by Ria Galanos, whose documentation and source can be found at
 * https://github.com/riagalanos/cs1-twitter
 * 
 **/
import twitter4j.TwitterException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONException;

/*
 * problems to be fixed
 * 
 * renewing the access token
 * how to keep the program running continuously
 * 
 */
   public class TwitterDriver
   {
      private static PrintStream consolePrint;
   
      public static void main (String []args) throws TwitterException, IOException
      {
         // set up classpath and properties file
             
         Twitterer twitBot = new Twitterer(consolePrint);
         SpotifyRequester req = new SpotifyRequester("https://api.spotify.com/v1/me/player/currently-playing", 
        		 "BQCyByhTdiBwp__A9uT93ap8pW_NxKWqg0hzI7PzUFISYVd5l5RNs9hy2kVclnpZeDXm6ZZ-ulHI7giT4httdZHawJ0BYPWdHzcWQnPlhZziTrQVeRX2QAUAViDYE-jlV4juFy2N-B38o9b5FzAz_gzPoZAZOA6KFeLlCyR02teQPhAG1Ck5-0vGV6-JuK6XbmlwXmCHr5fVOXy5OJtWKQcr0SstvVzZ1a3ufNjdZNCx8CEtPzvsuc9NDDkqDo4O4JEF8AHhX8O7jF0FPqiWCQnS");
         
         String message = "";
         String currentTrackID = "";
         HashMap<String, String> currentTrackInfo = new HashMap<String, String>();
         while (true)
         {
        	 currentTrackInfo = req.getTrackInfo();
        	 
	         if (currentTrackInfo != null && !currentTrackInfo.get("id").equals(currentTrackID))
	         {
	        	 message = "brent is currently listening to " +
	        			 currentTrackInfo.get("track_name") + " by " + 
	        			 currentTrackInfo.get("artists") + ". listen along at: " +
	        			 currentTrackInfo.get("link");
	        	 
	        	 //System.out.println(message);
	        	 twitBot.tweetOut(message);
	        	 currentTrackID = currentTrackInfo.get("id");
	        	 try {
					Thread.sleep(Long.parseLong(currentTrackInfo.get("duration")) / 2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	       	 }
         }
      
         
         
         

      }    
         
   }  
         
   