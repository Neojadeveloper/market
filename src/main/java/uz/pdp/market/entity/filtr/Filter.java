package uz.pdp.market.entity.filtr;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Husanov Asliddin Sat,12:09,02/04/22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {

    private LocalDate date;

}