package adrien.faouzi.entities;

import adrien.faouzi.enumeration.TypeAddress;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAddress", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User idUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCity", nullable = false)
    private City idCity;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,}$")
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull
    @Min(1)
    @Column(name = "number", nullable = false)
    private int number;

    @Pattern(regexp= "^[a-zA-Z1-9]{0,20}$")
    @Column(name = "box", length = 20)
    private String box;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeAddress", nullable = false)
    private TypeAddress typeAddress = TypeAddress.FACTURATION;

    @Column(name = "enable", nullable = false)
    private boolean enable = true;

    @OneToMany(mappedBy = "idAddress")
    private Set<Addressorder> addressorders = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && idUser == address.idUser && idCity == address.idCity && number == address.number && enable == address.enable && Objects.equals(street, address.street) && Objects.equals(box, address.box) && Objects.equals(typeAddress, address.typeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idCity, street, number, box, typeAddress, enable);
    }

    public Set<Addressorder> getAddressorders() {
        return addressorders;
    }

    public void setAddressorders(Set<Addressorder> addressorders) {
        this.addressorders = addressorders;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTypeAddress() {
        return typeAddress.getTypeAddress();
    }

    public void setTypeAddress(TypeAddress typeAddress) {
        this.typeAddress = typeAddress;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getIdCity() {
        return idCity;
    }

    public void setIdCity(City idCity) {
        this.idCity = idCity;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}