package pl.skawianna.ui

import org.apache.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

import pl.skawianna.User;
import pl.skawianna.UserService;
import pl.skawianna.push.Broadcaster;
import pl.skawianna.push.Broadcaster.BroadcastListener;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Label
import com.vaadin.annotations.Push;
import com.vaadin.grails.Grails

/**
 *
 *
 * @author askawinska
 */
@Push
class MyUI extends UI implements BroadcastListener {
	static {
		SLF4JBridgeHandler.install();
	}
	
	private final static Logger log = Logger.getLogger(MyUI.class.getName());

	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
		log.info("init. current username? ${myUsername}")
		if (myUsername){
			setContent(new Shoutbox())
		} else {
			setContent(new Welcome())
		}
		
		// Register to receive server push broadcasts
		Broadcaster.register(this)
    }
	
	
	@Override
	public void receiveBroadcast(final String message) {
		// Must lock the session to execute logic safely
		access(new Runnable() {
			@Override
			public void run() {
				
				if (getContent() instanceof Shoutbox){
					((Shoutbox)getContent()).fetchOnPush();
				}
				log.info "BROADCAST! $message"
			}
		});
	}
	// Must also unregister when the UI expires
	@Override
	public void detach() {
		Broadcaster.unregister(this);
		super.detach();
	}
	
	public void joinLivechat(String username){
		VaadinService.currentRequest.wrappedSession.setAttribute("myUsername", username)
		
		setContent(new Shoutbox())
	}
	
	public String getMyUsername(){
		String myUsername = VaadinService.currentRequest.wrappedSession.getAttribute("myUsername")
		log.info "myUsername: $myUsername"
		return myUsername
	}
	
	
}
