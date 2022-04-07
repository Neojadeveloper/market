package uz.pdp.market.service.debtList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.debtList.DebtListCreateDto;
import uz.pdp.market.dto.debtList.DebtListDto;
import uz.pdp.market.dto.debtList.DebtListUpdateDto;
import uz.pdp.market.dto.response.AppErrorDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.auth.AuthUser;
import uz.pdp.market.entity.market.Currency;
import uz.pdp.market.entity.market.DebtList;
import uz.pdp.market.entity.market.Measurement;
import uz.pdp.market.mapper.DebtListMapper;
import uz.pdp.market.repository.DebtListRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.debtList.DebtListValidator;

import java.util.List;
import java.util.Optional;

@Service
public class DebtListService extends AbstractService<DebtListRepository, DebtListMapper, DebtListValidator> implements GenericCrudService<DebtList, DebtListDto, DebtListCreateDto, DebtListUpdateDto, GenericCriteria, Long> {

    protected DebtListService(DebtListRepository repository, DebtListMapper mapper, DebtListValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(DebtListCreateDto createDto) {

        Currency currency = new Currency();
        AuthUser authUser = new AuthUser();
        Measurement measurement = new Measurement();

        DebtList debtList = mapper.fromCreateDto(createDto);

        currency.setId(Long.valueOf(createDto.getCurrencyId()));
        debtList.setCurrency(currency);

        authUser.setId(Long.valueOf(createDto.getLenderId()));
        debtList.setLender(authUser);

        measurement.setId(Long.valueOf(createDto.getMeasurementId()));
        debtList.setMeasurement(measurement);
        debtList.setGetDate(createDto.getGetDate());
        repository.save(debtList);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<DebtList> optionalDebtList = repository.findByIdAndDeletedFalse(id);
        if (optionalDebtList.isEmpty()) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND)
                    .build()
            ), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(DebtListUpdateDto updateDto) {
        Optional<DebtList> debtListOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (debtListOptional.isEmpty()) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("DebtList not found by id : '%s'".formatted(updateDto.getId()))
                    .build()
            ), HttpStatus.CONFLICT);
        }
        DebtList debtList = mapper.fromUpdateDto(updateDto, debtListOptional.get());


        Currency currency = new Currency();
        AuthUser authUser = new AuthUser();
        Measurement measurement = new Measurement();

        currency.setId(Long.valueOf(updateDto.getCurrencyId()));
        debtList.setCurrency(currency);

        authUser.setId(Long.valueOf(updateDto.getLenderId()));
        debtList.setLender(authUser);

        measurement.setId(Long.valueOf(updateDto.getMeasurementId()));
        debtList.setMeasurement(measurement);
        debtList.setGetDate(updateDto.getGetDate());

        repository.save(debtList);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<DebtListDto>>> getAll() {
        List<DebtList> debtLists = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(debtLists), (long) debtLists.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<DebtListDto>> get(Long id) {
        Optional<DebtList> debtListOptional = repository.findByIdAndDeletedFalse(id);
        if (debtListOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().build()), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(debtListOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
