package ru.tinkoff.touristguide.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.touristguide.dto.RouteRequest;
import ru.tinkoff.touristguide.exception.NotValidRouteException;
import ru.tinkoff.touristguide.model.Sight;
import ru.tinkoff.touristguide.service.RouteService;
import ru.tinkoff.touristguide.util.ErrorUtil;

import javax.validation.Valid;
import java.time.OffsetTime;
import java.util.Map;

@RestController
@RequestMapping("/sights/route")
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @Operation(
            description = "Get recommended route by time, budget and favourite categories of place to visit",
            summary = "Get recommended route"
    )
    public Map<Pair<OffsetTime, OffsetTime>, Sight> getRoute(@Valid @RequestBody RouteRequest routeRequest, Errors errors) {
        if (errors.hasErrors()) {
            throw new NotValidRouteException(ErrorUtil.getExceptionMessage(errors));
        }

        return routeService.getRoute(routeRequest);
    }
}
