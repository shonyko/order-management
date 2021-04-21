package org.alexk.presentation;

import com.google.gson.Gson;
import lombok.val;
import org.alexk.businessLogic.OrderBL;
import org.alexk.models.Order;
import org.alexk.models.enums.FieldType;
import org.alexk.models.misc.Column;
import org.alexk.models.misc.Field;
import org.alexk.models.viewmodels.OrderItemViewModel;
import org.alexk.models.viewmodels.OrderViewModel;
import org.alexk.utils.FieldRetriever;
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
 * Clasa care se ocupa de servirea continutului paginilor specifice unei comenzi
 */
@Controller
@RequestMapping("/order")
public class OrderController extends ControllerBase {
    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView getOrdersPage(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val orderList = new ArrayList<Column>();
        for(val field : FieldRetriever.getFields(OrderViewModel.class)) {
            orderList.add(new Column(field.getName()));
        }
        val gson = new Gson();
        val orderJson = gson.toJson(orderList);

        val orderItemList = new ArrayList<Column>();
        for(val field : FieldRetriever.getFields(OrderItemViewModel.class)) {
            orderItemList.add(new Column(field.getName()));
        }
        val orderItemJson = gson.toJson(orderItemList);

        modelMap.addAttribute("args", orderJson);
        modelMap.addAttribute("args1", orderItemJson);
        model.addAttribute("data", getView("orders", modelMap));

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/add")
    public ModelAndView getOrdersAddModal(HttpSession session, Model model) {

        model.addAttribute("success", true);

        val modelMap = new ModelMap();
        val fields = new ArrayList<Field>();

        modelMap.addAttribute("title", "Add Order");
        modelMap.addAttribute("fields", fields);

        for(val field : Order.class.getDeclaredFields()) {
            val name = field.getName();
            val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
            fields.add(new Field(label, name));
        }

        val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
        if(idField != null) {
            idField.setHidden(true);
            idField.setValue("0");
        }

        val switchField = fields.stream().filter(x -> x.getName().equals("customerId")).findFirst().orElse(null);
        if(switchField != null) {
            switchField.setType(FieldType.SELECT.name());
            switchField.setUrl("/api/customer/select");
            switchField.setLabel("Customer");
        }

        val dateField = fields.stream().filter(x -> x.getName().equals("date")).findFirst().orElse(null);
        if(dateField != null) {
            dateField.setType(FieldType.DATETIME.name());
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
    public ModelAndView getOrdersUpdateModal(HttpSession session, Model model, @PathVariable int id) {

        model.addAttribute("success", true);

        try {
            val logic = new OrderBL();
            val order = logic.findById(id);

            val modelMap = new ModelMap();
            val fields = new ArrayList<Field>();

            modelMap.addAttribute("title", "Update Order");
            modelMap.addAttribute("fields", fields);

            for(val field : Order.class.getDeclaredFields()) {
                field.setAccessible(true);
                val name = field.getName();
                val label = StringFormatter.capitalize(StringFormatter.splitCamelCase(name));
                val value = field.get(order).toString();
                fields.add(new Field(label, name, value));
            }

            val idField = fields.stream().filter(x -> x.getName().equals("id")).findFirst().orElse(null);
            if(idField != null) {
                idField.setReadonly(true);
            }

            val switchField = fields.stream().filter(x -> x.getName().equals("customerId")).findFirst().orElse(null);
            if(switchField != null) {
                switchField.setType(FieldType.SELECT.name());
                switchField.setUrl("/api/customer/select");
                switchField.setLabel("Customer");
            }

            val dateField = fields.stream().filter(x -> x.getName().equals("date")).findFirst().orElse(null);
            if(dateField != null) {
                dateField.setType(FieldType.DATETIME.name());
            }

            model.addAttribute("data", getView("modal", modelMap));
        } catch (Exception e) {
            model.addAttribute("success", false);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
