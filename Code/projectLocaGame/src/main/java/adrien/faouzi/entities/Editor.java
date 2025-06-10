package adrien.faouzi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name= "Editor.SelectEditorAll", query = "select e from Editor e"),
        @NamedQuery(name= "Editor.SelectEditorByIdEditor", query = "select e from Editor e where (e.id = :idEditor)"),
        @NamedQuery(name= "Editor.SelectEditorByFilterOrderByStrAsc",
                query = "select distinct e from Editor e " +
                        "where ( " +
                        "  ((lower(e.editorName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'editorname') then e.editorName " +
                        "  else e.id " +
                        "end asc"),
        @NamedQuery(name= "Editor.SelectEditorByFilterOrderByStrDesc",
                query = "select distinct e from Editor e " +
                        "where ( " +
                        "  ((lower(e.editorName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'editorname') then e.editorName " +
                        "  else e.id " +
                        "end desc"),
        @NamedQuery(name= "Editor.SelectEditorByFilterOrderByNumAsc",
                query = "select distinct e from Editor e " +
                        "where ( " +
                        "  ((lower(e.editorName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then e.id " +
                        "  else e.id " +
                        "end asc"),
        @NamedQuery(name= "Editor.SelectEditorByFilterOrderByNumDesc",
                query = "select distinct e from Editor e " +
                        "where ( " +
                        "  ((lower(e.editorName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then e.id " +
                        "  else e.id " +
                        "end desc"),
        @NamedQuery(name= "Editor.SelectJoin",
                query = "select p from Product p where (p.idEditor.id = :idEditor)")
})
@Entity
@Table(name = "editor")
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEditor", nullable = false)
    private int id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü]{1,60}$")
    @Column(name = "editorName", nullable = false, length = 60)
    private String editorName;

    @OneToMany(mappedBy = "idEditor")
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editor editor = (Editor) o;
        return id == editor.id && Objects.equals(editorName, editor.editorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, editorName);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}