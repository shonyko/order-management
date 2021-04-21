package org.alexk.presentation;

import com.google.gson.Gson;
import lombok.val;
import org.alexk.businessLogic.ProductBL;
import org.alexk.models.Product;
import org.alexk.models.misc.Column;
import org.alexk.models.misc.Field;
import org.alexk.utils.StringFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clasa care se ocupa de servirea continutului paginilor specifice unui produs
 */
@Controller
@RequestMapping("/product")
public class ProductController extends ControllerBase {
    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView getProductsPage(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val list = new ArrayList<Column>();
        for(val field : Product.class.getDeclaredFields()) {
            list.add(new Column(field.getName()));
        }
        val gson = new Gson();
        val json = gson.toJson(list);

        modelMap.addAttribute("args", json);
        model.addAttribute("data", getView("products", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/add")
    public ModelAndView getProductsAddModal(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val fields = new ArrayList<Field>();

        modelMap.addAttribute("title", "Add Product");
        modelMap.addAttribute("fields", fields);

        for(val field : Product.class.getDeclaredFields()) {
            val name = field.getName();
            val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
            fields.add(new Field(label, name));
        }

        val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
        if(idField != null) {
            idField.setHidden(true);
            idField.setValue("0");
        }

        model.addAttribute("data", getView("modal", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul produsului ale carui date urmeaza sa fie modificate
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/update/{id}")
    public ModelAndView getProductsUpdateModal(HttpSession session, Model model, @PathVariable int id) {

        model.addAttribute("success", true);

        try {
            val logic = new ProductBL();
            val product = logic.findById(id);

            val modelMap = new ModelMap();
            val fields = new ArrayList<Field>();

            modelMap.addAttribute("title", "Update Product");
            modelMap.addAttribute("fields", fields);

            for(val field : Product.class.getDeclaredFields()) {
                field.setAccessible(true);
                val name = field.getName();
                val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
                val value = field.get(product).toString();
                fields.add(new Field(label, name, value));
            }

            val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
            if(idField != null) {
                idField.setReadonly(true);
            }

            model.addAttribute("data", getView("modal", modelMap));
        } catch (Exception e) {
            model.addAttribute("success", false);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
