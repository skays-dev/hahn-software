package com.hahn.software.mappers.Component;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDtoMapper<TDto, TEntity> implements DtoMapper<TDto, TEntity> {
    protected ModelMapper modelMapper = new ModelMapper();
    protected ModelMapper modelMapperFull = new ModelMapper();
    protected Class<TDto> typeOfDto;
    protected Class<TEntity> typeOfEntity;


    @SuppressWarnings("unchecked")
    public AbstractDtoMapper() {
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        typeOfDto = (Class<TDto>) types[0];
        typeOfEntity = (Class<TEntity>) types[1];
    }

    @PostConstruct
    public void initMapper() {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(typeOfDto, typeOfEntity);
        modelMapper.createTypeMap(typeOfEntity, typeOfDto);
        addConfiguration();

        modelMapperFull.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapperFull.createTypeMap(typeOfDto, typeOfEntity);
        modelMapperFull.createTypeMap(typeOfEntity, typeOfDto);
    }

    // #######################  ModelMapper : Entite ---> DTO #######################
    @Override
    public List<TDto> toDtos(final Collection<TEntity> entityList) {
        if (entityList != null && !entityList.isEmpty()) {
            return entityList.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public TDto toDto(final TEntity source) {
        if (source != null) {
            return modelMapper.map(source, typeOfDto);
        }
        return null;
    }

    @Override
    public List<TEntity> toEntities(final Collection<TDto> entityList) {
        if (entityList != null && !entityList.isEmpty()) {
            return entityList.stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public TEntity toEntity(final TDto source) {
        if (source != null) {
            return modelMapper.map(source, typeOfEntity);
        }
        return null;
    }

    // ############################  Tools  ###########################
    @Override
    public void addConfiguration() {

    }
}
