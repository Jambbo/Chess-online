package com.kukuxer.chess.web.mappers;
import java.util.*;
public interface Mappable <E,D> { // entity and Dto
    /* entity to dto */
    D toDto(E entity);
    List<D> toDto(List<E> entity);
    /* dto to entity */
    E toEntity(D dto);
    List<E> toEntity(List<D> dto);
}
