package saas.com.backend.Domain.Shared;

import java.util.Objects;

//value object pentru phone number
//incapsuleaza un numar de telefon cu normalizare
public class PhoneNumber {
    private final String value;

    public PhoneNumber(String value){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        this.value = value;
    }

    private String normalize(String phone){
        String normalized = phone
                .replaceAll("\\s+", "")//elimina spatii
                .replaceAll("[-()]", "")//elimina linii si paranteze
                .trim();

        //trebuie sa contina cel putin cifre
        if(normalized.matches("^\\+?[0-9]{7,15}$")){
            return normalized;
        }

        //daca nu respecta formatul, totusi il returneaza normalizat
        //formate diferite de tari
        return normalized;
    }

    //returneaza valoarea numarului de telefon
    public String getValue() {
        return value;
    }

    //verifica daca numarul incepe cu cod de tara
    public boolean hashCountryCode(){
        return value.startsWith("+");
    }

    //returneaza codul de tara daca exista
    public String getCountryCode(){
        if(hashCountryCode()){
            //primele 1-3 cifre dupa + sunt codul tarii
            int endIndex = Math.min(value.length(), 4);//+40 sau +123
            return value.substring(0, endIndex);
        }
        return null;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        PhoneNumber that = (PhoneNumber) o;

        return Objects.equals(value, that.value);
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
