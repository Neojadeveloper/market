package uz.pdp.market.service;

import uz.pdp.market.mapper.BaseMapperI;
import uz.pdp.market.repository.AbstractRepository;
import uz.pdp.market.utils.validator.Validator;

public abstract class AbstractService<
        R extends AbstractRepository,
        M extends BaseMapperI,
        V extends Validator> {

    protected final R repository;
    protected final M mapper;
    protected final V validator;

    protected AbstractService(R repository, M mapper, V validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }
}
