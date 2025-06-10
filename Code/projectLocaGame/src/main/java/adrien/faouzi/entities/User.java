package adrien.faouzi.entities;

import adrien.faouzi.managedBeans.ConnectionBean;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;

@NamedQueries(value = {
        @NamedQuery(name = "User.SelectUserConnexion", query = "SELECT u from User u " +
                "where u.mail = :mail"),
        @NamedQuery(name = "User.SelectUserByFilterAsc", query = "SELECT u from User u " +
                " where ((lower(u.lastName )like concat('%', :resseachWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :resseachWord, '%')) or " +
                " (lower(u.phone )like concat('%', :resseachWord, '%')) or " +
                " (lower(u.mail )like concat('%', :resseachWord, '%')) or" +
                " (lower(u.idRole.roleName) like concat('%', :resseachWord, '%'))) " +
                " order by case " +
                " when (:orderBy like 'lastName') then u.lastName " +
                " when (:orderBy like 'firstName') then u.lastName " +
                " when (:orderBy like 'phone') then u.phone " +
                " when (:orderBy like 'mail') then u.mail " +
                " when (:orderBy like 'roleNome') then u.idRole.roleName " +
                " when (:orderBy like 'enable') then u.enable " +
                " else u.id " +
                " end asc "),
        @NamedQuery(name = "User.SelectUserByFilterDesc", query = "SELECT u from User u " +
                " where ((lower(u.lastName )like concat('%', :resseachWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :resseachWord, '%')) or " +
                " (lower(u.phone )like concat('%', :resseachWord, '%')) or " +
                " (lower(u.mail )like concat('%', :resseachWord, '%')) or" +
                " (lower(u.idRole.roleName) like concat('%', :resseachWord, '%'))) " +
                " order by case " +
                " when (:orderBy like 'lastName') then u.lastName " +
                " when (:orderBy like 'firstName') then u.lastName " +
                " when (:orderBy like 'phone') then u.phone " +
                " when (:orderBy like 'mail') then u.mail " +
                " when (:orderBy like 'roleNome') then u.idRole.roleName " +
                " when (:orderBy like 'enable') then u.enable " +
                " else u.id " +
                " end desc ")
})

@Entity
@Table(name = "user", indexes = {
        @Index(name = "mail", columnList = "mail", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role idRole;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{1,60}$")
    @Column(name = "lastName", nullable = false, length = 60)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{1,60}$")
    @Column(name = "firstName", nullable = false, length = 60)
    private String firstName;

    @NotNull
    @Column(name = "dateOfBirth", nullable = false)
    private LocalDateTime dateOfBirth;

    @NotNull
    @Pattern(regexp = "^[+][0-9]{1,4}[ ]{1}[0-9]{2,4}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}$")
    @Column(name = "phone", length = 16)
    private String phone;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registrationDate", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "enable", nullable = false)
    private boolean enable = true;

    @OneToMany(mappedBy = "idUser")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUser")
    private Set<Address> addresses = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && idRole == user.idRole && enable == user.enable && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(phone, user.phone) && Objects.equals(mail, user.mail) && Objects.equals(password, user.password) && Objects.equals(registrationDate, user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idRole, lastName, firstName, dateOfBirth, phone, mail, password, registrationDate, enable);
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getIdRole() {
        return idRole;
    }

    public void setIdRole(Role idRole) {
        this.idRole = idRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnableFormatStr(){
        return ((this.enable)? "Actif": "Inactif");
    }
    public String getEnableClassColor(){
        return ((this.enable)? "colorGreen": "colorRed");
    }

    /**
     * field permissionrole
     */
    @Transient
    public List<Permissionrole> listPermissionRole;

    @Transient
    public List<Permissionrole> getListPermissionRole() {
        if (this.listPermissionRole == null)
            ConnectionBean.initListPermissionRole(this);
        return this.listPermissionRole;
    }

    /**
     * Method to verify user access
     * @param permissionName
     * @return
     */
    @Transient
    public boolean verifyPermission(String permissionName)
    {
        return this.getListPermissionRole().stream()
                .filter(pr -> pr.getIdPermission().getPermissionName().equals(permissionName))
                .findFirst()
                .orElse(null) != null;
    }
}