package uz.pdp.market.dto.income;

import lombok.*;
import uz.pdp.market.dto.base.GenericDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeUpdateDto extends GenericDto {

    private String tittle;

    private String description;

    private double amount;

    private String measurementId;

    private Double price;

    private String currencyId;

}
