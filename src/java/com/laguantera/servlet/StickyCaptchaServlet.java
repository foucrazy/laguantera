package com.laguantera.servlet;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.servlet.CaptchaServletUtil;

public class StickyCaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 40913456229L;
    
    private static int _width = 200;
    private static int _height = 50;
    
    private static long _ttl = 1000 * 60 * 10;
    
    public static final String RESET = "reset";
    public static final String CHANGE = "change";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    	if (getInitParameter("captcha-height") != null) {
    		_height = Integer.valueOf(getInitParameter("captcha-height"));
    	}
    	
    	if (getInitParameter("captcha-width") != null) {
    		_width = Integer.valueOf(getInitParameter("captcha-width"));
    	}
    	
        if (getInitParameter("ttl") != null) {
            _ttl = Long.valueOf(getInitParameter("ttl"));
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        HttpSession session = req.getSession();
        String action = (String)req.getParameter("action");
        Captcha captcha = null;

        if((action!=null)&&(!action.isEmpty())&&(action.equals(StickyCaptchaServlet.RESET))){
            session.removeAttribute(Captcha.NAME);
            session.removeAttribute(AudioCaptcha.NAME);
        }
        
        
        if (session.getAttribute(Captcha.NAME) == null) {
            captcha = buildAndSetCaptcha(session);
        }

        captcha = (Captcha) session.getAttribute(Captcha.NAME);
        if (shouldExpire(captcha)) {
            captcha = buildAndSetCaptcha(session);
        }

        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }

    private Captcha buildAndSetCaptcha(HttpSession session) {
        Captcha captcha = new Captcha.Builder(_width, _height)
            .addText()
            .gimp()
            .addBorder()
            .addNoise()
            .addBackground()
            .build();

        session.setAttribute(Captcha.NAME, captcha);
        return captcha;
    }

    static void setTtl(long ttl) {
        if (ttl < 0) {
            ttl = 0;
        }

        _ttl = ttl;
    }

    static long getTtl() {
        return _ttl;
    }

    static boolean shouldExpire(Captcha captcha) {
        long ts = captcha.getTimeStamp().getTime();
        long now = new Date().getTime();
        long diff = now - ts;

        return diff >= _ttl;
    }
}
