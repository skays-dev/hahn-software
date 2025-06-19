package com.hahn.software.mappers.Component;

import java.util.Collection;
import java.util.List;

public interface DtoMapper<TDto, TEntity>  {
    List<TDto> toDtos(final Collection<TEntity> entityList);

    TDto toDto(final TEntity source);

    List<TEntity> toEntities(final Collection<TDto> entityList);

    TEntity toEntity(final TDto source);

    void addConfiguration();
}
