package pl.skawianna.uiimport java.util.Listimport org.apache.commons.logging.LogFactory;import org.apache.log4j.Logger;import org.joda.time.Period;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.transaction.HeuristicCompletionException;import org.springframework.transaction.TransactionException;import org.springframework.transaction.TransactionSystemException;import pl.skawianna.ShoutboxService;import pl.skawianna.Userimport pl.skawianna.UserServiceimport com.vaadin.event.ShortcutAction.KeyCode;import com.vaadin.grails.Grailsimport com.vaadin.ui.Buttonimport com.vaadin.ui.CustomComponentimport com.vaadin.ui.HorizontalLayout;import com.vaadin.ui.Labelimport com.vaadin.ui.RichTextArea;import com.vaadin.ui.Table;import com.vaadin.ui.Table.ColumnHeaderMode;import com.vaadin.ui.TextArea;import com.vaadin.ui.TextFieldimport com.vaadin.ui.UI;import com.vaadin.ui.VerticalLayoutimport com.vaadin.ui.Button.ClickEventimport com.vaadin.ui.Button.ClickListener
class Shoutbox extends CustomComponent  {
	VerticalLayout shoutboxLayout	HorizontalLayout mainLayout	HorizontalLayout messageLayout		TextArea txtShoutbox	TextArea txtMessage	Label lblMyUsername	Table tblUsers	Button btnOk		MyUI myUI = ((MyUI)UI.getCurrent())	ShoutboxService service = Grails.get(ShoutboxService)	UserService userService = Grails.get(UserService)		private final static Logger log = Logger.getLogger(Shoutbox.class.getName());		public Shoutbox() {		log.info("building layout...")		buildMainLayout()		log.info("built layout")		setCompositionRoot(mainLayout)				lblMyUsername.value = "${myUI.myUsername}:".toString()		fetchShoutbox(5)		fetchUsers()			}		private void buildMainLayout() {		mainLayout = new HorizontalLayout()		shoutboxLayout = new VerticalLayout()		messageLayout = new HorizontalLayout()				txtShoutbox = new TextArea()		txtMessage = new TextArea()		btnOk = new Button()		tblUsers = new Table()		lblMyUsername = new Label()		btnOk.setCaption(Grails.i18n("shoutbox.send"))		btnOk.addClickListener(new ClickListener() {			@Override			public void buttonClick(ClickEvent event) {				sendMessage()			}		})		btnOk.setClickShortcut(KeyCode.ENTER)				mainLayout.addComponent(shoutboxLayout)		mainLayout.addComponent(tblUsers)				shoutboxLayout.addComponent(txtShoutbox)		shoutboxLayout.addComponent(messageLayout)		messageLayout.addComponent(lblMyUsername)		messageLayout.addComponent(txtMessage)		messageLayout.addComponent(btnOk)				mainLayout.setSizeFull()		messageLayout.setSizeFull()				shoutboxLayout.setExpandRatio(txtShoutbox, 1)		messageLayout.setExpandRatio(txtMessage, 1)				txtShoutbox.immediate = true		txtShoutbox.wordwrap = true				tblUsers.immediate = true		tblUsers.addContainerProperty("user", Label.class, null)		tblUsers.columnHeaderMode = ColumnHeaderMode.HIDDEN		tblUsers.selectable = true						txtShoutbox.height = "300px"		txtShoutbox.width = "100%"				lblMyUsername.setWidth("50px")		lblMyUsername.immediate = true				txtMessage.width = "400px"				tblUsers.width = "200px"		tblUsers.height ="400px"				txtMessage.focus()			}		private void fetchShoutbox(int minutes){		List<Shoutbox> recentMessages = service.listLastRecent(new Period(0,5,0,0))		log.info "recentMessages: $recentMessages"		StringBuilder builder = new StringBuilder()		recentMessages.each { message ->			builder.append(message.toString() + "\n")		}				log.info "fetched messages: ${builder.toString()}"		txtShoutbox.readOnly = false		txtShoutbox.value = builder.toString()		if (txtShoutbox.value){		  txtShoutbox.cursorPosition = txtShoutbox.value.length() - 1		}		txtShoutbox.readOnly = true		txtMessage.focus()	}		private void fetchUsers(){		List<String> users = userService.formatUsersWithStatuses()		log.info "users: $users"		users.eachWithIndex{user, index ->			Object[] item = new Object[1]			item[0] = new Label(user, Label.CONTENT_XHTML)			tblUsers.addItem(item, index)			log.info "item: ${item}"		}	}		private void sendMessage(){		if (txtMessage.value){			service.sendMessage(txtMessage.value, myUI.myUsername)		}		txtMessage.value = ""		txtMessage.focus()	}}