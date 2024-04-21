package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Route;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    List<Route> getAllRoutes();
    Optional<Route> getRouteById(Long id);
    Route createRoute(Route route);
    Route updateRoute(Long id, Route routeDetails);
    void deleteRoute(Long id);
}
