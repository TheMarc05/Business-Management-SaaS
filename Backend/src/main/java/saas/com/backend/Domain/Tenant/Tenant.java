package saas.com.backend.Domain.Tenant;

import saas.com.backend.Domain.Shared.Email;
import saas.com.backend.Domain.Shared.PhoneNumber;

import java.time.LocalDateTime;
import java.util.UUID;

public class Tenant {
    private UUID id;
    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
    private String address;
    private SubscriptionPlan subscriptionPlan;
    private LocalDateTime createdAt;
    private boolean isActive;

    //crearea unui nou tenant
    public Tenant(String name, Email email, PhoneNumber phoneNumber, String address){
        this.id = UUID.randomUUID(); //generare ID in Domain
        this.name = validateName(name);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.subscriptionPlan = SubscriptionPlan.FREE;//default free
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    //constructor pt incarcarea din persistence (folosit de Repository)
    public Tenant(UUID id, String name, Email email, PhoneNumber phoneNumber, String address, SubscriptionPlan subscriptionPlan, LocalDateTime createdAt, boolean isActive){
        this.id = id;
        this.name = validateName(name);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.subscriptionPlan = subscriptionPlan;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    private String validateName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Tenant name cannot be null or empty");
        }

        String trimmed = name.trim();

        if(trimmed.length() < 2){
            throw new IllegalArgumentException("Tenant name must be at least 2 characters");
        }

        if (trimmed.length() > 100) {
            throw new IllegalArgumentException("Tenant name cannot exceed 100 characters");
        }

        return trimmed;
    }
}
