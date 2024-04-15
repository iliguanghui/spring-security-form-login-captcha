package com.lgypro.controller;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/app/api")
public class CaptchaController {
    @Autowired
    private Producer producer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        request.getSession().setAttribute("captcha", capText);
        BufferedImage image = producer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
