package uz.pdp.market.service.organization;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.market.criteria.OrganizationCriteria;
import uz.pdp.market.dto.organization.OrganizationCreateDto;
import uz.pdp.market.dto.organization.OrganizationDto;
import uz.pdp.market.dto.organization.OrganizationUpdateDto;
import uz.pdp.market.dto.response.DataDto;
import uz.pdp.market.entity.organization.Organization;
import uz.pdp.market.exception.exceptions.NotFoundException;
import uz.pdp.market.mapper.OrganizationMapper;
import uz.pdp.market.repository.OrganizationRepository;
import uz.pdp.market.service.AbstractService;
import uz.pdp.market.service.GenericCrudService;
import uz.pdp.market.utils.validator.organization.OrganizationValidator;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService extends AbstractService<
        OrganizationRepository,
        OrganizationMapper,
        OrganizationValidator> implements GenericCrudService<
        Organization,
        OrganizationDto,
        OrganizationCreateDto,
        OrganizationUpdateDto,
        OrganizationCriteria,
        Long> {
    protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidator validator) {
        super(repository, mapper, validator);
    }


    @Override
    public ResponseEntity<DataDto<Long>> create(OrganizationCreateDto createDto) {
        Organization organization = mapper.fromCreateDto(createDto);
        repository.save(organization);
        return new ResponseEntity<>(new DataDto<>(organization.getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        repository.delete(repository.getById(id));
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> update(OrganizationUpdateDto updateDto) {
        Optional<Organization> organizationOptional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (organizationOptional.isEmpty()) {
            throw new NotFoundException("Organization not found by id : '%s'".formatted(updateDto.getId()));
        }

        Organization organization = mapper.fromUpdateDto(updateDto, organizationOptional.get());
        repository.save(organization);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DataDto<List<OrganizationDto>>> getAll() {
        List<Organization> organizations = repository.findAllByDeletedFalse();
        List<OrganizationDto> organization = mapper.toDto(organizations);
        return new ResponseEntity<>(new DataDto<>(organization), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<OrganizationDto>>> getAll(OrganizationCriteria criteria) {
        PageRequest request = PageRequest.of(criteria.getPage(), criteria.getSize());
        List<Organization> organizations = repository.findAll(request).get().toList();
        List<OrganizationDto> organization = mapper.toDto(organizations);
        return new ResponseEntity<>(new DataDto<>(organization), HttpStatus.OK);
    }

    @Override
    public Long totalCount(OrganizationCriteria criteria) {
        return null;
    }

    @Override
    public ResponseEntity<DataDto<OrganizationDto>> get(Long id) {
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(repository.getById(id))), HttpStatus.OK);
    }
}

