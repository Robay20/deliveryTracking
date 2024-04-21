package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Route;
import com.example.packettracerbase.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    @Override
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Route updateRoute(Long id, Route routeDetails) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + id));

        // Update route details here
        route.setStartTime(routeDetails.getStartTime());
        route.setWaypoints(routeDetails.getWaypoints());
        route.setEndTimeEstimated(routeDetails.getEndTimeEstimated());
        route.setDistance(routeDetails.getDistance());
        route.setDuration(routeDetails.getDuration());
        // Set other fields as needed

        return routeRepository.save(route);
    }

    @Override
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new EntityNotFoundException("Route not found with id: " + id);
        }
        routeRepository.deleteById(id);
    }
}
