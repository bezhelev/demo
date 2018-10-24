package com.example.demo.servises;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RedirectController {

    @RequestMapping( "/contact/edit")
    String getAppsSfd() {
        return "webapps/editing_contact";
    }

    @RequestMapping( "/contact/")
    public String getIndex() {
        return "webapps/contact";
    }

    @RequestMapping( "/contact/js")
    public String getContactJs() {
        return "webapps/js/generalMethod.js";
    }

    @RequestMapping( "/contact/getJs")
    public String getGetContactJs() {
        return "webapps/js/getContact.js";
    }

    @RequestMapping( "/contact/editContactJS")
    public String getEditContactJs() {
        return "webapps/js/editContact.js";
    }

    @RequestMapping( "/phonecontact/getJs")
    public String getPhoneContactJs() {
        return "webapps/js/phoneContact.js";
    }

    @RequestMapping( "/attached/getJs")
    public String getAttachedJs() {
        return "webapps/js/attachedFiles.js";
    }


    @RequestMapping( "/base64js.min.js")
    public String getLibJs() {
        return "webapps/js/lib/base64js.min.js";
    }
}
