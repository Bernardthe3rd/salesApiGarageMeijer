package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.exceptions.SignatureException;
import nl.garagemeijer.salesapi.mappers.SignatureMapper;
import nl.garagemeijer.salesapi.models.Signature;
import nl.garagemeijer.salesapi.repositories.SignatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class SignatureService {

    private final SignatureRepository signatureRepository;
    private final SignatureMapper signatureMapper;

    public SignatureService(SignatureRepository signatureRepository, SignatureMapper signatureMapper) {
        this.signatureRepository = signatureRepository;
        this.signatureMapper = signatureMapper;
    }

    public SignatureOutputDto storeSignature(MultipartFile file, String url) {
        try {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] bytes = file.getBytes();
            Signature signature = new Signature(originalFilename, contentType, url, bytes);
            signature.setUploadDate(LocalDate.now());
            return signatureRepository.save(signature);
        } catch (IOException e) {
            throw new SignatureException("Fout bij het opslaan van de handtekening", e);
        }

    }

    public SignatureOutputDto getSignatureById(Long id) {
        return signatureRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Signature with id: " + id + " not found"));
    }
}
