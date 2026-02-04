package saas.com.backend.Domain.Shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

//value object pentru money
//incapsuleaza o suma de bani cu moneda
//foloseste BigDecimal pt precizie in calcule financiare, amount-ul este rotunjit la doua zecimale (standard pt monede)
public class Money {
    private static final int SCALE = 2;//doua zecimale pt monede
    private static final RoundingMode ROUDING_MODE = RoundingMode.HALF_UP;
    private final BigDecimal amount;
    private final String currency;//ISO 4217 format

    public Money(BigDecimal amount, String currency){
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }

        if(currency == null || currency.trim().isEmpty()){
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }

        String normalizedCurrency = currency.trim().toUpperCase();

        if(normalizedCurrency.length() != 3){
            throw new IllegalArgumentException("Currency needs to be 3-letter ISO code: " + currency);
        }

        this.amount = amount.setScale(SCALE, ROUDING_MODE);
        this.currency = normalizedCurrency;
    }

    //constructor convenabil pentru int (presupune ca e deja in unitati intregi)
    public Money(int amount, String currency){
        this(BigDecimal.valueOf(amount), currency);
    }

    //constructor convenabil pentru double
    public Money(double amount, String currency){
        this(BigDecimal.valueOf(amount), currency);
    }

    //aduna doua sume de bani
    public Money add(Money other){
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    //scade o suma de bani
    public  Money subtract(Money other){
        validateSameCurrency(other);
        BigDecimal result = this.amount.subtract(other.amount);

        if(result.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Cannot subtract " + other + " from " + this + " (result would be negative)");
        }

        return new Money(result, this.currency);
    }

    //inmulteste suma cu un factor
    public Money multiply(BigDecimal multiplier){
        if (multiplier == null) {
            throw new IllegalArgumentException("Multiplier cannot be null");
        }
        return new Money(this.amount.multiply(multiplier), this.currency);
    }

    //verifica daca suma este mai mare decat alta
    public boolean isGreaterThan(Money other){
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    //verifica daca suma este mai mica sau egala cu zero
    public boolean isZero(){
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    //valideaza ca doua sume de bani au aceeasi moneda
    private void validateSameCurrency(Money other){
        if(!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Cannot perform operation on different currencies: " + this.currency + " and " + other.currency);
        }
    }

    //returneaza amount-ul
    public BigDecimal getAmount(){
        return amount;
    }

    //returneaza moneda
    public String getCurrency(){
        return currency;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode(){
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString(){
        return amount + " " + currency;
    }
}
