package restaurante.example.demo.service.mapper.common;

public interface ISourceTargetMapper<Entity,DTO> {
    DTO entityToDto(Entity  entity);
    Entity dtoToEntity(DTO dto);   
}
