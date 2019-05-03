package com.zhilingsd.base.common.utils;

import com.alibaba.fastjson.JSON;
import com.zhilingsd.base.common.result.CollectionResult;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.function.Consumer;


public class SpringWebFileUtil {

    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_RESPONSE_MESSAGE = "response-message";
    private static final String MEDIA_TYPE_OCTET_STREAM = "application/octet-stream";
    private static final String MEDIA_TYPE_JSON = "application/json;charset=UTF-8";
    private static final String CHARACTER_ENCODING = "UTF-8";

    public static ResponseEntity<Resource> download(Resource file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(file.getFilename(), CHARACTER_ENCODING));
        headers.add(HEADER_RESPONSE_MESSAGE, URLEncoder.encode(JSON.toJSONString(CollectionResult.success()), CHARACTER_ENCODING));
        return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_OCTET_STREAM)).body(file);
    }

    public static ResponseEntity<Resource> download(Resource file, String realFileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(realFileName, CHARACTER_ENCODING));
        headers.add(HEADER_RESPONSE_MESSAGE, URLEncoder.encode(JSON.toJSONString(CollectionResult.success()), CHARACTER_ENCODING));
        return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_OCTET_STREAM)).body(file);
    }

    public static ResponseEntity<byte[]> download(byte[] fileByeArray, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName, CHARACTER_ENCODING));
        headers.add(HEADER_RESPONSE_MESSAGE, URLEncoder.encode(JSON.toJSONString(CollectionResult.success()), CHARACTER_ENCODING));
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_OCTET_STREAM)).body(fileByeArray);
    }

    public static void uploadStream(RequestEntity<ByteArrayResource> req, Consumer<ByteArrayResource> consumer) {
        consumer.accept(req.getBody());
    }


    public static ResponseEntity<byte[]> responseImage(byte[] fileByeArray) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png")).body(fileByeArray);
    }

    public static ResponseEntity<Resource> responseImage(Resource file) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png")).body(file);
    }

    public static ResponseEntity<Resource> ResourceDownloadFail(CollectionResult result) {
        String resultString = JSON.toJSONString(result);
        HttpHeaders headers = new HttpHeaders();
        String encodeResult;
        try {
            encodeResult = URLEncoder.encode(resultString, CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            encodeResult = resultString;
        }
        headers.add(HEADER_RESPONSE_MESSAGE, encodeResult);
        ByteArrayResource resource = new ByteArrayResource(resultString.getBytes());
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_JSON)).body(resource);
    }

    public static ResponseEntity<byte[]> downloadFail(CollectionResult result) {
        String resultString = JSON.toJSONString(result);
        HttpHeaders headers = new HttpHeaders();
        String encodeResult;
        try {
            encodeResult = URLEncoder.encode(resultString, CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            encodeResult = resultString;
        }
        headers.add(HEADER_RESPONSE_MESSAGE, encodeResult);
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_JSON)).body(resultString.getBytes());
    }

    public static byte[] toByteArray(InputStream in, ByteArrayOutputStream out) throws IOException {

        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

}
