package com.project.downloadfiles.service;

import com.project.downloadfiles.entity.Attachment;
import com.project.downloadfiles.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImpl implements AttachmentService{
    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        //Nombre del archivo
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            //Si cuenca con algun caracter invalido
            if (fileName.contains("..")){
                throw new Exception("File name contains invalid path sequence" + fileName);
            }

            Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());
            return attachmentRepository.save(attachment);

        } catch (Exception e){
            throw new Exception("Could not save file: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId)
                        .orElseThrow(() -> new Exception("File not found with id: "+ fileId));
    }
}
