package pl.skawianna.uiimport org.apache.log4j.Loggerimport org.springframework.transaction.TransactionExceptionimport pl.skawianna.Userimport pl.skawianna.UserServiceimport pl.skawianna.push.Broadcaster;import com.vaadin.data.Propertyimport com.vaadin.data.Property.ValueChangeEvent;import com.vaadin.data.Property.ValueChangeListener;import com.vaadin.grails.Grailsimport com.vaadin.ui.Buttonimport com.vaadin.ui.CustomComponentimport com.vaadin.ui.Labelimport com.vaadin.ui.TextFieldimport com.vaadin.ui.UIimport com.vaadin.ui.VerticalLayoutimport com.vaadin.ui.Button.ClickEventimport com.vaadin.ui.Button.ClickListenerclass Welcome extends CustomComponent  {	VerticalLayout loginLayout	TextField txtLogin	Label lblError	Button btnOk	UserService userService = Grails.get(UserService)	private final static Logger log = Logger.getLogger(Welcome.class.getName());	MyUI myUI = (MyUI)UI.getCurrent()	public Welcome() {		buildMainLayout()		setCompositionRoot(loginLayout)		txtLogin.focus()	}	private void buildMainLayout() {		Property.ValueChangeListener enterListener = new ValueChangeListener() {					@Override					public void valueChange(ValueChangeEvent event) {						login()					}				}		loginLayout = new VerticalLayout()		txtLogin = new TextField()		lblError = new Label()		btnOk = new Button()		txtLogin.setCaption(Grails.i18n("welcome.login"))		txtLogin.setRequired(true)		txtLogin.setImmediate(true)		txtLogin.addValueChangeListener(new ValueChangeListener() {					@Override					public void valueChange(ValueChangeEvent event) {						login()					}				})		btnOk.setCaption(Grails.i18n("welcome.enter"))		btnOk.addClickListener(new ClickListener() {					@Override					public void buttonClick(ClickEvent event) {						login()					}				})		loginLayout.setSizeFull()		loginLayout.addComponent(txtLogin)		loginLayout.addComponent(lblError)		loginLayout.addComponent(btnOk)	}	private void login(){		try {			log.info "registering user: ${txtLogin.value}"			userService.registerUser(txtLogin.value)			log.info "registered user. listing users:"			List<User> users = userService.getUsers()			log.info "listed users: $users"			myUI.joinLivechat(txtLogin.value)						// tell other users to refresh ui:			Broadcaster.broadcast("LOGIN");		}		catch (TransactionException e){			//		if ((e?.cause instanceof TransactionSystemException)			//			&& (e?.cause?.cause instanceof DataIntegrityViolationException)){			lblError.value = Grails.i18n('welcome.loginMustBeUnique')			txtLogin.focus()			//		} else {			//			log.error(e.stackTrace)			//		}		}	}}