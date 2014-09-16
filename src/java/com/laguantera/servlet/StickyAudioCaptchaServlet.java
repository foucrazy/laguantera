package com.laguantera.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.servlet.CaptchaServletUtil;


public class StickyAudioCaptchaServlet extends HttpServlet {
    private static final long serialVersionUID = 4690256047223360039L;

    @Override protected void doGet(HttpServletRequest req,
        HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = (String)req.getParameter("action");
        
        if((action!=null)&&(!action.isEmpty())&&(action.equals(StickyCaptchaServlet.RESET))){
            session.removeAttribute(Captcha.NAME);
            session.removeAttribute(AudioCaptcha.NAME);
        }
        
        AudioCaptcha ac = new AudioCaptcha.Builder()
            .addAnswer()
            .addNoise()
            .build();

        req.getSession().setAttribute(AudioCaptcha.NAME, ac);
        CaptchaServletUtil.writeAudio(resp, ac.getChallenge());
    }

    @Override protected void doPost(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
