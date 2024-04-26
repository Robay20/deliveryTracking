package com.example.packettracer.utils;

import com.example.packettracer.Role;
import com.example.packettracer.model.Driver;
import com.example.packettracer.model.Location;
import com.example.packettracer.model.Packet;
import com.example.packettracer.model.Route;
import com.example.packettracer.model.Sender;
import com.example.packettracer.model.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class JsonParser {

    public static Set<Driver> parseDrivers(JSONArray driversArray) throws JSONException {
        Set<Driver> drivers = new HashSet<>();
        if (driversArray != null) {
            for (int i = 0; i < driversArray.length(); i++) {
                drivers.add(parseDriver(driversArray.getJSONObject(i)));
            }
        }
        return drivers;
    }

    public static Set<Packet> parsePackets(JSONArray packetsArray) throws JSONException {
        Set<Packet> packets = new HashSet<>();
        if (packetsArray != null) {
            for (int i = 0; i < packetsArray.length(); i++) {
                packets.add(parsePacket(packetsArray.getJSONObject(i)));
            }
        }
        return packets;
    }


    public static Set<Route> parseRoutes(JSONArray routesArray) throws JSONException {
        Set<Route> routes = new HashSet<>();
        if (routesArray != null) {

            for (int i = 0; i < routesArray.length(); i++) {
                JSONObject routeJson = routesArray.getJSONObject(i);
                routes.add(parseRoute(routeJson)); // Use the parseRoute method to parse each JSONObject
            }
        }
        return routes;
    }

    public static Driver parseDriver(JSONObject jsonObject) throws JSONException {
        // Parsing fields from the Person superclass
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        boolean isActive = jsonObject.getBoolean("active");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        String email = jsonObject.getString("email");
        Role role = Role.valueOf(jsonObject.getString("role"));
        LocalDate dateOfBirth = LocalDate.parse(jsonObject.getString("dateOfBirth"));

        // Parsing fields specific to Driver
        String cinDriver = jsonObject.getString("cinDriver");
        String licenseNumber = jsonObject.getString("licenseNumber");
        String licensePlate = jsonObject.getString("licensePlate");
        String brand = jsonObject.getString("brand");

        // Parsing sets of Packets and Routes
        Set<Packet> packets = parsePackets(jsonObject.optJSONArray("packets")); // Assumes a method parsePackets exists
        Set<Route> routes = parseRoutes(jsonObject.optJSONArray("routes")); // Assumes a method parseRoutes exists

        // Create and return a new Driver instance
        return new Driver(username, password, isActive, firstName, lastName, email, role, dateOfBirth,
                cinDriver, licenseNumber, licensePlate, brand, packets, routes);
    }

    public static Sender parseSender(JSONObject senderJson) throws JSONException {
        String username = senderJson.getString("username");
        String password = senderJson.getString("password");
        boolean isActive = senderJson.getBoolean("active");
        String firstName = senderJson.getString("firstName");
        String lastName = senderJson.getString("lastName");
        String email = senderJson.getString("email");
        Role role = Role.valueOf(senderJson.getString("role"));
        LocalDate dateOfBirth = LocalDate.parse(senderJson.getString("dateOfBirth"));
        String cinSender = senderJson.getString("cinSender");
        Set<Packet> packets = parsePackets(senderJson.optJSONArray("packets")); // Initialize an empty set or parse from JSON if included
        // If packets are detailed within the sender, you would need to parse them here.


        return new Sender(username, password, isActive, firstName, lastName, email, role, dateOfBirth, cinSender, packets);
    }
    public static Packet parsePacket(JSONObject packetJson) throws JSONException {
        Long cinPacket = packetJson.getLong("cinPacket");
        Sender sender = parseSender(packetJson.getJSONObject("sender"));
        Client client = parseClient(packetJson.getJSONObject("client"));
        Set<Driver> drivers = parseDrivers(packetJson.optJSONArray("drivers"));
        Set<LocalDateTime> timeChanges = parseTimeChanges(packetJson.getJSONArray("timeChanges"));
        Set<String> medicalPieces = parseMedicalPieces(packetJson.getJSONArray("medicalPieces"));
        String city = packetJson.getString("city");
        String status = packetJson.getString("status");
        String description = packetJson.getString("description");
        Route route = parseRoute(packetJson.getJSONObject("route"));

        return new Packet(cinPacket, sender, client, drivers, timeChanges, medicalPieces, city, status, description, route);
    }

    public static Route parseRoute(JSONObject routeJson) throws JSONException {
        Long idRouter = routeJson.getLong("idRouter");
        LocalDateTime startTime = LocalDateTime.parse(routeJson.getString("startTime"));
        Set<Location> waypoints = parseWaypoints(routeJson.getJSONArray("waypoints"));
        LocalDateTime endTimeEstimated = LocalDateTime.parse(routeJson.getString("endTimeEstimated"));
        Double distance =  routeJson.getDouble("distance");
        Double duration =  routeJson.getDouble("duration");
        Set<Driver> drivers = parseDrivers(routeJson.optJSONArray("drivers"));
        Set<Packet> packets = parsePackets(routeJson.optJSONArray("packets"));

        return new Route(idRouter, startTime, waypoints, endTimeEstimated, distance, duration, drivers, packets);
    }

    private static Set<Location> parseWaypoints(JSONArray waypointsArray) throws JSONException {
        Set<Location> waypoints = new HashSet<>();
        for (int i = 0; i < waypointsArray.length(); i++) {
            JSONObject waypointJson = waypointsArray.getJSONObject(i);
            Location location = new Location(
                     waypointJson.getDouble("latitude"),
                     waypointJson.getDouble("longitude")
            );
            waypoints.add(location);
        }
        return waypoints;
    }

    public static Set<LocalDateTime> parseTimeChanges(JSONArray timeChangesArray) throws JSONException {
        Set<LocalDateTime> timeChanges = new HashSet<>();
        for (int i = 0; i < timeChangesArray.length(); i++) {
            timeChanges.add(LocalDateTime.parse(timeChangesArray.getString(i)));
        }
        return timeChanges;
    }

    public static Set<String> parseMedicalPieces(JSONArray medicalPiecesArray) throws JSONException {
        Set<String> medicalPieces = new HashSet<>();
        for (int i = 0; i < medicalPiecesArray.length(); i++) {
            medicalPieces.add(medicalPiecesArray.getString(i));
        }
        return medicalPieces;
    }

    public static Client parseClient(JSONObject clientJson) throws JSONException {
        String username = clientJson.getString("username");
        String password = clientJson.getString("password");
        boolean isActive = clientJson.getBoolean("active");
        String firstName = clientJson.getString("firstName");
        String lastName = clientJson.getString("lastName");
        String email = clientJson.getString("email");
        Role role = Role.valueOf(clientJson.getString("role"));
        LocalDate dateOfBirth = LocalDate.parse(clientJson.getString("dateOfBirth"));
        String cinClient = clientJson.getString("cinClient");
        Location locationClient = parseLocation(clientJson.getJSONObject("location_Client"));
        Set<Packet> packets = parsePackets(clientJson.optJSONArray("packets"));

        return new Client(username, password, isActive, firstName, lastName, email, role, dateOfBirth, cinClient, locationClient, packets);
    }

    private static Location parseLocation(JSONObject locationJson) throws JSONException {
        double latitude = locationJson.getDouble("latitude");
        double longitude = locationJson.getDouble("longitude");
        return new Location(latitude, longitude); // Assuming constructor for Location takes latitude and longitude
    }
}
