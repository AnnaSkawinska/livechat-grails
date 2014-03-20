package pl.skawianna.ui

import org.apache.log4j.Logger;
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
class ShoutboxUI extends UI {
	static {
		SLF4JBridgeHandler.install();
	 }
	
	private final static Logger log = Logger.getLogger(ShoutboxUI.class.getName());

    @Override
    protected void init(VaadinRequest vaadinRequest) {
		log.info("init!")
		
		setContent(new Shoutbox())
		
		
    }
}
