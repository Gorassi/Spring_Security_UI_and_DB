package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
 //   public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model) {
//        model.addAttribute("name", name);
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam (name="myText") String myText,
//                      @RequestParam (defaultValue="mySimpleTag") String tag,
//                      @RequestParam (name="surname", defaultValue="Cirtakis") String surname,
//                      @RequestParam (name="age", defaultValue="999") int age,
//                      @RequestParam (defaultValue="999") String phone,
                      @RequestParam (defaultValue="No comment") String comment,
                      Map<String, Object> model){
        System.out.println("GreetingController... method add()...");
//        System.out.println("text = " + myText + "    tag = " + tag);
//        System.out.println("surname = " + surname + "    age = " + age);
//        System.out.println("phone = " + phone + "    comment = " + comment);
        Message message = new Message(myText, comment);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;

        if(filter != null&& !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
}