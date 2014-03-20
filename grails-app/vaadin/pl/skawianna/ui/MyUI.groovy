package pl.skawianna.ui

import org.slf4j.bridge.SLF4JBridgeHandler;

import pl.skawianna.User;
import pl.skawianna.UserService;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Label
import com.vaadin.grails.Grails

/**
 *
 *
 * @author
 */
class MyUI extends UI {
	static {
		SLF4JBridgeHandler.install();
	 }
	private TextField login
	private Label error
	private Button ok

    @Override
    protected void init(VaadinRequest vaadinRequest) {

		VerticalLayout layout = new VerticalLayout()

        String homeLabel = Grails.i18n("default.home.label")
        Label label = new Label(homeLabel)
        layout.addComponent(label)
		layout.addComponent(new Label("Yippie?"))

		setContent(new Welcome())
		
		
    }
}
