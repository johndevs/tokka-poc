package fi.jasoft.tokka;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.vaadin.server.VaadinServlet;

@WebServlet(
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="fi.jasoft.tokka.TokkaUI"),
		@WebInitParam(name="widgetset", value="fi.jasoft.tokka.TokkaWidgetset")
    })
public class TokkaServlet extends VaadinServlet { }
