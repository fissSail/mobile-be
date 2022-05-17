package com.ruoyi.web.controller.common;

import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @Author: wxm
 * @Date: 2022/1/11 14:33
 * @Version 1.0
 */
public class Base64DecodeMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public Base64DecodeMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

        new FileOutputStream(dest).write(imgContent);

    }

    /**
     * base64转multipartFile *
     */
    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(","); //base64编码后的图片有头信息所以要分离出来   [0]data:image/png;base64, 图片内容为索引[1]
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            // 如果传递数据时没有传递类似于 data:image/png;base64 的图片头信息,则默认设置图片内容索引为0
            b = baseStrs.length > 1 ? decoder.decodeBuffer(baseStrs[1]) : decoder.decodeBuffer(baseStrs[0]);
//            b = decoder.decodeBuffer(baseStrs[1]); //取索引为1的元素进行处理
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        // 如果传递数据时没有传递类似于 data:image/png;base64 的图片头信息,则默认设置图片头信息
        String header = baseStrs.length > 1 ? baseStrs[0] : "data:image/png;base64";
        return new Base64DecodeMultipartFile(b, header);//处理过后的数据通过Base64DecodeMultipartFile转换为MultipartFile对象
    }

}