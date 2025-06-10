package adrien.faouzi.entities;

import adrien.faouzi.enumeration.StatusCopy;
import adrien.faouzi.managedBeans.CopyStaticBean;
import adrien.faouzi.services.CopyService;
import adrien.faouzi.utility.UtilityProcessing;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name= "Copy.SelectCopyByIdCopy", query = "select c from Copy c where (c.id = :idCopy)"),
        @NamedQuery(name= "Copy.SelectCopyByFilterOrderByStrAsc",
                query = "select distinct c from Copy c " +
                        "where ( " +
                        "  ((lower(c.idStore.storeName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.idPricePlatform.idProduct.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.copyName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'storename') then c.idStore.storeName " +
                        "  when (:orderBy like 'productname') then c.idPricePlatform.idProduct.productName " +
                        "  when (:orderBy like 'copyname') then c.copyName " +
                        //"  when (:orderBy like 'buyprice') then c.buyPrice " +
                        "  when (:orderBy like 'status') then c.status " +
                        "  else c.id " +
                        "end asc"),
        @NamedQuery(name= "Copy.SelectCopyByFilterOrderByStrDesc",
                query = "select distinct c from Copy c " +
                        "where ( " +
                        "  ((lower(c.idStore.storeName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.idPricePlatform.idProduct.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.copyName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'storename') then c.idStore.storeName " +
                        "  when (:orderBy like 'productname') then c.idPricePlatform.idProduct.productName " +
                        "  when (:orderBy like 'copyname') then c.copyName " +
                        //"  when (:orderBy like 'buyprice') then c.buyPrice " +
                        "  when (:orderBy like 'status') then c.status " +
                        "  else c.id " +
                        "end desc"),
        @NamedQuery(name= "Copy.SelectCopyByFilterOrderByNumAsc",
                query = "select distinct c from Copy c " +
                        "where ( " +
                        "  ((lower(c.idStore.storeName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.idPricePlatform.idProduct.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.copyName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'buyprice') then c.buyPrice " +
                        "  else c.id " +
                        "end asc"),
        @NamedQuery(name= "Copy.SelectCopyByFilterOrderByNumDesc",
                query = "select distinct c from Copy c " +
                        "where ( " +
                        "  ((lower(c.idStore.storeName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.idPricePlatform.idProduct.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.copyName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'buyprice') then c.buyPrice " +
                        "  else c.id " +
                        "end desc"),
        @NamedQuery(name= "Copy.SelectCountCopy",
                query = "select c from Copy c " +
                        "where ( " +
                        "  (c.idPricePlatform.id = :idPricePlatform) and " +
                        "  (c.id < :idCopy) " +
                        ")"),
        @NamedQuery(name= "Copy.SelectCountCopyAvailableForAPricePlatform",
                query = "select c from Copy c " +
                        "where ( " +
                        "  (c.idPricePlatform.id = :idPricePlatform) and " +
                        "  (c.status = :statusDisponible) " +
                        ")")

})
@Entity
@Table(name = "copy", indexes = {
        @Index(name = "copyName", columnList = "copyName", unique = true)
})
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCopy", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idStore", nullable = false)
    private Store idStore;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPricePlatform", nullable = false)
    private Priceplatform idPricePlatform;

    //!!! auto generate !!!.
    @Column(name = "copyName", nullable = false, length = 60)
    private String copyName;

    @NotNull
    @Min(1)
    @Column(name = "buyPrice", nullable = false)
    private float buyPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCopy status;

    //---->>

    @OneToMany(mappedBy = "idCopy")
    private Set<Copyorder> copyorders = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copy copy = (Copy) o;
        return id == copy.id && idStore == copy.idStore && idPricePlatform == copy.idPricePlatform && Float.compare(copy.buyPrice, buyPrice) == 0 && Objects.equals(copyName, copy.copyName) && Objects.equals(status, copy.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idStore, idPricePlatform, copyName, buyPrice, status);
    }

    public Set<Copyorder> getCopyorders() {
        return copyorders;
    }

    public void setCopyorders(Set<Copyorder> copyorders) {
        this.copyorders = copyorders;
    }

    public StatusCopy getStatus() {
        return status;
    }
    public String getStatusStr() {
        return status.getStatusCopy();
    }

    public void setStatus(StatusCopy status) {
        this.status = status;
    }
    //public void setStatus(String statusStr) {
    //    this.status = StatusCopy.strToEnum(statusStr);
    //}

    public float getBuyPrice() {
        return buyPrice;
    }
    public String getBuyPriceFormatStr() {
        return UtilityProcessing.floatToStrTwoDigit(this.buyPrice);
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCopyName() {
        //return copyName;
        return (
                String.valueOf(((this.idStore==null)? 0: this.idStore.getId()))+"."+
                String.valueOf(((this.idPricePlatform==null)? 0: this.idPricePlatform.getIdPlatform().getId()))+"."+
                String.valueOf(((this.idPricePlatform==null)? 0: this.idPricePlatform.getIdProduct().getId()))+"."+
                String.valueOf(CopyStaticBean.getCountCopy(this)+1)
                );
    }

    public void setCopyName() { //String copyName
        //this.copyName = copyName;
        this.copyName = (
                String.valueOf(((this.idStore==null)? 0: this.idStore.getId()))+"."+
                String.valueOf(((this.idPricePlatform==null)? 0: this.idPricePlatform.getIdPlatform().getId()))+"."+
                String.valueOf(((this.idPricePlatform==null)? 0: this.idPricePlatform.getIdProduct().getId()))+"."+
                String.valueOf(CopyStaticBean.getCountCopy(this)+1)
                );
    }

    public void setCopyNameFalse(){
        this.copyName = "0.0.0.0";
    }

    public Priceplatform getIdPricePlatform() {
        return idPricePlatform;
    }

    public void setIdPricePlatform(Priceplatform idPricePlatform) {
        this.idPricePlatform = idPricePlatform;
    }

    public Store getIdStore() {
        return idStore;
    }

    public void setIdStore(Store idStore) {
        this.idStore = idStore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusClassColor(){
        if(status.getStatusCopy().equals("disponible"))
            return "colorGreen";
        if(status.getStatusCopy().equals("louer"))
            return "colorOrange";
        return "colorRed";
    }
}