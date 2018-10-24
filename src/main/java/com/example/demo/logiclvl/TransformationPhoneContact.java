package com.example.demo.logiclvl;

import com.example.demo.dao.DAOInterface;
import com.example.demo.dao.DAOPhoneContact;
import com.example.demo.dao.model.PhoneContact;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TransformationPhoneContact {
    final static Logger logger = Logger.getLogger(TransformationPhoneContact.class);
    List<PhoneContact> phoneContactsToAdd = new ArrayList<>();
    List<PhoneContact> phoneContactsToUpdate = new ArrayList<>();
    List<PhoneContact> rawPhoneContactList;
    DAOInterface<PhoneContact> daoInterface = new DAOPhoneContact();

    public TransformationPhoneContact(List<PhoneContact> rawPhoneContactList) {
        this.rawPhoneContactList = rawPhoneContactList;
    }

    public void handlePhoneContact(){
        String stringForLog="";
        if(rawPhoneContactList.size()<1) return;
        List listPhonesFromDB = daoInterface.getElementsByForeignKey(rawPhoneContactList.get(0).getContactKey());
        for(int i=0;i<rawPhoneContactList.size();i++){
            if(rawPhoneContactList.get(i).getId()==0 || rawPhoneContactList.get(i).getPhoneNumber()==null)
                continue;
            stringForLog+="\n"+rawPhoneContactList.get(i).toString() ;
            if(i<listPhonesFromDB.size()){
                if(!rawPhoneContactList.get(i).equals(listPhonesFromDB.get(i))){
                    phoneContactsToUpdate.add(rawPhoneContactList.get(i));
                }
            }
            else {
                phoneContactsToAdd.add(rawPhoneContactList.get(i));
            }
        }
        logger.info("TransformationPhoneContact for list:" + stringForLog);
        solvePhoneContact();
    }

    private void solvePhoneContact(){
        for(PhoneContact phoneContact:phoneContactsToAdd){
            daoInterface.addEntity(phoneContact);
        }
        for(PhoneContact phoneContact:phoneContactsToUpdate){
            daoInterface.updateEntity(phoneContact);
        }
    }

}
