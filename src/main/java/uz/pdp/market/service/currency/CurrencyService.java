package uz.pdp.market.service.currency;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.currency.CurrencyCreateDto;
import uz.pdp.market.dto.currency.CurrencyDto;
import uz.pdp.market.dto.currency.CurrencyUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Currency;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.CurrencyMapper;
import uz.pdp.market.repository.CurrencyRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.currency.CurrencyValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService extends AbstractService<CurrencyRepository, CurrencyMapper, CurrencyValidator> implements GenericCrudService<Currency, CurrencyDto, CurrencyCreateDto, CurrencyUpdateDto, GenericCriteria, Long> {

    protected CurrencyService(CurrencyRepository repository, CurrencyMapper mapper, CurrencyValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(CurrencyCreateDto createDto) {
        Currency currency = mapper.fromCreateDto(createDto);
        currency.setCreatedAt(LocalDateTime.now());
        repository.save(currency);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<Currency> optionalCurrency = repository.findByIdAndDeletedFalse(id);
        if (optionalCurrency.isEmpty())
            throw new NotFoundException("Currency not found by id : '%s'".formatted(id));
        repository.softDelete("" + optionalCurrency.get().getId());
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(CurrencyUpdateDto updateDto) {
        Optional<Currency> categoryOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Currency not found by id : '%s'".formatted(updateDto.getId()));
        }

        Currency currency = mapper.fromUpdateDto(updateDto, categoryOptional.get());
        currency.setUpdatedAt(LocalDateTime.now());
        repository.save(currency);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<CurrencyDto>>> getAll() {
        List<Currency> currencies = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(currencies), (long) currencies.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<CurrencyDto>> get(Long id) {
        Optional<Currency> currencyOptional = repository.findByIdAndDeletedFalse(id);
        if (currencyOptional.isEmpty())
            throw new NotFoundException("Currency not found by id : '%s'".formatted(id));

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(currencyOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
