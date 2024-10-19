package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.models.Signature;
import nl.garagemeijer.salesapi.repositories.SignatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class SignatureService {

    private final SignatureRepository signatureRepository;

    public SignatureService(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    public Signature storeSignature(MultipartFile file, String url) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();

        Signature signature = new Signature(originalFilename, contentType, url, bytes);
        signature.setUploadDate(LocalDate.now());

        return signatureRepository.save(signature);
    }

    public Signature getSignatureById(Long id) {
        return signatureRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Signature with id: " + id + " not found"));
    }
}
