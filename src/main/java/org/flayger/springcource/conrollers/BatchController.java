package org.flayger.springcource.conrollers;

import org.flayger.springcource.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final PersonDao personDao;

    @Autowired
    public BatchController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String index(){
        return "batch/index";
    }

    @GetMapping("/withoutBatch")
    public String withoutBatch(){
        personDao.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/withBatch")
    public String withBatch(){
        personDao.testBatchUpdate();
        return "redirect:/people";
    }

}
