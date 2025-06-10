package adrien.faouzi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name = "Platform.SelectPlatformAll",
                query = "select p from Platform p"),
        @NamedQuery(name = "Platform.SelectPlatformByIdPlatform",
                query = "select p from Platform p where (p.id = :idPlatform)"),
        @NamedQuery(name= "Platform.SelectPlatformByFilterOrderByStrAsc",
                query = "select distinct p from Platform p " +
                        "where ( " +
                        "  ((lower(p.platformName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'platformname') then p.platformName " +
                        "  else p.id " +
                        "end asc"),
        @NamedQuery(name= "Platform.SelectPlatformByFilterOrderByStrDesc",
                query = "select distinct p from Platform p " +
                        "where ( " +
                        "  ((lower(p.platformName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'platformname') then p.platformName " +
                        "  else p.id " +
                        "end desc"),
        @NamedQuery(name= "Platform.SelectPlatformByFilterOrderByNumAsc",
                query = "select distinct p from Platform p " +
                        "where ( " +
                        "  ((lower(p.platformName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then p.id " +
                        "  else p.id " +
                        "end asc"),
        @NamedQuery(name= "Platform.SelectPlatformByFilterOrderByNumDesc",
                query = "select distinct p from Platform p " +
                        "where ( " +
                        "  ((lower(p.platformName) like concat('%', :researchWord, '%'))) " +
                        ") " +
                        "order by case " +
                        "  when (:orderBy like 'id') then p.id " +
                        "  else p.id " +
                        "end desc"),
        @NamedQuery(name= "Platform.SelectJoin",
                query = "select pp from Priceplatform pp where (pp.idPlatform.id = :idPlatform)")
})
@Entity
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlatform", nullable = false)
    private int id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü]{1,60}$")
    @Column(name = "platformName", nullable = false, length = 60)
    private String platformName;

    @OneToMany(mappedBy = "idPlatform")
    private Set<Priceplatform> priceplatforms = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return id == platform.id;// && Objects.equals(platformName, platform.platformName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, platformName);
    }

    public Set<Priceplatform> getPriceplatforms() {
        return priceplatforms;
    }

    public void setPriceplatforms(Set<Priceplatform> priceplatforms) {
        this.priceplatforms = priceplatforms;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}