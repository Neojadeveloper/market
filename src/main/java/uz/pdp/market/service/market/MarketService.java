package uz.pdp.market.service.market;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.GenericCriteria;
import uz.pdp.market.dto.market.MarketCreateDto;
import uz.pdp.market.dto.market.MarketDto;
import uz.pdp.market.dto.market.MarketUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.market.Market;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.MarketMapper;
import uz.pdp.market.repository.MarketRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.market.MarketValidator;

import java.util.List;
import java.util.Optional;

@Service
public class MarketService extends AbstractService<MarketRepository, MarketMapper, MarketValidator> implements GenericCrudService<Market, MarketDto, MarketCreateDto, MarketUpdateDto, GenericCriteria, Long> {

    protected MarketService(MarketRepository repository, MarketMapper mapper, MarketValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(MarketCreateDto createDto) {
        Market market = mapper.fromCreateDto(createDto);
        repository.save(market);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<Market> optionalMarket = repository.findByIdAndDeletedFalse(id);
        if (optionalMarket.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(MarketUpdateDto updateDto) {
        Optional<Market> marketOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (marketOptional.isEmpty()) {
            throw new NotFoundException("Market not found by id : '%s'".formatted(updateDto.getId()));
        }
        Market market = mapper.fromUpdateDto(updateDto, marketOptional.get());
        repository.save(market);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<MarketDto>>> getAll() {
        List<Market> markets = repository.findAllByDeletedFalse();
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(markets), (long) markets.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<MarketDto>> get(Long id) {
        Optional<Market> marketOptional = repository.findByIdAndDeletedFalse(id);
        if (marketOptional.isEmpty())
            throw new NotFoundException("not found");

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(marketOptional.get())), HttpStatus.OK);
    }

    @Override
    public Long totalCount(GenericCriteria criteria) {
        return null;
    }
}
