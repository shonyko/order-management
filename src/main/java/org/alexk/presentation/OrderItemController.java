package org.alexk.presentation;

import lombok.val;
import org.alexk.businessLogic.OrderItemBL;
import org.alexk.models.OrderItem;
import org.alexk.models.enums.FieldType;
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
 * Clasa care se ocupa de servirea continutului paginilor specifice unui produs dintr-o comanda
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController extends ControllerBase {
    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/add")
    public ModelAndView getAddModal(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val fields = new ArrayList<Field>();

        modelMap.addAttribute("title", "Add Order Item");
        modelMap.addAttribute("fields", fields);

        for(val field : OrderItem.class.getDeclaredFields()) {
            val name = field.getName();
            val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
            fields.add(new Field(label, name));
        }

        val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
        if(idField != null) {
            idField.setHidden(true);
            idField.setValue("0");
        }

        val switchField2 = fields.stream().filter(x -> x.getName().equals("productId")).findFirst().orElse(null);
        if(switchField2 != null) {
            switchField2.setType(FieldType.SELECT.name());
            switchField2.setUrl("/api/product/select");
            switchField2.setLabel("Product");
        }

        model.addAttribute("data", getView("modal", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul comenzii in care urmeaza sa fie adaugat produsul
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/add/{id}")
    public ModelAndView getAddToOrderModal(HttpSession session, Model model, @PathVariable int id) {
        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val fields = new ArrayList<Field>();

        modelMap.addAttribute("title", "Add Order Item");
        modelMap.addAttribute("fields", fields);

        for(val field : OrderItem.class.getDeclaredFields()) {
            val name = field.getName();
            val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
            fields.add(new Field(label, name));
        }

        val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
        if(idField != null) {
            idField.setHidden(true);
            idField.setValue("0");
        }

        val orderField = fields.stream().filter(x -> x.getName().equals("orderId")).findFirst().orElse(null);
        if(orderField != null) {
            orderField.setHidden(true);
            orderField.setValue(Integer.toString(id));
        }

        val switchField = fields.stream().filter(x -> x.getName().equals("productId")).findFirst().orElse(null);
        if(switchField != null) {
            switchField.setType(FieldType.SELECT.name());
            switchField.setUrl("/api/product/select/" + id);
            switchField.setLabel("Product");
        }

        model.addAttribute("data", getView("modal", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul produsului dintr-o comanda ale carui date urmeaza sa fie modificate
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/update/{id}")
    public ModelAndView getUpdateModal(HttpSession session, Model model, @PathVariable int id) {

        model.addAttribute("success", true);

        try {
            val logic = new OrderItemBL();
            val orderItem = logic.findById(id);

            val modelMap = new ModelMap();
            val fields = new ArrayList<Field>();

            modelMap.addAttribute("title", "Update Order Item");
            modelMap.addAttribute("fields", fields);

            for(val field : OrderItem.class.getDeclaredFields()) {
                field.setAccessible(true);
                val name = field.getName();
                val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
                val value = field.get(orderItem).toString();
                fields.add(new Field(label, name, value));
            }

            val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
            if(idField != null) {
                idField.setReadonly(true);
            }

            val idkField = fields.stream().filter(x -> x.getName().equals("orderId")).findFirst().orElse(null);
            if(idkField != null) {
                idkField.setReadonly(true);
            }

            val switchField = fields.stream().filter(x -> x.getName().equals("productId")).findFirst().orElse(null);
            if(switchField != null) {
                switchField.setType(FieldType.SELECT.name());
                switchField.setUrl("/api/product/select");
                switchField.setLabel("Product");
            }

            model.addAttribute("data", getView("modal", modelMap));
        } catch (Exception e) {
            model.addAttribute("success", false);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
