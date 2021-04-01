package com.chitra.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class GenericUtils {

    public String b64Encode(String encode){
        return Base64Utils.encodeToString(encode.getBytes(StandardCharsets.UTF_8));
    }

}
