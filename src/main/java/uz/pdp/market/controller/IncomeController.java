package uz.pdp.market.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.market.dto.filter.FilterDto;
import uz.pdp.market.dto.income.IncomeCreateDto;
import uz.pdp.market.dto.income.IncomeDto;
import uz.pdp.market.dto.income.IncomeUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.filtr.Filter;
import uz.pdp.market.service.income.IncomeService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class IncomeController extends AbstractController<IncomeService> {

    public IncomeController(IncomeService service) {
        super(service);
    }

    @PostMapping(value = PATH + "/income")
    public ResponseEntity<DataDto<Long>> create(@Valid @RequestBody IncomeCreateDto createDto) {
        return service.create(createDto);
    }

    @PutMapping(value = PATH + "/income")
    public ResponseEntity<DataDto<Boolean>> update(@Valid @RequestBody IncomeUpdateDto updateDto) {
        return service.update(updateDto);
    }

    @DeleteMapping(value = PATH + "/income/{id}")
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping(value = PATH + "/income/{id}")
    public ResponseEntity<DataDto<IncomeDto>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping(value = PATH + "/income")
    public ResponseEntity<DataDto<List<IncomeDto>>> getAll() {
        return service.getAll();
    }

    @PostMapping(value = PATH + "/income/day")
    public ResponseEntity<DataDto<List<IncomeDto>>> getDaily(@Valid @RequestBody FilterDto filterDto) {
        return service.getDaily(filterDto);
    }

    @PostMapping(value = PATH+"/income/week")
    public ResponseEntity<DataDto<List<IncomeDto>>> getWeekly(@Valid @RequestBody FilterDto filterDto){
        return service.getWeekly(filterDto);
    }

    @PostMapping(value = PATH+"/income/month")
    public ResponseEntity<DataDto<List<IncomeDto>>> getMonthly(@Valid @RequestBody FilterDto filterDto){
        return service.getMonthly(filterDto);
    }

    @PostMapping(value = PATH+"/income/year")
    public ResponseEntity<DataDto<List<IncomeDto>>> getYearly(@Valid @RequestBody FilterDto filterDto){
        return service.getYearly(filterDto);
    }
}
