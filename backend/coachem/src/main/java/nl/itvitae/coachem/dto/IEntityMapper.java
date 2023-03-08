package nl.itvitae.coachem.dto;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface IEntityMapper<E, D> {
    D get(E e);

    @Mapping(target = "id", ignore = true)
    E post(D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    E update(D dto, @MappingTarget E e);
}
