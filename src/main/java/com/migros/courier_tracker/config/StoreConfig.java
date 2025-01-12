package com.migros.courier_tracker.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.courier_tracker.service.dto.StoreDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Configuration
public class StoreConfig {

    @Bean
    @Cacheable("storeListCache")
    public List<StoreDTO> storeList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Locate and read the 'stores.json' file from the resources folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/stores.json");
        if (inputStream == null) {
            throw new IOException("The stores.json file could not be found in the 'resources/data/' folder");
        }

        // Deserialize the JSON file into a list of StoreDTO objects

        return objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
        }).stream().map(data -> {
            String name = (String) data.get("name");
            double latitude = (double) data.get("lat");
            double longitude = (double) data.get("lng");
            Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
            point.setSRID(4326);
            StoreDTO store = new StoreDTO();
            store.setName(name);
            store.setLocation(point);
            return store;
        }).toList();
    }
}