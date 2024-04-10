package io.github.zhoujunlin94.example.web.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年05月08日 11:01
 * @desc
 */
public class TemplateGenerator {

    public static void main(String[] args) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(TemplateGenerator.class, "/templates/");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("Model.java.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", "com.example.demo");
        dataModel.put("className", "User");

        String outputFilePath = "User.java";
        File outputFile = new File(outputFilePath);

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            template.process(dataModel, fileWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
