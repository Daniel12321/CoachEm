package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Category;

public record CategoryDto(Long id, String name) {

    @JsonIgnore
    public boolean isValid() {
        return name!=null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Category, CategoryDto> {
    }
}
