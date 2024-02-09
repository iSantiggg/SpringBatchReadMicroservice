package com.spring.batch.config;

import java.io.IOException;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonItemReader<T> implements ItemReader<T> {
    
	private final String url;
    private final Class<T> targetType;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private List<T> items;
    private int currentIndex = 0;

    public CustomJsonItemReader(String url, Class<T> targetType) {
        this.url = url;
        this.targetType = targetType;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public T read() throws IOException {
    	 if (items == null) {
             fetchDataFromUrl();
         }
         
         if (currentIndex < items.size()) {
             return items.get(currentIndex++);
         } else {
             return null;
         }
    }

    private void fetchDataFromUrl() throws IOException {
        String json = restTemplate.getForObject(url, String.class);
        items = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, targetType));
    }
}