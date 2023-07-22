package com.Petching.petching.restDocs.global.helper;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestPartDescriptor;

import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;

public interface S3ControllerTestHelper extends ControllerTestHelper {
    String DEFAULT_URL = "/api/s3";
    String UPLOAD_URI = "/uploads";
    String DELETE_URI = "/delete";

    default String getDefaultUrl() {
        return DEFAULT_URL;
    }

    default String getUploadUri() {
        return DEFAULT_URL + UPLOAD_URI;
    }

    default String getDeleteUri() {
        return DEFAULT_URL + DELETE_URI;
    }


    default List<RequestPartDescriptor> getDefaultS3PostRequestParts() {

        return List.of(
                partWithName("files").description("올릴 파일(리스트)"),
                partWithName("uploadTo").description("올릴 경로. e.g: boards")
        );

    }

    default List<FieldDescriptor> getDefaultS3UploadResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("[].originalFileName")).type(JsonFieldType.STRING).description("업로드될 파일의 원래 이름"),
                fieldWithPath(parentPath.concat("[].uploadFileName")).type(JsonFieldType.STRING).description("업로드된 파일의 바뀐 이름"),
                fieldWithPath(parentPath.concat("[].uploadFilePath")).type(JsonFieldType.STRING).description("업로드된 파일의 경로"),
                fieldWithPath(parentPath.concat("[].uploadFileUrl")).type(JsonFieldType.STRING).description("업로드된 파일의 Url")
        );
    }
}
