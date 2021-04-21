package org.alexk.presentation.api;

import lombok.val;
import org.alexk.businessLogic.ProductBL;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Product;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;

/**
 * Clasa care se ocupa de request-urile care au legatura cu datele produselor
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductAPIController {
    /**
     * Obiect ce contine logica necesara pentru procesarea unui produs
     */
    private final ProductBL logic = new ProductBL();

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
     * @param product datele despre produs care urmeaza sa fie salvate
     * @return un JSON care contine datele dorite
     */
    @PostMapping
    public ModelAndView add(HttpSession session, Model model, Product product) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Adaugarea a fost facuta cu succes!");

        try {
            logic.insert(product);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Adaugarea a esuat!");
        }

        model.asMap().remove("product");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param product datele despre produs care urmeaza sa fie actualizate
     * @return un JSON care contine datele dorite
     */
    @PutMapping
    public ModelAndView update(HttpSession session, Model model, Product product) {
        model.addAttribute("success", true);
        model.addAttribute("message", "Actualizarea a fost facuta cu succes!");

        try {
            logic.update(product);
        } catch (ModelException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Actualizarea a esuat!");
        }

        model.asMap().remove("product");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    /**
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul produsului ale carui date urmeaza sa fie sterse
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
     * Metoda care returneaza toate produsele in vederea utilizarii intr-un select
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/select")
    public ModelAndView getAllSelectable(HttpSession session, Model model) {
        model.addAttribute("success", true);

        try {
            val list = logic.findAllSelectable();
            model.addAttribute("data", list);
            model.addAttribute("product", true);
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
     * Metoda care returneaza toate produsele care nu au fost deja adaugate in comanda in vederea utilizarii intr-un select
     * @param session sesiunea actuala
     * @param model modelul folosit pentru a intoarce date
     * @param id id-ul comenzii
     * @return un JSON care contine datele dorite
     */
    @GetMapping("/select/{id}")
    public ModelAndView getAllSelectableByOrderId(HttpSession session, Model model, @PathVariable int id) {
        model.addAttribute("success", true);

        try {
            val list = logic.findAllSelectableByOrderId(id);
            model.addAttribute("data", list);
            model.addAttribute("product", true);
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
