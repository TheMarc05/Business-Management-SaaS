package saas.com.backend.Domain.Tenant;

import lombok.Getter;
import saas.com.backend.Domain.Shared.Email;
import saas.com.backend.Domain.Shared.PhoneNumber;

import java.time.LocalDateTime;
import java.util.UUID;

//tenant aggregate root
//reprezinta business-ul
//este root-ul multi-tenancy - nu are tenantId (este root-ul)
//toate celelalte entitati au tenantId care se refera la acest Tenant
//acesta este domain entity - nu contine JPA annotations, JPA mapping-ul se face in infrastructure layer prin adapter pattern
public class Tenant {
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private Email email;
    @Getter
    private PhoneNumber phoneNumber;
    @Getter
    private String address;
    @Getter
    private SubscriptionPlan subscriptionPlan;
    @Getter
    private LocalDateTime createdAt;
    @Getter
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

    public void updateContactInfo(Email email, PhoneNumber phoneNumber){
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null");
        }
        if(phoneNumber == null){
            throw new IllegalArgumentException("PhoneNumber cannot be null");
        }

        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateAddress(String address){
        this.address = address;
    }

    public void upgradePlan(SubscriptionPlan newPlan){
        if(newPlan == null){
            throw new IllegalArgumentException("Subscription Plan cannot be null");
        }

        //momentan nu se poate trece la un plan mai mic, trebuie un proces separat
        this.subscriptionPlan = newPlan;
    }

    //dezactiveaza tenant-ul, nu mai poate accesa sistemul daca este dezactivat
    public void deactivate(){
        this.isActive = false;
    }

    //activeaza tenant-ul
    public void activate(){
        this.isActive = true;
    }

    //verifica daca tenant-ul are un plan specific
    public boolean hasPlan(SubscriptionPlan plan){
        return this.subscriptionPlan == plan;
    }

    //verifica daca are plan premium
    public boolean isPremium(){
        return subscriptionPlan == SubscriptionPlan.PREMIUM;
    }
}
