package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "LanguageProduct.deleteLanguageProduct",
                query = "delete from Languageproduct lp " +
                        "where ( " +
                        "  lp.idLanguage.id = :idLanguage and" +
                        "  lp.idProduct.id = :idProduct" +
                        ")")
})
@Entity
@Table(name = "languageproduct")
public class Languageproduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLanguageProduct", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idLanguage", nullable = false)
    private Languagegame idLanguage;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProduct", nullable = false)
    private Product idProduct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Languageproduct that = (Languageproduct) o;
        return idLanguage == that.idLanguage && idProduct == that.idProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLanguage, idProduct);
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public Languagegame getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Languagegame idLanguage) {
        this.idLanguage = idLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}