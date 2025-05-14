package restaurante.example.demo.persistence.enums;

public enum StatusEnum {

    ACTIVE(true),
    INACTIVE(false);
    
    private final Boolean status;
        
    StatusEnum(Boolean status){
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }
}
