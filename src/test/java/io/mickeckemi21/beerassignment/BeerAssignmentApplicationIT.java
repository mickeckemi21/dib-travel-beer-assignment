package io.mickeckemi21.beerassignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mickeckemi21.beerassignment.client.PunkApiBeer;
import io.mickeckemi21.beerassignment.controller.BeerController;
import io.mickeckemi21.beerassignment.dto.BeerDto;
import io.mickeckemi21.beerassignment.repository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerAssignmentApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeerRepository beerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenTenBeersInDatabase_whenGetAllBeers_thenBeerArraySizeEqualsToTen() throws Exception {
        // given
        final int arraySize = 10;

        // when
        final ResultActions getAllBeersResult =
                this.mockMvc.perform(get("/beers")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        getAllBeersResult
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(notNullValue())))
                .andExpect(jsonPath("$.length()", equalTo(arraySize)));
    }

    @Test
    void givenTenBeersInDatabase_whenGetBeerByInternalId_thenBeerNotNullAndBeerInternalIdEqualsToRequested() throws Exception {
        // given
        final MvcResult getAllBeersMvcResult = this.mockMvc.perform(
                get("/beers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final MockHttpServletResponse getAllBeersResponse = getAllBeersMvcResult.getResponse();
        final Collection<BeerDto> beerDtoCollection =
                this.objectMapper.readValue(getAllBeersResponse
                        .getContentAsString(), new TypeReference<Collection<BeerDto>>() {});

        assertNotNull(beerDtoCollection);
        assertEquals(10, beerDtoCollection.size());

        final BeerDto beerDto = beerDtoCollection.iterator().next();

        // when
        final ResultActions getBeerByInternalIdResult =
                this.mockMvc.perform(get(format("/beers/%s", beerDto.getInternalId()))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        getBeerByInternalIdResult
                .andExpect(jsonPath("$", is(notNullValue())))
                .andExpect(jsonPath("$.name", equalTo(beerDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(beerDto.getDescription())))
                .andExpect(jsonPath("$.internalId", equalTo(beerDto.getInternalId())))
                .andExpect(jsonPath("$.averageTemperature", equalTo(beerDto.getAverageTemperature())));
    }

    @Test
    @Transactional
    void givenTenBeersInDatabase_whenDeleteOneByInternalId_thenBeerArraySizeEqualsToNine() throws Exception {
        // given
        final MvcResult getAllBeersMvcResult =
                this.mockMvc.perform(get("/beers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final MockHttpServletResponse getAllBeersResponse = getAllBeersMvcResult.getResponse();
        final Collection<BeerDto> beerDtoCollection =
                this.objectMapper.readValue(getAllBeersResponse
                        .getContentAsString(), new TypeReference<Collection<BeerDto>>() {});

        assertNotNull(beerDtoCollection);
        assertEquals(10, beerDtoCollection.size());

        final BeerDto beerDto = beerDtoCollection.iterator().next();

        // when
        this.mockMvc.perform(delete(format("/beers/%s", beerDto.getInternalId()))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        final ResultActions getAllBeersResult =
                this.mockMvc.perform(get("/beers")
                        .contentType(MediaType.APPLICATION_JSON));

        getAllBeersResult
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(notNullValue())))
                .andExpect(jsonPath("$.length()", equalTo(9)));
    }

    @Test
    @Transactional
    void givenTenBeersInDatabase_whenDeleteTwoByInternalIdAndHitFillEndpoint_thenBeerArraySizeEqualsToTen() throws Exception {
        // given
        final MvcResult getAllBeersMvcResult =
                this.mockMvc.perform(get("/beers")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        final MockHttpServletResponse getAllBeersResponse = getAllBeersMvcResult.getResponse();
        final Collection<BeerDto> beerDtoCollection =
                this.objectMapper.readValue(getAllBeersResponse
                        .getContentAsString(), new TypeReference<Collection<BeerDto>>() {});

        assertNotNull(beerDtoCollection);
        assertEquals(10, beerDtoCollection.size());

        final Iterator<BeerDto> beerDtoIterator = beerDtoCollection.iterator();
        final BeerDto beerDto1 = beerDtoIterator.next();
        final BeerDto beerDto2 = beerDtoIterator.next();

        // when
        this.mockMvc.perform(delete(format("/beers/%s", beerDto1.getInternalId()))
                .contentType(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(delete(format("/beers/%s", beerDto2.getInternalId()))
                .contentType(MediaType.APPLICATION_JSON));

        final ResultActions beersFillResult = this.mockMvc.perform(get("/beers/fill")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        beersFillResult
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(notNullValue())))
                .andExpect(jsonPath("$.length()", equalTo(10)));
    }

}
