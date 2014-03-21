package pl.skawianna.uiimport java.util.Listimport org.apache.commons.logging.LogFactory;import org.apache.log4j.Logger;import org.joda.time.DateTime;import org.joda.time.Period;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.transaction.HeuristicCompletionException;import org.springframework.transaction.TransactionException;import org.springframework.transaction.TransactionSystemException;import pl.skawianna.ShoutboxService;import pl.skawianna.Userimport pl.skawianna.UserServiceimport pl.skawianna.push.Broadcaster;import com.vaadin.data.Container;import com.vaadin.data.Item;import com.vaadin.data.util.IndexedContainer;import com.vaadin.event.ShortcutAction.KeyCode;import com.vaadin.grails.Grailsimport com.vaadin.shared.ui.label.ContentMode;import com.vaadin.ui.Buttonimport com.vaadin.ui.CustomComponentimport com.vaadin.ui.HorizontalLayout;import com.vaadin.ui.Labelimport com.vaadin.ui.RichTextArea;import com.vaadin.ui.Table;import com.vaadin.ui.Table.ColumnHeaderMode;import com.vaadin.ui.TextArea;import com.vaadin.ui.TextFieldimport com.vaadin.ui.UI;import com.vaadin.ui.VerticalLayoutimport com.vaadin.ui.Button.ClickEventimport com.vaadin.ui.Button.ClickListener
class Shoutbox extends CustomComponent  {
	VerticalLayout shoutboxLayout	HorizontalLayout mainLayout	HorizontalLayout messageLayout	HorizontalLayout statusLayout	VerticalLayout messageAndStatusLayout		TextArea txtShoutbox	TextArea txtMessage	TextArea txtStatus	Label lblMyUsername	Label lblStatus	Table tblUsers	Button btnOk	Button btnStatus	Container usersContainer		MyUI myUI = ((MyUI)UI.getCurrent())	ShoutboxService service = Grails.get(ShoutboxService)	UserService userService = Grails.get(UserService)		private DateTime openTime = new DateTime()		private final static Logger log = Logger.getLogger(Shoutbox.class.getName());		public Shoutbox() {		log.info("building layout...")		buildMainLayout()		log.info("built layout")		setCompositionRoot(mainLayout)				lblMyUsername.value = "${myUI.myUsername?: ''}:".toString()		fetchShoutbox()		fetchUsers()			}		public void fetchOnPush(){		fetchShoutbox()		fetchUsers()	}		private void buildMainLayout() {		mainLayout = new HorizontalLayout()		shoutboxLayout = new VerticalLayout()		messageLayout = new HorizontalLayout()		statusLayout = new HorizontalLayout()		messageAndStatusLayout = new VerticalLayout()				txtShoutbox = new TextArea()		txtMessage = new TextArea()		txtStatus = new TextArea()		btnOk = new Button()		btnStatus = new Button()		tblUsers = new Table()		lblMyUsername = new Label()		lblStatus = new Label()		btnOk.setCaption(Grails.i18n("shoutbox.send"))		btnOk.addClickListener(new ClickListener() {			@Override			public void buttonClick(ClickEvent event) {				sendMessage()			}		})		btnOk.setClickShortcut(KeyCode.ENTER)				btnStatus.setCaption(Grails.i18n("shoutbox.setStatus"))		btnStatus.addClickListener(new ClickListener() {			@Override			public void buttonClick(ClickEvent event) {				setStatus()			}		})				mainLayout.addComponent(shoutboxLayout)		mainLayout.addComponent(tblUsers)				shoutboxLayout.addComponent(txtShoutbox)		shoutboxLayout.addComponent(messageAndStatusLayout)		messageAndStatusLayout.addComponent(messageLayout)		messageAndStatusLayout.addComponent(statusLayout)		statusLayout.addComponent(lblStatus)		statusLayout.addComponent(txtStatus)		statusLayout.addComponent(btnStatus)				messageLayout.addComponent(lblMyUsername)		messageLayout.addComponent(txtMessage)		messageLayout.addComponent(btnOk)				mainLayout.setSizeFull()		messageLayout.setSizeFull()				shoutboxLayout.setExpandRatio(txtShoutbox, 1)		messageLayout.setExpandRatio(txtMessage, 1)		statusLayout.setExpandRatio(txtStatus, 1)				txtShoutbox.immediate = true		txtShoutbox.wordwrap = true				tblUsers.immediate = true		tblUsers.columnHeaderMode = ColumnHeaderMode.HIDDEN		tblUsers.selectable = true						usersContainer = new IndexedContainer()		usersContainer.addContainerProperty("user", Label.class, null)		tblUsers.setContainerDataSource(usersContainer)				txtShoutbox.height = "300px"		txtShoutbox.width = "100%"						lblMyUsername.width = "50px"		lblMyUsername.immediate = true				txtMessage.width = "400px"				lblStatus.width = "50px"		lblStatus.value = Grails.i18n("shoutbox.status")				txtStatus.width = "400px"		txtStatus.height = "25px"				tblUsers.width = "200px"		tblUsers.height ="400px"				txtMessage.focus()			}		private void fetchShoutbox(){		Period beforeOpen = new Period(0,5,0,0)				List<Shoutbox> recentMessages = service.listSince(openTime.minus(beforeOpen))					log.info "recentMessages: $recentMessages"		StringBuilder builder = new StringBuilder()		recentMessages.each { message ->			builder.append(message.toString() + "\n")		}				log.info "fetched messages: ${builder.toString()}"		txtShoutbox.readOnly = false		txtShoutbox.value = builder.toString()		if (txtShoutbox.value){		  txtShoutbox.cursorPosition = txtShoutbox.value.length() - 1		}		txtShoutbox.readOnly = true		txtMessage.focus()	}		private void fetchUsers(){		List<String> users = userService.formatUsersWithStatuses()		log.info "users: $users"				usersContainer.removeAllItems()		users.eachWithIndex{user, index ->			Item newItem = usersContainer.getItem(usersContainer.addItem())			newItem.getItemProperty("user").setValue(new Label(user, ContentMode.HTML))		}		log.debug "loaded items:"		tblUsers.getItemIds().each{			log.debug "item: [$it]:${tblUsers.getItem(it)}"		}		tblUsers.refreshRowCache()		tblUsers.refreshRenderedCells()	}		public void updateLoginLabel(){		lblMyUsername.value = "${myUI.myUsername?: ''}:".toString()	}		private void sendMessage(){		if (txtMessage.value){			service.sendMessage(txtMessage.value, myUI.myUsername)		}		txtMessage.value = ""		txtMessage.focus()				Broadcaster.broadcast("MESSAGE")	}		private void setStatus(){		userService.setStatus(myUI.myUsername, "${txtStatus.value ?: ''}".toString())		Broadcaster.broadcast("STATUS")	}}