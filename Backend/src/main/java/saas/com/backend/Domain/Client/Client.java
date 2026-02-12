package saas.com.backend.Domain.Client;

import lombok.Getter;
import saas.com.backend.Domain.Shared.Email;
import saas.com.backend.Domain.Shared.PhoneNumber;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//reprezinta clientul business-ului
//fiecare client apartine unui singur tenant
public class Client {
    @Getter
    private UUID id;
    @Getter
    private UUID tenantId;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private Email email;
    private PhoneNumber phoneNumber;
    @Getter
    private String notes;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;

    //constructor pt a crea un nou Client
    public Client(UUID tenantId, String firstName, String lastName, Email email, PhoneNumber phoneNumber, String notes, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = UUID.randomUUID();
        this.tenantId = Objects.requireNonNull(tenantId, "tenantId cannot be null");
        this.firstName = validateName(firstName, "firstName");
        this.lastName = validateName(lastName, "lastName");
        this.email = email;
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.notes = notes != null ? notes.trim() : null;//trim daca nu e null
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //constructor pt incarcarea din constructor
    public Client(UUID id, UUID tenantId, String firstName, String lastName, Email email, PhoneNumber phoneNumber, String notes, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.tenantId = Objects.requireNonNull(tenantId, "tenantId cannot be null");
        this.firstName = validateName(firstName, "firstName");
        this.lastName = validateName(lastName, "lastName");
        this.email = email;
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.notes = notes != null ? notes.trim() : null;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
    }

    private String validateName(String value, String fieldName) {
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }

        String trimmed = value.trim();

        if(trimmed.length() > 100){
            throw new IllegalArgumentException(fieldName + " cannot be longer than 100 characters");
        }

        return trimmed;
    }

    public boolean belongsToTenant(UUID tenantId){
        return this.tenantId.equals(tenantId);
    }

    public void updateContactInfo(Email email, PhoneNumber phoneNumber){
        if(phoneNumber == null){
            throw new IllegalArgumentException("phone number cannot be null");
        }

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(Email email){
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePhone(PhoneNumber phoneNumber){
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phone number cannot be null");
        this.updatedAt = LocalDateTime.now();
    }

    public void updateNotes(String notes){
        this.notes = notes != null ? notes.trim() : null;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasEmail(){
        return email != null;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
