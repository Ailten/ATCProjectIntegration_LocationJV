package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "CategoryProduct.deleteCategoryProduct",
                query = "delete from Categoryproduct cp " +
                        "where ( " +
                        "  cp.idCategory.id = :idCategory and" +
                        "  cp.idProduct.id = :idProduct" +
                        ")")
})
@Entity
@Table(name = "categoryproduct")
public class Categoryproduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoryProduct", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCategory", nullable = false)
    private Category idCategory;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProduct", nullable = false)
    private Product idProduct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoryproduct that = (Categoryproduct) o;
        return idCategory == that.idCategory && idProduct == that.idProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, idProduct);
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public Category getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}