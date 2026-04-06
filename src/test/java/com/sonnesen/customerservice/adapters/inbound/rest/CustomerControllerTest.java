package com.sonnesen.customerservice.adapters.inbound.rest;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonnesen.customerservice.adapters.inbound.rest.mapper.CustomerRestMapper;
import com.sonnesen.customerservice.adapters.inbound.rest.model.CustomerResponse;
import com.sonnesen.customerservice.application.usecase.create.CreateCustomerOutput;
import com.sonnesen.customerservice.application.usecase.get.CustomerOutput;
import com.sonnesen.customerservice.application.usecase.list.CustomerListOutput;
import com.sonnesen.customerservice.domain.exception.NotFoundException;
import com.sonnesen.customerservice.domain.model.CustomerId;
import com.sonnesen.customerservice.domain.model.Page;
import com.sonnesen.customerservice.domain.model.Pagination;
import com.sonnesen.customerservice.ports.inbound.activate.ActivateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.create.CreateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.deactivate.DeactivateCustomerUseCase;
import com.sonnesen.customerservice.ports.inbound.get.GetCustomerByIdUseCase;
import com.sonnesen.customerservice.ports.inbound.list.ListCustomersUseCase;
import com.sonnesen.customerservice.ports.inbound.update.UpdateCustomerUseCase;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

        @Autowired
        MockMvc mockMvc;

        @MockitoBean
        CreateCustomerUseCase createCustomerUseCase;

        @MockitoBean
        ActivateCustomerUseCase activateCustomerUseCase;

        @MockitoBean
        DeactivateCustomerUseCase deactivateCustomerUseCase;

        @MockitoBean
        UpdateCustomerUseCase updateCustomerUseCase;

        @MockitoBean
        ListCustomersUseCase listCustomersUseCase;

        @MockitoBean
        GetCustomerByIdUseCase getCustomerByIdUseCase;

        @MockitoBean
        CustomerRestMapper customerMapper;

        @Test
        void shouldReturnBadRequestWhenNameIsMissing() throws Exception {
                final var content = """
                                        {
                                            "email": "john.doe@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("name"))
                                .andExpect(jsonPath("$.invalid-fields[0].message").value("must not be null"));
        }

        @Test
        void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
                final var content = """
                                        {
                                            "name": "",
                                            "email": "john.doe@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("name"))
                                .andExpect(jsonPath("$.invalid-fields[0].message")
                                                .value("size must be between 1 and 255"));
        }

        @Test
        void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
                final var tooLongName = "John" + "n".repeat(256);
                final var content = """
                                        {
                                            "name": "%s",
                                            "email": "john.doe@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """.formatted(tooLongName);
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("name"))
                                .andExpect(jsonPath("$.invalid-fields[0].message")
                                                .value("size must be between 1 and 255"));
        }

        @Test
        void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {
                final var content = """
                                        {
                                            "name": "John Doe",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("email"))
                                .andExpect(jsonPath("$.invalid-fields[0].message").value("must not be null"));
        }

        @Test
        void shouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
                final var content = """
                                        {
                                            "name": "John Doe",
                                            "email": "",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("email"))
                                .andExpect(jsonPath("$.invalid-fields[0].message")
                                                .value("size must be between 1 and 255"));
        }

        @Test
        void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
                final var content = """
                                    {
                                        "name": "John Doe",
                                        "email": "invalid-email",
                                        "phoneNumber": "11999999999",
                                        "cpf": "12345678901"
                                    }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("One or more validation errors occurred"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"))
                                .andExpect(jsonPath("$.invalid-fields[0].name").value("email"))
                                .andExpect(jsonPath("$.invalid-fields[0].message")
                                                .value("must be a well-formed email address"));
        }

        @Test
        void shouldCreateCustomer() throws Exception {
                // Arrange
                final var customerId = 1L;
                final var output = new CreateCustomerOutput(customerId);

                when(createCustomerUseCase.execute(any())).thenReturn(output);

                // Act & Assert
                final var content = """
                                        {
                                            "name": "John Doe",
                                            "email": "john.doe@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(post("/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", containsString("/v1/customers/1")));
        }

        @Test
        void shouldActivateCustomer() throws Exception {
                // Arrange
                final var customerId = CustomerId.of(1L);

                doNothing().when(activateCustomerUseCase).execute(customerId);

                // Act & Assert
                mockMvc.perform(patch("/v1/customers/1/activate"))
                                .andDo(print())
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldDeactivateCustomer() throws Exception {
                // Arrange
                final var customerId = CustomerId.of(1L);

                doNothing().when(deactivateCustomerUseCase).execute(customerId);

                // Act & Assert
                mockMvc.perform(patch("/v1/customers/1/deactivate"))
                                .andDo(print())
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundExceptionWhenActivatingNonExistingCustomer() throws Exception {
                // Arrange
                final var customerId = CustomerId.of(999L);

                doThrow(NotFoundException.class).when(activateCustomerUseCase).execute(customerId);

                // Act & Assert
                mockMvc.perform(patch("/v1/customers/999/activate"))
                                .andDo(print())
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnNotFoundExceptionWhenDeactivatingNonExistingCustomer() throws Exception {
                // Arrange
                final var customerId = CustomerId.of(999L);

                doThrow(NotFoundException.class).when(deactivateCustomerUseCase).execute(customerId);

                // Act & Assert
                mockMvc.perform(patch("/v1/customers/999/deactivate"))
                                .andDo(print())
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnBadRequestWhenActivatingCustomerWithInvalidId() throws Exception {
                // Act & Assert
                mockMvc.perform(patch("/v1/customers/0/activate"))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail").value("Invalid customer ID: 0"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers/0/activate"))
                                .andExpect(jsonPath("$.title").value("Bad Request"));
        }

        @Test
        void shouldUpdateCustomer() throws Exception {
                // Arrange
                doNothing().when(updateCustomerUseCase).execute(any());

                // Act & Assert
                final var content = """
                                        {
                                            "name": "John Doe Updated",
                                            "email": "john.doe.updated@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(put("/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingCustomer() throws Exception {
                // Arrange
                doThrow(NotFoundException.class).when(updateCustomerUseCase).execute(any());

                // Act & Assert
                final var content = """
                                        {
                                            "name": "John Doe Updated",
                                            "email": "john.doe.updated@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "12345678901"
                                        }
                                """;
                mockMvc.perform(put("/v1/customers/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnBadRequestWhenUpdatingCustomerWithInvalidCpf() throws Exception {
                // Act & Assert
                final var content = """
                                        {
                                            "name": "John Doe Updated",
                                            "email": "john.doe.updated@mail.com",
                                            "phoneNumber": "11999999999",
                                            "cpf": "123456789012"
                                        }
                                """;
                mockMvc.perform(put("/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andDo(print())
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnOkWhenGettingAllCustomers() throws Exception {
                // Arrange
                final var currentPage = 0;
                final var perPage = 10;
                final var page = new Page(currentPage, perPage);
                final var now = Instant.now();
                final var customers = List.<CustomerListOutput>of(
                                new CustomerListOutput(1L, "John Doe", "john.doe@mail.com", "479999999999",
                                                "12345678900", true,
                                                now, now));
                final var paginatedCustomers = new Pagination<CustomerListOutput>(currentPage, perPage, 1L, customers);

                when(listCustomersUseCase.execute(page)).thenReturn(paginatedCustomers);

                // Act & Assert
                mockMvc.perform(get("/v1/customers"))
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        void shouldReturnOkWhenGettingAllCustomersWithPagination() throws Exception {
                // Arrange
                final var currentPage = 1;
                final var perPage = 5;
                final var page = new Page(currentPage, perPage);
                final var now = Instant.now();
                final var customers = List.<CustomerListOutput>of(
                                new CustomerListOutput(1L, "John Doe", "john.doe@mail.com", "479999999999",
                                                "12345678900", true,
                                                now, now));
                final var paginatedCustomers = new Pagination<CustomerListOutput>(currentPage, perPage, 1L, customers);

                when(listCustomersUseCase.execute(page)).thenReturn(paginatedCustomers);

                // Act & Assert
                mockMvc.perform(get("/v1/customers")
                                .param("page", String.valueOf(currentPage))
                                .param("perPage", String.valueOf(perPage)))
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        void shouldReturnBadRequestWhenGettingAllCustomersWithInvalidPagination() throws Exception {
                // Act & Assert
                mockMvc.perform(get("/v1/customers")
                                .param("page", "-1")
                                .param("perPage", "0"))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail")
                                                .value("Invalid page value: -1. It must be greater than or equal to 0"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"));
        }

        @Test
        void shouldReturnBadRequestWhenGettingAllCustomersWithInvalidPerPage() throws Exception {
                // Act & Assert
                mockMvc.perform(get("/v1/customers")
                                .param("page", "0")
                                .param("perPage", "-1"))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.detail")
                                                .value("Invalid per page value: -1. It must be greater than 0"))
                                .andExpect(jsonPath("$.instance").value("/v1/customers"))
                                .andExpect(jsonPath("$.title").value("Bad Request"));
        }

        @Test
        void shouldReturnOkWhenGettingCustomerById() throws Exception {
                // Arrange
                final var customerId = 1L;
                final var customerName = "John Doe";
                final var email = "john.doe@mail.com";
                final var phoneNumber = "47999999999";
                final var cpf = "12345678900";
                final var now = Instant.now();
                final var output = new CustomerOutput(customerId, customerName, email, phoneNumber, cpf, true, now,
                                now);

                when(getCustomerByIdUseCase.execute(CustomerId.of(1L))).thenReturn(output);

                final var response = new CustomerResponse()
                                .customerId(customerId)
                                .name(customerName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .cpf(cpf)
                                .active(true)
                                .createdAt(OffsetDateTime.ofInstant(now, ZoneId.systemDefault()))
                                .updatedAt(OffsetDateTime.ofInstant(now, ZoneId.systemDefault()));

                when(customerMapper.toResponse(output)).thenReturn(response);

                mockMvc.perform(get("/v1/customers/1"))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.customerId").value(customerId))
                                .andExpect(jsonPath("$.name").value(customerName))
                                .andExpect(jsonPath("$.email").value(email))
                                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber))
                                .andExpect(jsonPath("$.cpf").value(cpf))
                                .andExpect(jsonPath("$.active").value(true))
                                .andExpect(
                                                jsonPath("$.createdAt").value(OffsetDateTime
                                                                .ofInstant(now, ZoneId.systemDefault()).toString()))
                                .andExpect(jsonPath("$.updatedAt")
                                                .value(OffsetDateTime.ofInstant(now, ZoneId.systemDefault())
                                                                .toString()));
        }
}
