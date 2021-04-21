package org.alexk.presentation;

import com.google.gson.Gson;
import lombok.val;
import org.alexk.models.Customer;
import org.alexk.models.misc.Column;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Clasa care se ocupa de servirea continutului principal al aplicatiei
 */
@Controller
public class MainController extends ControllerBase {
    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView index(HttpSession session, Model model) {

        val list = new ArrayList<Column>();
        for(val field : Customer.class.getDeclaredFields()) {
            list.add(new Column(field.getName()));
        }
        val gson = new Gson();
        val json = gson.toJson(list);

        model.addAttribute("args", json);
        model.addAttribute("view", "customers");
        return new ModelAndView("layout");
    }
}
