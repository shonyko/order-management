package org.alexk.presentation.api;

import lombok.val;
import org.alexk.businessLogic.CustomerBL;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Customer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;

/**
 * Clasa care se ocupa de request-urile care au legatura cu datele clientilor
 */
@RestController
@RequestMapping(value = "/api/customer")
public class CustomerAPIController {
    /**
     * Obiect ce contine logica necesara pentru procesarea unui client
     */
    private final CustomerBL logic = new CustomerBL();

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView getAll(HttpSession session, Model model) {
        model.addAttribute("success", true);

        try {
            val list = logic.findAll();
            model.addAttribute("data", list);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Unknown error while fetching data");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param customer datele despre client care urmeaza sa fie salvate
     * @return un JSON care contine datele dorite
     */
    @PostMapping
    public ModelAndView add(HttpSession session, Model model, Customer customer) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Adaugarea a fost facuta cu succes!");

        try {
            logic.insert(customer);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Adaugarea a esuat!");
        }

        model.asMap().remove("customer");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param customer datele despre client care urmeaza sa fie actualizate
     * @return un JSON care contine datele dorite
     */
    @PutMapping
    public ModelAndView update(HttpSession session, Model model, Customer customer) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Actualizarea a fost facuta cu succes!");

        try {
            logic.update(customer);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Actualizarea a esuat!");
        }

        model.asMap().remove("customer");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul clientului ale carui date urmeaza sa fie sterse
     * @return un JSON care contine datele dorite
     */
    @DeleteMapping("/{id}")
    public ModelAndView delete(HttpSession session, Model model, @PathVariable int id) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Stergerea a fost facuta cu succes!");

        try {
            logic.delete(id);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Stergerea a esuat!");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * Metoda care returneaza toti clientii in vederea utilizarii intr-un select
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/select")
    public ModelAndView getAllSelectable(HttpSession session, Model model) {
        model.addAttribute("success", true);

        try {
            val list = logic.findAll();
            model.addAttribute("data", list);
            model.addAttribute("customer", true);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Unknown error while fetching data");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
