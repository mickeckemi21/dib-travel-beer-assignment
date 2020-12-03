package io.mickeckemi21.beerassignment.controller;

import io.mickeckemi21.beerassignment.client.PunkApiBeer;
import io.mickeckemi21.beerassignment.client.PunkApiClient;
import io.mickeckemi21.beerassignment.dto.BeerDto;
import io.mickeckemi21.beerassignment.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/beers")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;
    private final PunkApiClient<CompletableFuture<PunkApiBeer>> client;

    @GetMapping
    public Iterable<BeerDto> getAllBears() {
        return this.beerService.getAll();
    }

    @GetMapping("/{internalId}")
    public BeerDto getBeerById(@PathVariable String internalId) {
        return this.beerService.getByInternalId(internalId);
    }

    @DeleteMapping("/{internalId}")
    public BeerDto deleteBeerById(@PathVariable String internalId) {
        return this.beerService.deleteByInternalId(internalId);
    }

    @GetMapping("/fill")
    public Collection<BeerDto> fillBeersUpToTen() {
        return this.beerService.fill();
    }

}
