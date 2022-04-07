package uz.pdp.market.dto.inputProduct;

import lombok.*;
import uz.pdp.market.dto.base.Dto;
import uz.pdp.market.entity.market.Category;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputProductCreateDto implements Dto {

    private String name;

    private String imgPath;

    private Category category;

    private double amount;

    private long measurementId;

    private Double incomePrice;

    private Double outcomePrice;

    private Date expireDate;

    private long currencyId;

    private long marketId;
}
