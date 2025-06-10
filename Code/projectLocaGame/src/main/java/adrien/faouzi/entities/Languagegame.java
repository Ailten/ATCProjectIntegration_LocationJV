package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name= "LanguageGame.SelectLanguageGameByIdProduct",
                query = "select lg from Languageproduct lp " +
                        "join Languagegame lg on (lp.idLanguage.id = lg.id) " +
                        "where (lp.idProduct.id = :idProduct)"),
        @NamedQuery(name= "LanguageGame.SelectLanguageGameAll",
                query = "select lg from Languagegame lg"),
        @NamedQuery(name= "LanguageGame.SelectLanguageGameByIdLanguage",
                query = "select lg from Languagegame lg " +
                        "where (lg.id = :idLanguage)")

})
@Entity
@Table(name = "languagegame")
public class Languagegame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLanguage", nullable = false)
    private int id;

    @Column(name = "languageName", nullable = false, length = 60)
    private String languageName;

    @OneToMany(mappedBy = "idLanguage")
    private Set<Languageproduct> languageproducts = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Languagegame that = (Languagegame) o;
        return id == that.id && Objects.equals(languageName, that.languageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageName);
    }

    public Set<Languageproduct> getLanguageproducts() {
        return languageproducts;
    }

    public void setLanguageproducts(Set<Languageproduct> languageproducts) {
        this.languageproducts = languageproducts;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}