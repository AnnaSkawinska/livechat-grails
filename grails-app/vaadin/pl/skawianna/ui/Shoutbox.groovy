package pl.skawianna.uiimport java.util.Listimport org.apache.commons.logging.LogFactory;import org.apache.log4j.Logger;import org.joda.time.Period;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.transaction.HeuristicCompletionException;import org.springframework.transaction.TransactionException;import org.springframework.transaction.TransactionSystemException;import pl.skawianna.ShoutboxService;import pl.skawianna.Userimport pl.skawianna.UserServiceimport com.vaadin.grails.Grailsimport com.vaadin.ui.Buttonimport com.vaadin.ui.CustomComponentimport com.vaadin.ui.HorizontalLayout;import com.vaadin.ui.Labelimport com.vaadin.ui.RichTextArea;import com.vaadin.ui.Table;import com.vaadin.ui.TextArea;import com.vaadin.ui.TextFieldimport com.vaadin.ui.VerticalLayoutimport com.vaadin.ui.Button.ClickEventimport com.vaadin.ui.Button.ClickListener
class Shoutbox extends CustomComponent  {
	VerticalLayout shoutboxLayout	HorizontalLayout mainLayout	HorizontalLayout messageLayout		TextArea txtShoutbox	TextArea txtMessage	Table tblUsers	Button btnOk	ShoutboxService service = Grails.get(ShoutboxService)	private final static Logger log = Logger.getLogger(Shoutbox.class.getName());		public Shoutbox() {		log.info("building layout...")		buildMainLayout()		log.info("built layout")		setCompositionRoot(mainLayout)				fetchShoutbox(5)			}		private void buildMainLayout() {		mainLayout = new HorizontalLayout()		shoutboxLayout = new VerticalLayout()		messageLayout = new HorizontalLayout()				txtShoutbox = new TextArea()		txtMessage = new TextArea()		btnOk = new Button()		tblUsers = new Table()		btnOk.setCaption(Grails.i18n("shoutbox.send"))		btnOk.addClickListener(new ClickListener() {			@Override			public void buttonClick(ClickEvent event) {						}		})				mainLayout.addComponent(shoutboxLayout)		mainLayout.addComponent(tblUsers)				shoutboxLayout.addComponent(txtShoutbox)		shoutboxLayout.addComponent(messageLayout)				messageLayout.addComponent(txtMessage)		messageLayout.addComponent(btnOk)				mainLayout.setSizeFull()		messageLayout.setSizeFull()				shoutboxLayout.setExpandRatio(txtShoutbox, 1)		messageLayout.setExpandRatio(txtMessage, 1)				txtShoutbox.setEnabled(false)		txtShoutbox.setImmediate(true)						txtShoutbox.setHeight("150px")		txtShoutbox.setWidth("100%")				txtMessage.setWidth("500px")				tblUsers.setWidth("200px")							}			private void fetchShoutbox(int minutes){		List<Shoutbox> recentMessages = service.listLastRecent(new Period(0,5,0,0))		log.info "recentMessages: $recentMessages"		StringBuilder builder = new StringBuilder()		recentMessages.each { message ->			builder.append(message.toString() + "\n")		}				log.info "fetched messages: ${builder.toString()}"		txtShoutbox.value = builder.toString()	}}