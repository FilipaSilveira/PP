/*
* Nome: Ana Filipa Sousa Silveira
* Número: 8160040
* Turma: LSIRC
*
* Nome: Rafael António Alves Maia
* Número: 8160489
* Turma: LSIRC
*/

package base;

import order.base.IAddress;

/**
 *
 * @author Filipa
 */
public class Address implements IAddress {

    private String city;
    private String country;
    private int number;
    private String state;
    private String street;

    public Address(String city, String country, int number, String state, String street) {
        this.city = city;
        this.country = country;
        this.number = number;
        this.state = state;
        this.street = street;
    }
    
    
    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public void setCity(String string) {
        this.city = string;
    }

    @Override
    public void setCountry(String string) {
        this.country = string;
    }

    @Override
    public void setNumber(int i) {
        this.number = i;
    }

    @Override
    public void setState(String string) {
        this.state = string;
    }

    @Override
    public void setStreet(String string) {
        this.street = string;
    }
    
    @Override
     public String toString(){
        return "Country: " + this.getCountry() +
               "\nCity: " + this.getCity() +
               "\nState: " + this.getState() +
               "\nNumber: " + this.number +
               "\nStreet: " + this.street;
    }
    
}
