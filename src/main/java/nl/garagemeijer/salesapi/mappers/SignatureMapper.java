package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.models.Signature;
import org.springframework.stereotype.Component;

@Component
public class SignatureMapper {

    private final SaleMapper saleMapper;

    public SignatureMapper(SaleMapper saleMapper) {
        this.saleMapper = saleMapper;
    }

    public SignatureOutputDto signatureToOutputDto(Signature signature) {
        var dto = new SignatureOutputDto();

        dto.setId(signature.getId());
        dto.setFileName(signature.getFileName());
        dto.setContentType(signature.getContentType());
        dto.setUrl(signature.getUrl());
        dto.setUploadDate(signature.getUploadDate());
        dto.setContents(signature.getContents());
        dto.setSale(saleMapper.saleTosaleOutputDto(signature.getSale()));

        return dto;
    }
}
