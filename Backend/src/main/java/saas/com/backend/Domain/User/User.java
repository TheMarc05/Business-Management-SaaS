package saas.com.backend.Domain.User;


import saas.com.backend.Domain.Shared.Email;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//user aggregate root
//reprezinta un utilizator al sistemului (owner sau staff)
//fiecare user apartine unui singur Tenant (prin tenantId)
public class User {
    private UUID id;
    private UUID tenantId;
    private Email email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    //constructor pt a crea User nou
    public User(UUID tenantId, Email email, String passwordHash, String firstName, String lastName, UserRole role) {
        this.id = UUID.randomUUID();
        this.tenantId = Objects.requireNonNull(tenantId, "tenantId cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.passwordHash = validatePasswordHash(passwordHash);
        this.firstName = validateName(firstName, "firstName");
        this.lastName = validateName(lastName, "lastName");
        this.role = Objects.requireNonNull(role, "role cannot be null");
        this.isActive = isActive;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.lastLoginAt = lastLoginAt;
    }

    //constructor pt incarcarea din baza de date
    public User(UUID id, UUID tenantId, Email email, String passwordHash,String firstName, String lastName, UserRole role, boolean isActive, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.tenantId = Objects.requireNonNull(tenantId, "tenantId cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.passwordHash = validatePasswordHash(passwordHash);
        this.firstName = validateName(firstName, "firstName");
        this.lastName = validateName(lastName, "lastName");
        this.role = Objects.requireNonNull(role, "role cannot be null");
        this.isActive = isActive;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.lastLoginAt = lastLoginAt;
    }
    private String validatePasswordHash(String passwordHash) {
        if(passwordHash == null || passwordHash.trim().isEmpty()){
            throw new IllegalArgumentException("passwordHash cannot be null or empty");
        }

        return passwordHash.trim();
    }

    private String validateName(String value, String fieldName){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }

        String trimmed = value.trim();

        if(trimmed.length() > 100){
            throw new IllegalArgumentException(fieldName + " cannot be longer than 100 characters");
        }

        return trimmed;
    }

    //va fii de ajutor in services
    public boolean belongsToTenant(UUID tenantId){
        return this.tenantId.equals(tenantId);
    }

    public boolean canManageAppointments(){
        return role == UserRole.OWNER || role == UserRole.STAFF;
    }

    public boolean canManageClients(){
        return role == UserRole.OWNER || role == UserRole.STAFF;
    }

    public boolean canManageServices(){
        return role == UserRole.OWNER;
    }

    public void changePasswordHash(String newPasswordHash){
        this.passwordHash = validatePasswordHash(newPasswordHash);
    }

    public void deactivate(){
        this.isActive = false;
    }

    public void activate(){
        this.isActive = true;
    }

    public void markLoginSuccess(){
        this.lastLoginAt = LocalDateTime.now();
    }

    public UUID getId(){
        return id;
    }

    public UUID getTenantId(){
        return tenantId;
    }

    public Email getEmail(){
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
}
