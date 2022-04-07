package uz.pdp.market.dto.income;

import lombok.*;
import uz.pdp.market.dto.base.GenericDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeDto extends GenericDto {

    private String tittle;

    private String description;

    private double amount;

    private Long measurementId;

    private Double price;

    private Long currencyId;

}
