package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.signature.SignatureInputDto;
import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.SignatureMapper;
import nl.garagemeijer.salesapi.models.Signature;
import nl.garagemeijer.salesapi.repositories.SignatureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SignatureService {

    private final SignatureRepository signatureRepository;
    private final SignatureMapper signatureMapper;

    public SignatureService(SignatureRepository signatureRepository, SignatureMapper signatureMapper) {
        this.signatureRepository = signatureRepository;
        this.signatureMapper = signatureMapper;
    }

    public SignatureOutputDto storeSignature(SignatureInputDto file, String url) {
        Signature signature = new Signature(file.getOriginalFileName(), file.getContentType(), url, file.getContents());
        signature.setUploadDate(LocalDate.now());
        return signatureMapper.signatureToOutputDto(signatureRepository.save(signature));
    }

    public SignatureOutputDto getSignatureById(Long id) {
        Optional<Signature> optionalSignature = signatureRepository.findById(id);
        if (optionalSignature.isPresent()) {
            return signatureMapper.signatureToOutputDto(optionalSignature.get());
        } else {
            throw new RecordNotFoundException("Signature with id " + id + " not found");
        }
    }
}
