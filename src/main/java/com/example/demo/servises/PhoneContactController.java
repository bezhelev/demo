package com.example.demo.servises;

import com.example.demo.dao.DAOInterface;
import com.example.demo.dao.DAOPhoneContact;
import com.example.demo.dao.model.Contacts;
import com.example.demo.dao.model.PhoneContact;
import com.example.demo.logiclvl.TransformationPhoneContact;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phonecontact")
public class PhoneContactController {
    DAOInterface<PhoneContact> daoInterface= new DAOPhoneContact();

    @RequestMapping("/get/{id}")
    public @ResponseBody
    List<PhoneContact> getContact(@PathVariable int id) {
        return daoInterface.getElementsByForeignKey(id);
    }

    @RequestMapping("/delete/{id},{contactKey}")
    void deleteContact(@PathVariable int id,@PathVariable int contactKey) {
        daoInterface.deleteEntity(id,contactKey);
    }

    @PostMapping(value = "/add/")
    public String savePerson(@Valid @RequestBody  List<PhoneContact> phoneContacts, Errors errors) {
        TransformationPhoneContact transformationPhoneContact = new TransformationPhoneContact(phoneContacts);
        transformationPhoneContact.handlePhoneContact();
        return "true";
    }
}

