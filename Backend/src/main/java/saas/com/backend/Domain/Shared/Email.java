package saas.com.backend.Domain.Shared;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    //pattern simplu pt validarea email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");//nu poate fi modificat dupa creare (imutabilitate0
    private final String value;

    //creeaza un Email Value Object
    public Email(String value){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        //normalizare
        String normalized = value.toLowerCase().trim();

        if(!isValid(normalized)){
            throw new IllegalArgumentException("Invalid email address: " + value);
        }

        this.value = normalized;
    }

    //valideaza formatul email-ului
    private boolean isValid(String email){
        return EMAIL_PATTERN.matcher(email).matches();
    }

    //returneaza valoarea email-ului
    public String getValue(){
        return value;
    }

    //returneaza domeniul email-ului
    public String getDomain(){
        int atIndex = value.indexOf('@');
        return value.substring(atIndex + 1);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o ==null || getClass() != o.getClass()){
            return false;
        }

        Email email = (Email) o;

        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode(){
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return value;
    }
}
