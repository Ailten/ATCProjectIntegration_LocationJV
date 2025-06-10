package adrien.faouzi.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name = "City.SelectCityByPostalCode", query = "SELECT c from City c " +
                "where c.postalCode = :postalCode"),
        @NamedQuery(name = "City.SelectCityById", query= "SELECT c from City c where c.id = :id")
})


@Entity
@Table(name = "city", indexes = {
        @Index(name = "fk_idCountry", columnList = "idCountry")
})
public class City {

    @Min(1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCity", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCountry", nullable = false)
    private Country idCountry;

    @NotNull
    @Min(1)
    @Column(name = "postalCode", nullable = false)
    private int postalCode;

    @NotNull
    @Column(name = "cityName", nullable = false, length = 60)
    private String cityName;

    @OneToMany(mappedBy = "idCity")
    private Set<Address> addresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCity")
    private Set<Store> stores = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && idCountry == city.idCountry && postalCode == city.postalCode && Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCountry, postalCode, cityName);
    }


    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public Country getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Country idCountry) {
        this.idCountry = idCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}