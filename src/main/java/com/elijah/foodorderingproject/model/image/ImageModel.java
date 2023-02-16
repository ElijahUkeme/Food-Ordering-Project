package com.elijah.foodorderingproject.model.image;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class ImageModel {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    private String fileType;
    private String fileName;
    @Lob
    private byte[] data;

    public ImageModel(String fileType, String fileName, byte[] data) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.data = data;
    }
}
