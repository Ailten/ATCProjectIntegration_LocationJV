package adrien.faouzi.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDocument", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idDocumentType", nullable = false)
    private Documenttype idDocumentType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order idOrder;

    @Column(name = "numberDocument", nullable = false)
    private int numberDocument;

    @Column(name = "documentDate", nullable = false)
    private LocalDateTime documentDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id && idDocumentType == document.idDocumentType && idOrder == document.idOrder && numberDocument == document.numberDocument && Objects.equals(documentDate, document.documentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDocumentType, idOrder, numberDocument, documentDate);
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public int getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(int numberDocument) {
        this.numberDocument = numberDocument;
    }

    public Order getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Order idOrder) {
        this.idOrder = idOrder;
    }

    public Documenttype getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Documenttype idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}