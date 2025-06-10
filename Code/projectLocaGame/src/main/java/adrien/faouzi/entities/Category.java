package adrien.faouzi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name= "Category.SelectCategoryByIdProduct",
                query = "select c from Categoryproduct cp " +
                        "join Category c on (cp.idCategory.id = c.id) " +
                        "where (cp.idProduct.id = :idProduct)"),
        @NamedQuery(name= "Category.SelectCategoryAll",
                query = "select c from Category c"),
        @NamedQuery(name= "Category.SelectCategoryByIdCategory",
                query = "select c from Category c where (c.id = :idCategory)"),
        @NamedQuery(name= "Category.SelectCategoryByFilterOrderByStrAsc",
                query = "select distinct c from Category c " +
                        "where ( " +
                        "  ((lower(c.categoryName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'categoryname') then c.categoryName " +
                        "  else c.id " +
                        "end asc"),
        @NamedQuery(name= "Category.SelectCategoryByFilterOrderByStrDesc",
                query = "select distinct c from Category c " +
                        "where ( " +
                        "  ((lower(c.categoryName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'categoryname') then c.categoryName " +
                        "  else c.id " +
                        "end desc"),
        @NamedQuery(name= "Category.SelectCategoryByFilterOrderByNumAsc",
                query = "select distinct c from Category c " +
                        "where ( " +
                        "  ((lower(c.categoryName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then c.id " +
                        "  else c.id " +
                        "end asc"),
        @NamedQuery(name= "Category.SelectCategoryByFilterOrderByNumDesc",
                query = "select distinct c from Category c " +
                        "where ( " +
                        "  ((lower(c.categoryName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then c.id " +
                        "  else c.id " +
                        "end desc"),
        @NamedQuery(name= "Category.SelectJoin",
                query = "select cp from Categoryproduct cp where (cp.idCategory.id = :idCategory)")


})
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategory", nullable = false)
    private int id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü]{1,60}$")
    @Column(name = "categoryName", nullable = false, length = 60)
    private String categoryName;

    @OneToMany(mappedBy = "idCategory")
    private Set<Categoryproduct> categoryproducts = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }

    public Set<Categoryproduct> getCategoryproducts() {
        return categoryproducts;
    }

    public void setCategoryproducts(Set<Categoryproduct> categoryproducts) {
        this.categoryproducts = categoryproducts;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}