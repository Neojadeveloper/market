package uz.pdp.market.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.pdp.market.dto.income.IncomeCreateDto;
import uz.pdp.market.dto.income.IncomeDto;
import uz.pdp.market.dto.income.IncomeUpdateDto;
import uz.pdp.market.entity.market.Currency;
import uz.pdp.market.entity.market.Income;
import uz.pdp.market.entity.market.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
@Component
public class IncomeMapper  implements BaseMapperI{
    public IncomeDto toDto(Income income) {

        if (Objects.isNull(income)) {
            return null;
        }

        IncomeDto incomeDto = IncomeDto.builder()
                .amount(income.getAmount())
                .measurementId(income.getMeasurement().getId())
                .currencyId(income.getCurrency().getId())
                .description(income.getDescription())
                .price(income.getPrice())
                .tittle(income.getTittle())
                .build();
        incomeDto.setId(income.getId());
        return incomeDto;
    }

    public List<IncomeDto> toDto(List<Income> e) {
        List<IncomeDto> list = new ArrayList<>();
        for (Income income : e) {
            list.add(toDto(income));
        }
        return list;
    }

    public Income fromCreateDto(IncomeCreateDto incomeCreateDto) {
        Currency currency = new Currency();
        currency.setId(Long.valueOf(incomeCreateDto.getCurrencyId()));
        Measurement measurement = new Measurement();
        measurement.setId(Long.valueOf(incomeCreateDto.getMeasurementId()));
        return Income.builder()
                .currency(currency)
                .measurement(measurement)
                .tittle(incomeCreateDto.getTittle())
                .amount(incomeCreateDto.getAmount())
                .price(incomeCreateDto.getPrice())
                .description(incomeCreateDto.getDescription())
                .build();
    }

    public Income fromUpdateDto(IncomeUpdateDto d) {
        Currency currency = new Currency();
        currency.setId(Long.valueOf(d.getCurrencyId()));
        Measurement measurement = new Measurement();
        measurement.setId(Long.valueOf(d.getMeasurementId()));
        Income build = Income.builder()
                .currency(currency)
                .measurement(measurement)
                .tittle(d.getTittle())
                .amount(d.getAmount())
                .price(d.getPrice())
                .description(d.getDescription())
                .build();
        build.setId(d.getId());
        return build;
    }

    public Income fromUpdateDto(IncomeUpdateDto d, Income income) {
        Currency currency = new Currency();
        currency.setId(Long.valueOf(d.getCurrencyId()));
        Measurement measurement = new Measurement();
        measurement.setId(Long.valueOf(d.getMeasurementId()));

        income.setId(d.getId());
        income.setCurrency(currency);
        income.setMeasurement(measurement);
        income.setTittle(d.getTittle());
        income.setAmount(d.getAmount());
        income.setPrice(d.getPrice());
        income.setDescription(d.getDescription());

        return income;
    }

}
