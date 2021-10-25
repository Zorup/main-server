package com.example.socialpost.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="IMAGE_TB")
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long imageId;

    @Column
    private String type;

    @Lob
    private byte[] data;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="postId")
    private Post post;

    @Column
    private Integer seq;

    public static ImageBuilder of(ImageRequest ir) {
        ImageBuilder imageBuilder = null;
        try {
            imageBuilder = new ImageBuilder()
                    .type(ir.blobImage.getContentType())
                    .data(ir.blobImage.getBytes())
                    .seq(ir.getSeq());
        } catch (IOException e){
            e.printStackTrace();
        }

        return imageBuilder;
    }


    @Data
    public static class ImageRequest {  // 요청 파라미터 받을때 사용
        int seq;
        MultipartFile blobImage;
    }
    @Data
    public static class ImageRequestForm {  // 요청 파라미터 받을때 사용
        List<ImageRequest> images;
    }

    @Data
    @Builder
    public static class ImageResponse { // 응답에 사용
        private int seq;
        String type;
        byte[] data;

        public static ImageResponse of(Image image){
            return new ImageResponseBuilder()
                    .seq(image.getSeq())
                    .type(image.getType())
                    .data(image.getData()).build();
        }
    }


}
