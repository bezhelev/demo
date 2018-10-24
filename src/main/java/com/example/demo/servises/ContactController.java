package com.example.demo.servises;

import com.example.demo.dao.DAOContactInterface;
import com.example.demo.dao.DAOContacts;
import com.example.demo.dao.DAOInterface;
import com.example.demo.dao.model.Contacts;
import com.example.demo.logiclvl.EmailSender;
import com.example.demo.logiclvl.FileFromServer;
import com.example.demo.logiclvl.ViewContact;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/contact")
public class ContactController {
    DAOContactInterface<Contacts> daoInterface = new DAOContacts();

    @RequestMapping(value = "/get/{number},{count}", method = RequestMethod.GET)
    public @ResponseBody
    List getContacts(@PathVariable int number,@PathVariable int count) {
        ViewContact viewContact = new ViewContact();
        return viewContact.remakeEntityList(daoInterface.getAll(number,count));
    }

    @RequestMapping("/getSolo/{id}")
    Contacts getContact(@PathVariable int id) {
        return daoInterface.getEntityById(id);
    }

    @RequestMapping("/delete/{id}")
    void deleteContact(@PathVariable int id) {
        daoInterface.deleteEntity(id);
    }

    @PostMapping("/add/")
    void addContact(@Valid @RequestBody Contacts contacts) {
        contacts.setId(daoInterface.getLastIdRecord());
        daoInterface.addEntity(contacts);
    }

    @PostMapping("/update/")
    void updateContact(@Valid @RequestBody Contacts contacts) {
        daoInterface.updateEntity(contacts);
    }

    @RequestMapping("/getCount/")
    int getCountRecord(){
        return daoInterface.getCountRecord();
    }

    @RequestMapping("edit/getLastId/")
    int getLastIdRecord(){
        return daoInterface.getLastIdRecord();
    }

    @PostMapping("/getfiltered/{birthday}")
    public @ResponseBody
    List getFilteredContacts(@PathVariable Date birthday,@Valid @RequestBody Contacts contacts) {
        ViewContact viewContact = new ViewContact();
        return viewContact.remakeEntityList(daoInterface.getFilteredData(contacts,birthday));
    }

    @PostMapping("/sendemail/")
    String send(@Valid @RequestBody  List<String> textArray){
        EmailSender emailSender = new EmailSender(textArray);
        emailSender.sendEmailToUser();
        return "correct";
    }

    @GetMapping("/getaavatars/")
    public @ResponseBody
    String getAvatars(){
        FileFromServer fls = new FileFromServer();
        return fls.avatarToBase64("none");
    }

}
