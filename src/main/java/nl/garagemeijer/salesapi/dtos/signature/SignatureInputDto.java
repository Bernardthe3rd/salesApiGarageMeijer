package nl.garagemeijer.salesapi.dtos.signature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureInputDto {

    private String originalFileName;
    private String contentType;

}
