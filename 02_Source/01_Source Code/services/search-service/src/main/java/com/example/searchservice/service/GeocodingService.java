package com.example.searchservice.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {
    private final String API_KEY = "AIzaSyBKMinEVDHup5ZWXP8QYoM7xXiin8r_4SM";
    
    public double[] getLatLngFromAddress(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address.replace(" ", "+")
                + "&key=" + API_KEY;
        System.out.println("URL EDITED: " + url);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        try {
            JSONObject json = new JSONObject(response);
            System.out.println("JSON RES: " + json);
            JSONObject location = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");
            return new double[] { location.getDouble("lat"), location.getDouble("lng") };
        } catch (Exception e) {
            return new double[] { 0, 0 };
        }
    }
} 