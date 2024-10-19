package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "signatures")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private String url;
    private LocalDate uploadDate;

    @Lob
    private byte[] contents;

    @OneToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public Signature(String fileName, String contentType, String url, byte[] contents) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
        this.contents = contents;
    }

    public Signature() {

    }
}