package uz.pdp.market.service.orderList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.orderList.OrderListCreateDto;
import uz.pdp.market.dto.orderList.OrderListDto;
import uz.pdp.market.dto.orderList.OrderListUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.OrderList;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.OrderListMapper;
import uz.pdp.market.repository.AuthUserRepository;
import uz.pdp.market.repository.CategoryRepository;
import uz.pdp.market.repository.OrderListRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.orderList.OrderListValidator;

import java.util.List;
import java.util.Optional;

@Service
public class OrderListService extends AbstractService<OrderListRepository, OrderListMapper, OrderListValidator> implements GenericCrudService<OrderList, OrderListDto, OrderListCreateDto, OrderListUpdateDto, GenericCriteria, Long> {

    private final CategoryRepository categoryRepository;
    private final AuthUserRepository authUserRepository;

    protected OrderListService(OrderListRepository repository, OrderListMapper mapper, OrderListValidator validator, CategoryRepository categoryRepository, AuthUserRepository authUserRepository) {
        super(repository, mapper, validator);
        this.categoryRepository = categoryRepository;
        this.authUserRepository = authUserRepository;
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(OrderListCreateDto createDto) {
        OrderList orderList = mapper.fromCreateDto(createDto);
        orderList.setAmount(createDto.getAmount());
        orderList.setCategory(categoryRepository.findByIdAndDeletedFalse(Long.valueOf(createDto.getCategoryId())).get());
        orderList.setClientPhone(createDto.getClientPhone());
        orderList.setCompleteDate(createDto.getCompleteDate());
        orderList.setDescription(createDto.getDescription());
        orderList.setInAdvance(createDto.getInAdvance());
        orderList.setName(createDto.getName());
        orderList.setRegisteredDate(createDto.getRegisteredDate());

        repository.save(orderList);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<OrderList> optionalOrderList = repository.findByIdAndDeletedFalse(id);
        if (optionalOrderList.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(OrderListUpdateDto updateDto) {
        Optional<OrderList> orderListOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (orderListOptional.isEmpty()) {
            throw new NotFoundException("OrderList not found by id : '%s'".formatted(updateDto.getId()));
        }
        OrderList orderList = mapper.fromUpdateDto(updateDto, orderListOptional.get());
        repository.save(orderList);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<OrderListDto>>> getAll() {
        List<OrderList> orderLists = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(orderLists), (long) orderLists.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<OrderListDto>> get(Long id) {
        Optional<OrderList> orderListOptional = repository.findByIdAndDeletedFalse(id);
        if (orderListOptional.isEmpty())
            throw new NotFoundException("Not found");

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(orderListOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
