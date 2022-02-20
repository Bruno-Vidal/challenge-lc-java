package br.com.cristal.moviegame.entrypoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;

@Controller
@RequestMapping("/")
public class RedirectController {

    @GetMapping
    public void redirect(HttpServletResponse httpServletResponse) throws UnknownHostException {

        httpServletResponse.setHeader("Location","swagger-ui.html");
        httpServletResponse.setStatus(302);
    }


}
