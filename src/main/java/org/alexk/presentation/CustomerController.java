package org.alexk.presentation;

import com.google.gson.Gson;
import lombok.val;
import org.alexk.businessLogic.CustomerBL;
import org.alexk.models.Customer;
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
import java.util.ArrayList;

/**
 * Clasa care se ocupa de servirea continutului paginilor specifice unui client
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends ControllerBase {
    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView getCustomersPage(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val list = new ArrayList<Column>();
        for(val field : Customer.class.getDeclaredFields()) {
            list.add(new Column(field.getName()));
        }
        val gson = new Gson();
        val json = gson.toJson(list);

        modelMap.addAttribute("args", json);
        model.addAttribute("data", getView("customers", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/add")
    public ModelAndView getCustomersAddModal(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val fields = new ArrayList<Field>();

        modelMap.addAttribute("title", "Add Customer");
        modelMap.addAttribute("fields", fields);

        for(val field : Customer.class.getDeclaredFields()) {
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
     * @param id id-ul clientului ale carui date urmeaza sa fie modificate
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/update/{id}")
    public ModelAndView getCustomersUpdateModal(HttpSession session, Model model, @PathVariable int id) {

        model.addAttribute("success", true);

        try {
            val logic = new CustomerBL();
            val customer = logic.findById(id);

            val modelMap = new ModelMap();
            val fields = new ArrayList<Field>();

            modelMap.addAttribute("title", "Update Customer");
            modelMap.addAttribute("fields", fields);

            for(val field : Customer.class.getDeclaredFields()) {
                field.setAccessible(true);
                val name = field.getName();
                val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
                val value = field.get(customer).toString();
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
