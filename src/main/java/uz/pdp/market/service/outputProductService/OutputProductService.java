package uz.pdp.market.service.outputProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.OutputProductCriteria;
import uz.pdp.market.dto.outputProduct.OutputProductCreateDto;
import uz.pdp.market.dto.outputProduct.OutputProductDto;
import uz.pdp.market.dto.outputProduct.OutputProductUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Currency;
import uz.pdp.market.entity.market.InputProduct;
import uz.pdp.market.entity.market.Measurement;
import uz.pdp.market.entity.market.OutputProduct;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.OutputProductMapper;
import uz.pdp.market.repository.OutputProductRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.outputProduct.OutputProductValidator;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService extends AbstractService<
        OutputProductRepository,
        OutputProductMapper,
        OutputProductValidator> implements GenericCrudService<
        OutputProduct,
        OutputProductDto,
        OutputProductCreateDto,
        OutputProductUpdateDto,
        OutputProductCriteria,
        Long> {

    protected OutputProductService(OutputProductRepository repository, OutputProductMapper mapper, OutputProductValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(OutputProductCreateDto createDto) {
        OutputProduct outputProduct = mapper.fromCreateDto(createDto);

        // shunga qarab qolgan barcha service larni korib tog'irlab chiqish kk please

        InputProduct inputProduct = new InputProduct();
        inputProduct.setId(createDto.getInputProductId());

        Currency currency = new Currency();
        currency.setId(createDto.getCurrencyId());

        Measurement measurement = new Measurement();
        measurement.setId(createDto.getMeasurementId());

        outputProduct.setInputProduct(inputProduct);
        outputProduct.setCurrency(currency);
        outputProduct.setMeasurement(measurement);
        repository.save(outputProduct);

        repository.insertToIncome

                (outputProduct.getAmount(),
                        outputProduct.getMeasurement().getId(),
                        outputProduct.getPrice() == null ? outputProduct.getDiscountedPrice() : outputProduct.getPrice(),
                        outputProduct.getCurrency().getId());

        return new ResponseEntity<>(new DataDto<>(outputProduct.getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        repository.delete(repository.getById(id));
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(OutputProductUpdateDto updateDto) {
        Optional<OutputProduct> outputProductOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (outputProductOptional.isEmpty()) {
            throw new NotFoundException("OutputProduct not found by id : '%s'".formatted(updateDto.getId()));
        }

        OutputProduct outputProduct = mapper.fromUpdateDto(updateDto, outputProductOptional.get());
        repository.save(outputProduct);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<OutputProductDto>>> getAll() {
        List<OutputProduct> outputProducts = repository.findAllByDeletedFalse();
        List<OutputProductDto> outputProduct = mapper.toDto(outputProducts);
        return new ResponseEntity<>(new DataDto<>(outputProduct), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<OutputProductDto>> get(Long id) {
        OutputProduct outputProduct = repository.findByIdAndDeletedFalse(id).get();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(outputProduct)), HttpStatus.OK);
    }

    @Override
    public Long totalCount(OutputProductCriteria criteria) {
        return null;
    }
}
