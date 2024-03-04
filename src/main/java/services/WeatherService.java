package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherService {

    Reservation_Service rs = new Reservation_Service();


    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    public void weather(String location) throws UnsupportedEncodingException {
        String API_KEY = "";
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + encodedLocation + "&appid=" + API_KEY + "&units=metric";
        System.out.println(urlString);
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            System.out.println(result);
            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            double temperature = Double.parseDouble(mainMap.get("temp").toString());
            double humidity = Double.parseDouble(mainMap.get("humidity").toString());
            double windSpeed = Double.parseDouble(windMap.get("speed").toString());
            if (isWeatherBadForFootball(temperature, humidity, windSpeed)) {
                List<Reservation> list = rs.GetByLocation(location);
                // Take action if weather is bad for football game
                for(int i = 0 ; i<list.size();i++){
                    System.out.println("teeeeeeeeeeest");
                    MailSender.sendEmail("mouadhnemri04@gmail.com","Annulation de reservation","Votre reservation est annulé a cause de mauvais climat");
                    rs.delete(list.get(i));
                }
            }
            System.out.println("Current Temperature: " + temperature);
            System.out.println("Current Humidity: " + humidity);
            System.out.println("Wind Speeds: " + windSpeed);
            System.out.println("Wind Angle: " + windMap.get("deg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isWeatherBadForFootball(double temperature, double humidity, double windSpeed) {
        return temperature < 12 || humidity > 80 || windSpeed > 20;
    }

}