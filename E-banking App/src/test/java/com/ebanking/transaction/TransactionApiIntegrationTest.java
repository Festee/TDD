package com.ebanking.transaction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionApiIntegrationTest {
    private static final String BASE_URL = "/api/transactions";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsTransaction() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(transactionJson("create-check", "DEPOSIT", "250.00", "Salary")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.accountId").value("create-check"))
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(250.00))
                .andExpect(jsonPath("$.description").value("Salary"));
    }

    @Test
    void returnsAllTransactions() throws Exception {
        String accountId = "list-" + UUID.randomUUID();
        createTransaction(accountId, "DEPOSIT", "120.00", "List test");

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].accountId", hasItem(accountId)));
    }

    @Test
    void returnsTransactionById() throws Exception {
        long id = createTransaction("get-check", "WITHDRAWAL", "15.50", "ATM");

        mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.amount").value(15.50));
    }

    @Test
    void updatesTransaction() throws Exception {
        long id = createTransaction("update-check", "DEPOSIT", "20.00", "Initial");

        mockMvc.perform(put(BASE_URL + "/{id}", id)
                        .contentType("application/json")
                        .content(transactionJson("update-check", "TRANSFER", "45.25", "Updated")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.type").value("TRANSFER"))
                .andExpect(jsonPath("$.amount").value(45.25))
                .andExpect(jsonPath("$.description").value("Updated"));
    }

    @Test
    void deletesTransaction() throws Exception {
        long id = createTransaction("delete-check", "WITHDRAWAL", "10.00", "Delete me");

        mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsNegativeTransactionAmount() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(transactionJson("invalid-check", "DEPOSIT", "-1.00", "Invalid")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnsNotFoundForUnknownTransaction() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    private long createTransaction(String accountId, String type, String amount, String description)
            throws Exception {
        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(transactionJson(accountId, type, amount, description)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
        return response.get("id").asLong();
    }

    private String transactionJson(String accountId, String type, String amount, String description) {
        return """
                {"accountId":"%s","type":"%s","amount":%s,"description":"%s"}
                """.formatted(accountId, type, new BigDecimal(amount).toPlainString(), description);
    }
}
