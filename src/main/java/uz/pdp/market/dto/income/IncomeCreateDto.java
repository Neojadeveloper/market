package uz.pdp.market.dto.income;

import lombok.*;
import uz.pdp.market.dto.base.Dto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeCreateDto implements Dto {

    private String tittle;

    private String description;

    private double amount;

    private String measurementId;

    private Double price;

    private String currencyId;

}
