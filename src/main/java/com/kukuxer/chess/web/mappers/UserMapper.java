package com.kukuxer.chess.web.mappers;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User,UserDto>{
}
