package nl.garagemeijer.salesapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PurchaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllPurchases() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/purchases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNotEmpty());
    }

    @Test
    void shouldGetPurchaseById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/purchases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldCreatePurchase() throws Exception {
        String requestJson = """
                {
                    "supplier" : "Toyota Korea",
                    "purchasePriceIncl" : 35999,
                    "expectedDeliveryDate" : "2024-12-31",
                    "quantity" : 1,
                    "businessOrPrivate" : "PRIVATE"
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/purchases")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bpmPrice").value("3599.9"));
    }

    @Test
    void shouldUpdatePurchase() throws Exception {
        String requestJson = """
                {
                	"supplier": "Mazda Korea"
                	,"purchasePriceIncl": 25999
                	,"expectedDeliveryDate": "2025-12-31"
                	,"quantity": 2
                	,"businessOrPrivate": "PRIVATE"
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/purchases/1")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bpmPrice").value(2599.9))
                .andExpect(jsonPath("$.taxPrice").value(4512.27))
                .andExpect(jsonPath("$.purchasePriceEx").value(18886.83))
                .andExpect(jsonPath("$.businessOrPrivate").value("PRIVATE"));

    }

    @Test
    void shouldDeletePurchase() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/purchases/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAssignVehicleToPurchase() throws Exception {
        String requestJson = """
                {
                    "id" : 2
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/purchases/1/vehicle")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleOutput.id").value(2));
    }

    @Test
    void shouldAssignAdminToPurchase() throws Exception {
        String requestJson = """
                {
                    "id" : 1
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/purchases/1/admin")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminId").value(1));
    }
}
