package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
@NamedQueries(value = {
        @NamedQuery(name= "Store.SelectStoreByIdStore", query = "select s from Store s where s.id = :idStore"),
        @NamedQuery(name= "Store.SelectStoreAll", query = "select s from Store s")
})
@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStore", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCity", nullable = false)
    private City idCity;

    @Column(name = "storeName", nullable = false, length = 60)
    private String storeName;

    @Column(name = "streetStore", nullable = false)
    private String streetStore;

    @Column(name = "numberStore", nullable = false)
    private int numberStore;

    @Column(name = "boxStore", length = 20)
    private String boxStore;

    @Column(name = "phone", length = 45)
    private String phone;

    @OneToMany(mappedBy = "idStore")
    private Set<Copy> copies = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return id == store.id ;//&& idCity == store.idCity && numberStore == store.numberStore && Objects.equals(storeName, store.storeName) && Objects.equals(streetStore, store.streetStore) && Objects.equals(boxStore, store.boxStore) && Objects.equals(phone, store.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCity, storeName, streetStore, numberStore, boxStore, phone);
    }


    public Set<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copy> copies) {
        this.copies = copies;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBoxStore() {
        return boxStore;
    }

    public void setBoxStore(String boxStore) {
        this.boxStore = boxStore;
    }

    public int getNumberStore() {
        return numberStore;
    }

    public void setNumberStore(int numberStore) {
        this.numberStore = numberStore;
    }

    public String getStreetStore() {
        return streetStore;
    }

    public void setStreetStore(String streetStore) {
        this.streetStore = streetStore;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public City getIdCity() {
        return idCity;
    }

    public void setIdCity(City idCity) {
        this.idCity = idCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}