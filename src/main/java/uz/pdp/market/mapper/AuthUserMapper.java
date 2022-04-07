package uz.pdp.market.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.pdp.market.dto.auth.AuthUserDto;
import uz.pdp.market.entity.auth.AuthUser;

@Mapper(componentModel = "spring")
@Component
public interface AuthUserMapper extends uz.pdp.market.mapper.Mapper, BaseMapperI {

    AuthUserDto toDto(AuthUser user);
}
