package adrien.faouzi.entities;

import adrien.faouzi.Interface.IEntity;
import adrien.faouzi.enumeration.MultiPlayer;
import adrien.faouzi.enumeration.Pegi;
import adrien.faouzi.managedBeans.ProductStaticBean;
import adrien.faouzi.utility.UtilityProcessing;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;

@NamedQueries(value = {
        @NamedQuery(name= "Product.SelectProductAll", query = "select p from Product p"),
        @NamedQuery(name= "Product.SelectProductByIdProduct", query = "select p from Product p where (p.id = :idProduct)"),

        @NamedQuery(name= "Product.SelectProductByFilterOrderByStrAsc",
                query = "select distinct p from Product p " +
                        "join Categoryproduct cp on (cp.idProduct = p) " +
                        "join fetch cp.idCategory c " +
                        "join Languageproduct lp on (lp.idProduct = p) " +
                        "join fetch lp.idLanguage lg " +
                        "where ( " +
                        "  ((lower(p.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(p.idEditor.editorName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.categoryName)) like concat('%', :researchWord, '%')) or "+ //condition for filter all category.
                        "  ((lower(p.multiPlayer) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(lg.languageName)) like concat('%', :researchWord, '%')) "+ //condition for filter all language.
                        ") "+
                        "order by case " +
                        "  when (:orderBy like 'productname') then p.productName " +
                        "  when (:orderBy like 'editor') then p.idEditor.editorName " +
                        "  when (:orderBy like 'multiplayer') then p.multiPlayer " +
                        "  when (:orderBy like 'pegi') then p.pegi "+
                        "  when (:orderBy like 'daterelease') then p.releaseDate "+
                        "  when (:orderBy like 'enable') then p.enable "+
                        "  else p.id " +
                        "end asc" //+
                //"* case " +
                //"  when (:ascOrDesc like 'asc') then 1 " +
                //"  else -1 " +
                //"end"
        ),
        @NamedQuery(name= "Product.SelectProductByFilterOrderByStrDesc",
                query = "select distinct p from Product p " +
                        "join Categoryproduct cp on (cp.idProduct = p) " +
                        "join fetch cp.idCategory c " +
                        "join Languageproduct lp on (lp.idProduct = p) " +
                        "join fetch lp.idLanguage lg " +
                        "where ( " +
                        "  ((lower(p.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(p.idEditor.editorName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.categoryName)) like concat('%', :researchWord, '%')) or "+ //condition for filter all category.
                        "  ((lower(p.multiPlayer) like concat('%', :researchWord, '%'))) or " +
                        "  (lower(lg.languageName)) like concat('%', :researchWord, '%') "+ //condition for filter all language.
                        ") "+
                        "order by case " +
                        "  when (:orderBy like 'productname') then p.productName " +
                        "  when (:orderBy like 'editor') then p.idEditor.editorName " +
                        "  when (:orderBy like 'multyplayer') then p.multiPlayer " +
                        "  when (:orderBy like 'pegi') then p.pegi "+
                        "  when (:orderBy like 'daterelease') then p.releaseDate "+
                        "  when (:orderBy like 'enable') then p.enable "+
                        "  else p.id " +
                        "end desc" //+
                //"* case " +
                //"  when (:ascOrDesc like 'asc') then 1 " +
                //"  else -1 " +
                //"end"
        ),
        @NamedQuery(name= "Product.SelectProductByFilterOrderByNumAsc",
                query = "select distinct p from Product p " +
                        "join Categoryproduct cp on (cp.idProduct = p) " +
                        "join fetch cp.idCategory c " +
                        "join Languageproduct lp on (lp.idProduct = p) " +
                        "join fetch lp.idLanguage lg " +
                        "where ( " +
                        "  ((lower(p.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(p.idEditor.editorName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.categoryName)) like concat('%', :researchWord, '%')) or "+ //condition for filter all category.
                        "  ((lower(p.multiPlayer) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(lg.languageName)) like concat('%', :researchWord, '%')) "+ //condition for filter all language.
                        ") "+
                        "order by case " +
                        "  when (:orderBy like 'id') then p.id "+
                        //"  when (:orderBy like 'daterelease') then p.releaseDate "+
                        "  else p.id " +
                        "end asc" //+
                //"* case " +
                //"  when (:ascOrDesc like 'asc') then 1 " +
                //"  else -1 " +
                //"end"
        ),
        @NamedQuery(name= "Product.SelectProductByFilterOrderByNumDesc",
                query = "select distinct p from Product p " +
                        "join Categoryproduct cp on (cp.idProduct = p) " +
                        "join fetch cp.idCategory c " +
                        "join Languageproduct lp on (lp.idProduct = p) " +
                        "join fetch lp.idLanguage lg " +
                        "where ( " +
                        "  ((lower(p.productName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(p.idEditor.editorName) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(c.categoryName)) like concat('%', :researchWord, '%')) or "+ //condition for filter all category.
                        "  ((lower(p.multiPlayer) like concat('%', :researchWord, '%'))) or " +
                        "  ((lower(lg.languageName)) like concat('%', :researchWord, '%')) "+ //condition for filter all language.
                        ") "+
                        "order by case " +
                        "  when (:orderBy like 'id') then p.id "+
                        //"  when (:orderBy like 'daterelease') then p.releaseDate "+
                        "  else p.id " +
                        "end desc" //+
                //"* case " +
                //"  when (:ascOrDesc like 'asc') then 1 " +
                //"  else -1 " +
                //"end"
        ),
        @NamedQuery(name= "Product.SelectJoinCategoryProduct",
                query = "select cp from Categoryproduct cp where (cp.idProduct.id = :idProduct)"),
        @NamedQuery(name= "Product.SelectJoinLanguageProduct",
                query = "select lp from Languageproduct lp where (lp.idProduct.id = :idProduct)"),
        @NamedQuery(name= "Product.SelectJoinPricePlatform",
                query = "select pp from Priceplatform pp where (pp.idProduct.id = :idProduct)")


})
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduct", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idEditor", nullable = false)
    private Editor idEditor;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü:_()\"&%'+!?.-]{1,60}$")
    @Column(name = "productName", nullable = false, length = 60)
    private String productName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pegi", nullable = false)
    private Pegi pegi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "multiPlayer", nullable = false)
    private MultiPlayer multiPlayer;

    @NotNull
    @Column(name = "releaseDate", nullable = false)
    private LocalDateTime releaseDate;

    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü:_()\"&%'+!?.-]{0,255}$")
    @Column(name = "description")
    private String description;

    @Column(name = "enable", nullable = false)
    private boolean enable = false;

    @OneToMany(mappedBy = "idProduct")
    private Set<Priceplatform> priceplatforms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProduct")
    private Set<Languageproduct> languageproducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProduct")
    private Set<Categoryproduct> categoryproducts = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id; // && idEditor == product.idEditor && enable == product.enable && Objects.equals(productName, product.productName) && Objects.equals(pegi, product.pegi) && Objects.equals(multiPlayer, product.multiPlayer) && Objects.equals(releaseDate, product.releaseDate) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idEditor, productName, pegi, multiPlayer, releaseDate, description, enable);
    }

    public Set<Categoryproduct> getCategoryproducts() {
        return categoryproducts;
    }

    public void setCategoryproducts(Set<Categoryproduct> categoryproducts) {
        this.categoryproducts = categoryproducts;
    }

    public Set<Languageproduct> getLanguageproducts() {
        return languageproducts;
    }

    public void setLanguageproducts(Set<Languageproduct> languageproducts) {
        this.languageproducts = languageproducts;
    }

    public Set<Priceplatform> getPriceplatforms() {
        return priceplatforms;
    }

    public void setPriceplatforms(Set<Priceplatform> priceplatforms) {
        this.priceplatforms = priceplatforms;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MultiPlayer getMultiPlayer() { return multiPlayer; }

    /*
    public String getMultiPlayer() {
        if(multiPlayer==null)
            return "---";
        return multiPlayer.getMultiPlayer();
    }
    */

    public void setMultiPlayer(MultiPlayer multiPlayer) {
        this.multiPlayer = multiPlayer;
    }

    public Pegi getPegi() { return pegi; }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Editor getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Editor idEditor) {
        this.idEditor = idEditor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    //get pegi format.
    public String getEnableFormatStr(){
        return ((this.enable)? "disponible": "indisponible");
    }


    //list of all category get by table join.
    @Transient
    private List<Category> listCategory;
    public List<Category> getListCategory(){
        if(this.listCategory == null)
            ProductStaticBean.initListCategory(this);
        return this.listCategory;
    }
    public void setListCategory(List<Category> listCategory){
        this.listCategory = listCategory;
    }

    //list of all language game get by table join.
    @Transient
    private List<Languagegame> listLanguageGame;
    public List<Languagegame> getListLanguageGame(){
        if(this.listLanguageGame == null)
            ProductStaticBean.initListLanguageGame(this);
        return this.listLanguageGame;
    }
    public void setListLanguageGame(List<Languagegame> listLanguageGame){
        this.listLanguageGame = listLanguageGame;
    }



    //multiplayer seter string.
    public void setMultiPlayer(String multiPlayer) {
        this.multiPlayer = MultiPlayer.strToEnum(multiPlayer);
    }



    //pegi format str.
    public String getPegiFormatStr(){
        if(pegi==null)
            return "0";
        return pegi.getPegi();
    }
    public void setPegiFormatStr(String pegi) {
        this.pegi = Pegi.intToEnum(Integer.parseInt(pegi));
    }


    //date str.
    public String getReleaseDateFormatStr(){
        return UtilityProcessing.localdatetimeInPattern(this.releaseDate);
    }

    //enable class.
    public String getEnableClassColor(){
        return ((this.enable)? "colorGreen": "colorRed");
    }
}
