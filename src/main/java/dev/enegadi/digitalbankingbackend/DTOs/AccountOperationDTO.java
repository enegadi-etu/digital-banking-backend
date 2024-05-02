package dev.enegadi.digitalbankingbackend.DTOs;

import dev.enegadi.digitalbankingbackend.enums.OperationDate;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationDate type;
    private String description;
}
