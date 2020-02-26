package cn.wengzi.controller;

import cn.wengzi.model.Student;
import cn.wengzi.utils.XmlParseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/xmlParse")
public class XmlParseHandler {

    @GetMapping
    public List<Student> getData() {
        String filePath = "D:\\others\\XmlParse\\src\\main\\resources\\Student.xml";
        return XmlParseUtils.parseXmlToList(filePath);
    }
}
