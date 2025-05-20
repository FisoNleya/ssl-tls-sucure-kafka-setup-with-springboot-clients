package com.mc2.micro2.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('ROLE_STATISTIC_ACCESS')")
public class StatisticController {

    @PreAuthorize("hasRole('ROLE_STATISTIC_VIEW_VEHICLES') ")
    @GetMapping("/vehicles")
    public List<Object> getAllVehicles() {
        return List.of("some statistic for vehicle stats");
    }

    @PreAuthorize("hasRole('ROLE_STATISTIC_VIEW_TICKETS')")
    @GetMapping("/tickets")
    public List<Object> getAllTickets() {
        return List.of("some statistic for tickets stats");
    }

    @PreAuthorize("hasRole('ROLE_STATISTIC_VIEW_FINANCES')")
    @GetMapping("/finances")
    public List<Object> getAllFinances() {
        return List.of("some statistic for financial stats");
    }

}

