package uz.pdp.market.service.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.category.CategoryCreateDto;
import uz.pdp.market.dto.category.CategoryDto;
import uz.pdp.market.dto.category.CategoryUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Category;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.CategoryMapper;
import uz.pdp.market.repository.CategoryRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.category.CategoryValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends AbstractService<CategoryRepository, CategoryMapper, CategoryValidator> implements GenericCrudService<Category, CategoryDto, CategoryCreateDto, CategoryUpdateDto, GenericCriteria, Long> {

    protected CategoryService(CategoryRepository repository, CategoryMapper mapper, CategoryValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(CategoryCreateDto createDto) {
        Category category = mapper.fromCreateDto(createDto);
        category.setCreatedAt(LocalDateTime.now());
        repository.save(category);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<Category> optionalCategory = repository.findByIdAndDeletedFalse(id);
        if (optionalCategory.isEmpty()) throw new NotFoundException("Category not found by id : '%s'".formatted(id));
        repository.delete(optionalCategory.get());
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(CategoryUpdateDto updateDto) {
        Optional<Category> categoryOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not found by id : '%s'".formatted(updateDto.getId()));
        }

        Category category = mapper.fromUpdateDto(updateDto, categoryOptional.get());
        category.setUpdatedAt(LocalDateTime.now());
        repository.save(category);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<CategoryDto>>> getAll() {
        List<Category> categories = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(categories), (long) categories.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<CategoryDto>> get(Long id) {
        Optional<Category> categoryOptional = repository.findByIdAndDeletedFalse(id);
        if (categoryOptional.isEmpty()) throw new NotFoundException("Category not found by id : '%s'".formatted(id));

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(categoryOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
