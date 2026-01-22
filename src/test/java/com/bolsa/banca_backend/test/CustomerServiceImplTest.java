package com.bolsa.banca_backend.test;



import com.bolsa.banca_backend.dto.CustomerCreateRequest;
import com.bolsa.banca_backend.dto.CustomerDto;
import com.bolsa.banca_backend.dto.CustomerResponse;
import com.bolsa.banca_backend.dto.CustomerUpdateRequest;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests reales (F5) para CustomerServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepo;

    @InjectMocks
    private CustomerServiceImpl service;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    void create_shouldSaveCustomerAndReturnResponse() {
        // Arrange
        CustomerCreateRequest req = new CustomerCreateRequest();
        req.setFullName("Jose Lema");
        req.setIdentification("0102030405");
        req.setAddress("Otavalo sn y principal");
        req.setPhone("098254785");
        req.setPassword("1234");
        req.setStatus(true);

        UUID newId = UUID.randomUUID();

        when(customerRepo.save(any(Customer.class))).thenAnswer(inv -> {
            Customer c = inv.getArgument(0);
            c.setId(newId);
            return c;
        });

        // Act
        CustomerResponse resp = service.create(req);

        // Assert
        assertNotNull(resp);
        assertEquals(newId, resp.getId());
        assertEquals("Jose Lema", resp.getFullName());
        assertEquals("0102030405", resp.getIdentification());
        assertEquals("Otavalo sn y principal", resp.getAddress());
        assertEquals("098254785", resp.getPhone());
        assertTrue(resp.getStatus());

        verify(customerRepo).save(customerCaptor.capture());
        Customer saved = customerCaptor.getValue();

        assertEquals("Jose Lema", saved.getFullName());
        assertEquals("0102030405", saved.getIdentification());
        assertEquals("Otavalo sn y principal", saved.getAddress());
        assertEquals("098254785", saved.getPhone());
        assertEquals("1234", saved.getPassword());
        assertTrue(saved.getStatus());
    }

    @Test
    void create_shouldDefaultStatusTrue_whenNull() {
        // Arrange
        CustomerCreateRequest req = new CustomerCreateRequest();
        req.setFullName("Ana");
        req.setIdentification("111");
        req.setAddress("Quito");
        req.setPhone("099");
        req.setPassword("1234");
        req.setStatus(null); // <- importante

        when(customerRepo.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CustomerResponse resp = service.create(req);

        // Assert
        assertNotNull(resp);
        // status debe quedar true por defecto (lo haces en el builder)
        verify(customerRepo).save(customerCaptor.capture());
        assertTrue(customerCaptor.getValue().getStatus());
    }

    @Test
    void update_shouldUpdateFieldsAndReturnResponse() {
        // Arrange
        UUID id = UUID.randomUUID();

        Customer existing = Customer.builder()
                .fullName("Viejo")
                .identification("old")
                .address("old")
                .phone("old")
                .password("old")
                .status(true)
                .build();
        existing.setId(id);

        CustomerUpdateRequest req = new CustomerUpdateRequest();
        req.setFullName("Nuevo Nombre");
        req.setIdentification("0102030405");
        req.setAddress("Nueva direccion");
        req.setPhone("0999999999");
        req.setPassword("abcd");
        req.setStatus(false);

        when(customerRepo.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepo.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CustomerResponse resp = service.update(id, req);

        // Assert
        assertNotNull(resp);
        assertEquals(id, resp.getId());
        assertEquals("Nuevo Nombre", resp.getFullName());
        assertEquals("0102030405", resp.getIdentification());
        assertEquals("Nueva direccion", resp.getAddress());
        assertEquals("0999999999", resp.getPhone());
        assertFalse(resp.getStatus());

        verify(customerRepo).save(customerCaptor.capture());
        Customer saved = customerCaptor.getValue();
        assertEquals("Nuevo Nombre", saved.getFullName());
        assertEquals("0102030405", saved.getIdentification());
        assertEquals("Nueva direccion", saved.getAddress());
        assertEquals("0999999999", saved.getPhone());
        assertEquals("abcd", saved.getPassword());
        assertFalse(saved.getStatus());
    }

    @Test
    void update_shouldThrow_whenCustomerNotFound() {
        UUID id = UUID.randomUUID();
        CustomerUpdateRequest req = new CustomerUpdateRequest();

        when(customerRepo.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.update(id, req));

        assertTrue(ex.getMessage().contains("Cliente no encontrado"));
        verify(customerRepo, never()).save(any());
    }

    @Test
    void getById_shouldReturnDto() {
        // Arrange
        UUID id = UUID.randomUUID();

        Customer existing = Customer.builder()
                .fullName("Jose")
                .identification("0102")
                .address("Otavalo")
                .phone("0982")
                .password("1234")
                .status(true)
                .build();
        existing.setId(id);

        when(customerRepo.findById(id)).thenReturn(Optional.of(existing));

        // Act
        CustomerDto dto = service.getById(id);

        // Assert
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Jose", dto.getFullName());
        assertEquals("0102", dto.getIdentification());
        assertEquals("Otavalo", dto.getAddress());
        assertEquals("0982", dto.getPhone());
        assertTrue(dto.getStatus());
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(customerRepo.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.getById(id));

        assertTrue(ex.getMessage().contains("Cliente no encontrado"));
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        // Arrange
        Customer c1 = Customer.builder()
                .fullName("A").identification("1").address("x").phone("p").password("pw").status(true)
                .build();
        c1.setId(UUID.randomUUID());

        Customer c2 = Customer.builder()
                .fullName("B").identification("2").address("y").phone("q").password("pw").status(false)
                .build();
        c2.setId(UUID.randomUUID());

        when(customerRepo.findAll()).thenReturn(List.of(c1, c2));

        // Act
        List<CustomerDto> dtos = service.getAll();

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("A", dtos.get(0).getFullName());
        assertEquals("B", dtos.get(1).getFullName());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        UUID id = UUID.randomUUID();
        when(customerRepo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(customerRepo).deleteById(id);
    }

    @Test
    void delete_shouldThrow_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(customerRepo.existsById(id)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.delete(id));

        assertTrue(ex.getMessage().contains("Cliente no encontrado"));
        verify(customerRepo, never()).deleteById(any());
    }
}

