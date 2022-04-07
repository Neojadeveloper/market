package uz.pdp.market.service.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.IncomeCriteria.IncomeCriteria;
import uz.pdp.market.dto.filter.FilterDto;
import uz.pdp.market.dto.income.IncomeCreateDto;
import uz.pdp.market.dto.income.IncomeDto;
import uz.pdp.market.dto.income.IncomeUpdateDto;
import uz.pdp.market.dto.response.AppErrorDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Income;
import uz.pdp.market.mapper.IncomeMapper;
import uz.pdp.market.repository.IncomeRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.income.IncomeValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService extends AbstractService<
        IncomeRepository,
        IncomeMapper,
        IncomeValidator> implements GenericCrudService<
        Income,
        IncomeDto,
        IncomeCreateDto,
        IncomeUpdateDto,
        IncomeCriteria,
        Long> {

    @Autowired
    protected IncomeService(IncomeRepository repository, @Qualifier("incomeMapper") IncomeMapper mapper, IncomeValidator validator) {
        super(repository, mapper, validator);
    }


    @Override
    public ResponseEntity<DataDto<Long>> create(IncomeCreateDto createDto) {
        Income income = mapper.fromCreateDto(createDto);
        repository.save(income);
        return new ResponseEntity<>(new DataDto<>(income.getId()), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        repository.delete(repository.getById(id));
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);

    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(IncomeUpdateDto updateDto) {
        Optional<Income> optionalIncome = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (optionalIncome.isEmpty()) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("OutputProduct not found by id : '%s'".formatted(updateDto.getId()))
                    .build()
            ), HttpStatus.CONFLICT);
        }

        Income income = mapper.fromUpdateDto(updateDto, optionalIncome.get());
        repository.save(income);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity<DataDto<List<IncomeDto>>> getAll() {
        List<Income> incomeList = repository.findAllByDeletedFalse();
        List<IncomeDto> incomeDtos = mapper.toDto(incomeList);
        return new ResponseEntity<>(new DataDto<>(incomeDtos), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<DataDto<IncomeDto>> get(Long id) {
        Optional<Income> income = repository.findByIdAndDeletedFalse(id);
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(income.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(IncomeCriteria criteria) {
        return null;
    }

    public ResponseEntity<DataDto<List<IncomeDto>>> getDaily(FilterDto filterDto) {
        List<Income> incomeDaily = new ArrayList<>();
        for (Income income : repository.findAllByDeletedFalse()) {
            if (income.getCreatedAt().toLocalDate().equals(filterDto.getDate()))
                incomeDaily.add(income);
        }
        List<IncomeDto> incomeDtos = mapper.toDto(incomeDaily);
        return new ResponseEntity<>(new DataDto<>(incomeDtos), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<IncomeDto>>> getWeekly(FilterDto filterDto) {
        List<Income> incomeWeekly = new ArrayList<>();
        for (Income income : repository.findAllByDeletedFalse()) {
            for (int i = 1; i <= filterDto.getDate().getDayOfWeek().getValue(); i++) {
                if (income.getCreatedAt().getDayOfWeek().getValue() == i) {
                    incomeWeekly.add(income);
                }
            }
        }
        List<IncomeDto> incomeDtos = mapper.toDto(incomeWeekly);
        return new ResponseEntity<>(new DataDto<>(incomeDtos), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<IncomeDto>>> getMonthly(FilterDto filterDto) {
        List<Income> incomeMonthly = new ArrayList<>();
        for (Income income : repository.findAllByDeletedFalse()) {
            if (income.getCreatedAt().getDayOfMonth()==filterDto.getDate().getDayOfMonth())
                incomeMonthly.add(income);
        }
        List<IncomeDto> incomeDtos = mapper.toDto(incomeMonthly);
        return new ResponseEntity<>(new DataDto<>(incomeDtos), HttpStatus.OK);

    }

    public ResponseEntity<DataDto<List<IncomeDto>>> getYearly(FilterDto filterDto) {
        List<Income> incomeYearly = new ArrayList<>();
        for (Income income : repository.findAllByDeletedFalse()) {
            if (income.getCreatedAt().getYear()==filterDto.getDate().getYear())
                incomeYearly.add(income);
        }
        List<IncomeDto> incomeDtos = mapper.toDto(incomeYearly);
        return new ResponseEntity<>(new DataDto<>(incomeDtos), HttpStatus.OK);

    }
}
