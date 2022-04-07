package uz.pdp.market.service.measurment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.measurement.MeasurementCreateDto;
import uz.pdp.market.dto.measurement.MeasurementDto;
import uz.pdp.market.dto.measurement.MeasurementUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Measurement;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.MeasurementMapper;
import uz.pdp.market.repository.MeasurementRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.measurement.MeasurementValidator;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService extends AbstractService<MeasurementRepository, MeasurementMapper, MeasurementValidator> implements GenericCrudService<Measurement, MeasurementDto, MeasurementCreateDto, MeasurementUpdateDto, GenericCriteria, Long> {

    protected MeasurementService(MeasurementRepository repository, MeasurementMapper mapper, MeasurementValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(MeasurementCreateDto createDto) {
        Measurement measurement = mapper.fromCreateDto(createDto);
        repository.save(measurement);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<Measurement> optionalMeasurement = repository.findByIdAndDeletedFalse(id);
        if (optionalMeasurement.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(MeasurementUpdateDto updateDto) {
        Optional<Measurement> measurementOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (measurementOptional.isEmpty()) {
            throw new NotFoundException("Measurement not found by id : '%s'".formatted(updateDto.getId()));
        }
        Measurement measurement = mapper.fromUpdateDto(updateDto, measurementOptional.get());
        repository.save(measurement);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<MeasurementDto>>> getAll() {
        List<Measurement> measurements = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(measurements), (long) measurements.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<MeasurementDto>> get(Long id) {
        Optional<Measurement> measurementOptional = repository.findByIdAndDeletedFalse(id);
        if (measurementOptional.isEmpty())
           throw new NotFoundException("Not found");

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(measurementOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
