package com.vlc.maeummal.global.openAi;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64DecodedMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private final String header;

    public Base64DecodedMultipartFile(byte[] fileContent, String header) {
        this.fileContent = fileContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        String[] parts = header.split(";");
        String mimeType = parts[0].split(":")[1];
        System.out.println(mimeType.trim());
        return mimeType.trim(); // MIME 타입 반환
    }


    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("transferTo() method is not supported");
    }
}
