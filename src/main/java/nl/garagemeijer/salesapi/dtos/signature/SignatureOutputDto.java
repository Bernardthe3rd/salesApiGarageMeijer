package nl.garagemeijer.salesapi.dtos.signature;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignatureOutputDto {

    private Long id;
    private String fileName;
    private String contentType;
    private String url;
    private LocalDate uploadDate;
    private byte[] contents;

}
