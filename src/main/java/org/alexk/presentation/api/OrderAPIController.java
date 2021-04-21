package org.alexk.presentation.api;

import lombok.val;
import org.alexk.businessLogic.OrderBL;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;

/**
 * Clasa care se ocupa de request-urile care au legatura cu datele comenzilor
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderAPIController {
    /**
     * Obiect ce contine logica necesara pentru procesarea unei comenzi
     */
    private final OrderBL logic = new OrderBL();

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping
    public ModelAndView getAll(HttpSession session, Model model) {
        model.addAttribute("success", true);

        try {
            val list = logic.findAllViewModels();
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
     * @param order datele despre comanda care urmeaza sa fie salvate
     * @return un JSON care contine datele dorite
     */
    @PostMapping
    public ModelAndView add(HttpSession session, Model model, Order order) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Adaugarea a fost facuta cu succes!");

        try {
            logic.insert(order);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Adaugarea a esuat!");
        }

        model.asMap().remove("order");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param order datele despre comanda care urmeaza sa fie actualizate
     * @return un JSON care contine datele dorite
     */
    @PutMapping
    public ModelAndView update(HttpSession session, Model model, Order order) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Actualizarea a fost facuta cu succes!");

        try {
            logic.update(order);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Actualizarea a esuat!");
        }

        model.asMap().remove("order");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul comenzii ale carei date urmeaza sa fie sterse
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
     * Metoda care finalizeaza o comanda
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul comenzii care urmeaza sa fie finalizata
     * @return un JSON care contine datele dorite
     */
    @PostMapping("/finalize/{id}")
    public ModelAndView finalizeOrder(HttpSession session, Model model, @PathVariable int id) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Finalizarea a avut loc cu succes!");

        try {
            logic.finalizeOrder(id);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Finalizarea a esuat!");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
