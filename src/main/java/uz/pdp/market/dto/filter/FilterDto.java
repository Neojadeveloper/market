package uz.pdp.market.dto.filter;

import lombok.*;
import uz.pdp.market.dto.base.Dto;

import java.time.LocalDate;

/**
 * @author Husanov Asliddin Sat,12:14,02/04/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDto implements Dto {

    private LocalDate date;
}
