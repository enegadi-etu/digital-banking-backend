package dev.enegadi.digitalbankingbackend.web;

import dev.enegadi.digitalbankingbackend.DTOs.CustomerDTO;
import dev.enegadi.digitalbankingbackend.entities.Customer;
import dev.enegadi.digitalbankingbackend.exepctions.CustomerNotFoundException;
import dev.enegadi.digitalbankingbackend.services.BankAccountService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> listCustomers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDTO getCustomer(@PathVariable(name = "customerId") Long id) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO cutomerDTO){
        return bankAccountService.saveCustomer(cutomerDTO);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable(name = "customerId") Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }

    @PatchMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody  CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
}


