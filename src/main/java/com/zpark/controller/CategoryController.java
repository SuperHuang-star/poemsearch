package com.zpark.controller;

import com.zpark.entity.Category;
import com.zpark.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/queryAll")
    public void findAll(HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.findAll();
        StringBuilder sb = new StringBuilder("<select>");
        for (Category category : categories) {
            sb.append("<option value="+"'"+category.getId()+"'"+">");
            sb.append(category.getName());
            sb.append("</option>");
        }
            sb.append("</select>");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(sb);
    }

    @RequestMapping("/queryAll2")
    public List<Category> queryAll2(){
        return categoryService.findAll();
    }
}
