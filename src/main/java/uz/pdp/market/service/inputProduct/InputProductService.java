package uz.pdp.market.service.inputProduct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.inputProduct.InputProductCreateDto;
import uz.pdp.market.dto.inputProduct.InputProductDto;
import uz.pdp.market.dto.inputProduct.InputProductUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.InputProduct;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.InputProductMapper;
import uz.pdp.market.repository.InputProductRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.inputProduct.InputProductValidator;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService extends AbstractService<InputProductRepository,
        InputProductMapper, InputProductValidator>
        implements GenericCrudService<InputProduct,
        InputProductDto, InputProductCreateDto,
        InputProductUpdateDto, GenericCriteria, Long> {

    protected InputProductService(InputProductRepository repository, InputProductMapper mapper, InputProductValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(InputProductCreateDto createDto) {
        InputProduct inputProduct = mapper.fromCreateDto(createDto);
        repository.save(inputProduct);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<InputProduct> optionalInputProduct = repository.findByIdAndDeletedFalse(id);
        if (optionalInputProduct.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(InputProductUpdateDto updateDto) {
        Optional<InputProduct> inputProductOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (inputProductOptional.isEmpty()) {
            throw new NotFoundException("InputProduct not found by id : '%s'".formatted(updateDto.getId()));
        }
        InputProduct inputProduct = mapper.fromUpdateDto(updateDto, inputProductOptional.get());
        repository.save(inputProduct);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<InputProductDto>>> getAll() {
        List<InputProduct> inputProducts = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(inputProducts), (long) inputProducts.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<InputProductDto>> get(Long id) {
        Optional<InputProduct> inputProductOptional = repository.findByIdAndDeletedFalse(id);
        if (inputProductOptional.isEmpty())
            throw new NotFoundException("Not found");

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(inputProductOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
