package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "copyorder")
public class Copyorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCopyOrder", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCopy", nullable = false)
    private Copy idCopy;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order idOrder;

    @Column(name = "copyPrice")
    private float copyPrice;

    @Column(name = "returnDestroy", nullable = false)
    private boolean returnDestroy = false;

    @Column(name = "penalityPrice")
    private float penalityPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copyorder copyorder = (Copyorder) o;
        return idCopy == copyorder.idCopy && idOrder == copyorder.idOrder && returnDestroy == copyorder.returnDestroy && Objects.equals(copyPrice, copyorder.copyPrice) && Objects.equals(penalityPrice, copyorder.penalityPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCopy, idOrder, copyPrice, returnDestroy, penalityPrice);
    }

    public float getPenalityPrice() {
        return penalityPrice;
    }

    public void setPenalityPrice(float penalityPrice) {
        this.penalityPrice = penalityPrice;
    }

    public boolean getReturnDestroy() {
        return returnDestroy;
    }

    public void setReturnDestroy(boolean returnDestroy) {
        this.returnDestroy = returnDestroy;
    }

    public float getCopyPrice() {
        return copyPrice;
    }

    public void setCopyPrice(float copyPrice) {
        this.copyPrice = copyPrice;
    }

    public Order getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Order idOrder) {
        this.idOrder = idOrder;
    }

    public Copy getIdCopy() {
        return idCopy;
    }

    public void setIdCopy(Copy idCopy) {
        this.idCopy = idCopy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}