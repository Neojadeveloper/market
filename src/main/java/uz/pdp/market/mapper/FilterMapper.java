package uz.pdp.market.mapper;

import uz.pdp.market.dto.filter.FilterDto;
import uz.pdp.market.entity.filtr.Filter;

import java.util.List;

/**
 * @author Husanov Asliddin Sat,12:15,02/04/22
 */
public interface FilterMapper extends BaseMapper<
        Filter,
        FilterDto,
        Object,Object>{

    @Override
    FilterDto toDto(Filter filter);

    @Override
    List<FilterDto> toDto(List<Filter> e);

    }
