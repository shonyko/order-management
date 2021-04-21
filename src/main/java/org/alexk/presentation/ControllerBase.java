package org.alexk.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Clasa care se ocupa de randarea unor view-uri partiale
 */
@Controller
public class ControllerBase {
    /**
     * Obiect care faciliteaza randarea view-urilor
     */
    @Autowired
    ViewResolver viewResolver;

    /**
     * @param viewName numele view-ului ce urmeaza sa fie randat
     * @param modelMap map-ul de modele care va fi folosit ca si parametrii pentru view-ul specificat
     * @return un string care reprezinta continutul view-ului dupa randare
     */
    protected String getView(String viewName, ModelMap modelMap) {
        String res = null;
        try {
            var view = viewResolver.resolveViewName(viewName, Locale.US);
            MockHttpServletResponse mockResp = new MockHttpServletResponse();
            MockHttpServletRequest mockReq = new MockHttpServletRequest();
            view.render(modelMap, mockReq, mockResp);
            res = mockResp.getContentAsString();
        }
        catch (Exception e) {
            System.out.println("View rederer error: " + e.getMessage());
        }

        return res;
    }
}
