package com.example.demo.servises;

import com.example.demo.dao.DAOAttachedFile;
import com.example.demo.dao.DAOInterface;
import com.example.demo.dao.model.AttachedFile;
import com.example.demo.logiclvl.FileFromServer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attached")
public class AttachedController {

    DAOInterface<AttachedFile> daoInterface = new DAOAttachedFile();


    @PostMapping("/process")
    String getFileInBase64(@Valid @RequestBody FileFromServer fileFromServer){
        fileFromServer.handleAttachedList();
        return "true";
    }

    @RequestMapping("/get/{id}")
    public @ResponseBody
    List<AttachedFile> getAllAttached(@PathVariable int id){
        return daoInterface.getElementsByForeignKey(id);
    }

    @RequestMapping("/delete/{id}")
    void deleteAttached(@PathVariable int id){
        daoInterface.deleteEntity(id,0);
    }

}
