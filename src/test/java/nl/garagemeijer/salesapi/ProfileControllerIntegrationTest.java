package nl.garagemeijer.salesapi;

import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllProfiles() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNotEmpty());
    }

    @Test
    void shouldGetProfileById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mark"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void shouldCreateProfile() throws Exception {
        String requestJson = """
                {
                    "role": "SELLER",
                    "firstName" : "Puppy",
                    "lastName" : "Lover",
                    "dateOfBirth" : "1987-08-21",
                    "street" : "Bar street 8",
                    "postalCode" : "78965 ABN",
                    "city" : "Birmingham",
                    "country" : "Engeland",
                    "email" : "goodbye@hello.nl",
                    "phoneNumber" : "0612358791"
                }
                """;

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/profiles")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

    }

    @Test
    void shouldUpdateProfile() throws Exception {
        String requestJson = """
                {
                    "role": "ADMIN",
                    "firstName" : "Puppy",
                    "lastName" : "Lover",
                    "dateOfBirth" : "1987-08-21",
                    "street" : "Bar street 8",
                    "postalCode" : "78965 ABN",
                    "city" : "Birmingham",
                    "country" : "Engeland",
                    "email" : "goodbye@hello.nl",
                    "phoneNumber" : "0612358791"
                }
                """;
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/profiles/1")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Puppy"));
    }

    @Test
    void shouldDeleteProfile() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/profiles/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }
}
