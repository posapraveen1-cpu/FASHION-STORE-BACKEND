package com.fashionstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductJsonTest {
    @Test
    void exposesCategoryNameAsAStringForTheStorefront() throws Exception {
        Product product = new Product("Linen shirt", "Soft linen", new BigDecimal("1890.00"), null, new Category("women"));
        JsonNode json = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(product));

        assertEquals("women", json.get("category").asText());
        assertFalse(json.has("categoryEntity"));
    }
}
