package org.Hausarbeit.gui.views;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.Hausarbeit.gui.components.TopPanel;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.EmailInUseException;
import org.Hausarbeit.process.exceptions.EmptyFieldException;
import org.Hausarbeit.process.exceptions.NoEqualPasswordException;
import org.Hausarbeit.process.proxy.RegistrationControlProxy;
import org.Hausarbeit.services.util.Views;

import java.sql.SQLException;

public class RegistrationView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    private void setUp() {
        this.addComponent( new TopPanel() );
        Label line = new Label("<hr>", ContentMode.HTML);
        this.addComponent(line);
        line.setSizeFull();
        setStyleName("schrift-profil");
        //Eingabefelder
        //Email
        final Binder<UserDTO> emailBinder = new Binder<>();
        final TextField fieldEmail = new TextField("Email:");
        fieldEmail.focus();
        fieldEmail.setPlaceholder("name@carlook.de");
        fieldEmail.setRequiredIndicatorVisible(true);
        emailBinder.forField(fieldEmail)
                .withValidator(new EmailValidator("Biite geben Sie eine korrekte Emailadresse ein!"))
                .bind(UserDTO::getEmail, UserDTO::setEmail);
        fieldEmail.setId("email");

        //Anrede
        final Binder<UserDTO> anredeBinder = new Binder<>();
        final TextField fieldAnrede = new TextField("Anrede:");
        fieldAnrede.focus();
        fieldAnrede.setPlaceholder("Anrede");
        fieldAnrede.setRequiredIndicatorVisible(true);
        anredeBinder.forField(fieldAnrede)
                //.withValidator(new VornameValidator("Biite geben Sie eine korrekte Emailadresse ein!"))
                .bind(UserDTO::getAnrede, UserDTO::setAnrede);
        fieldAnrede.setId("anrede");


        //VornameValidator erstellen bzw. einen fuer Name und Vorname
        //Vorname
        final Binder<UserDTO> vornameBinder = new Binder<>();
        final TextField fieldVorname = new TextField("Vorname:");
        fieldVorname.focus();
        fieldVorname.setPlaceholder("Vorname");
        fieldVorname.setRequiredIndicatorVisible(true);
        vornameBinder.forField(fieldVorname)
                //.withValidator(new VornameValidator("Biite geben Sie eine korrekte Emailadresse ein!"))
                .bind(UserDTO::getVorname, UserDTO::setVorname);
        fieldVorname.setId("vorname");

        //Name
        final Binder<UserDTO> nachnameBinder = new Binder<>();
        final TextField fieldNachname = new TextField("Nachname:");
        fieldNachname.focus();
        fieldNachname.setPlaceholder("Nachname");
        fieldNachname.setRequiredIndicatorVisible(true);
        vornameBinder.forField(fieldNachname)
                //.withValidator(new VornameValidator("Biite geben Sie eine korrekte Emailadresse ein!"))
                .bind(UserDTO::getNachname, UserDTO::setNachname);
        fieldVorname.setId("nachname");

        //Passwort setzen und Counter Label darunter
        final Binder<UserDTO> password1Binder = new Binder<>();
        final PasswordField fieldPassword1 = new PasswordField("Passwort:");
        fieldPassword1.setPlaceholder("Passwort");
        fieldPassword1.setMaxLength(20);
        fieldPassword1.setRequiredIndicatorVisible(true);
        password1Binder.forField(fieldPassword1)
                .withValidator(str -> str.length() > 2, "Ihr Passwort muss mindestens 3 Zeichen enthalten!")
                .withValidator(str -> str.length() < 21, "Ihr Passwort darf nicht mehr als 20 Zeichen enthalten!")
                .asRequired("Bitte gegen Sie ein Passwort ein!")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        Label counter1 = new Label();
        counter1.setValue(fieldPassword1.getValue().length() + " of " + fieldPassword1.getMaxLength());
        fieldPassword1.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                counter1.setValue(fieldPassword1.getValue().length() + " of " + fieldPassword1.getMaxLength());

            }
        });
        fieldPassword1.setValueChangeMode(ValueChangeMode.EAGER);
        fieldPassword1.setId("passwort1");

        //Passwort wiederholen
        final Binder<UserDTO> password2Binder = new Binder<>();
        final PasswordField fieldPassword2 = new PasswordField("Passwort wiederholen:");
        fieldPassword2.setPlaceholder("Passwort");
        fieldPassword2.setMaxLength(20);
        fieldPassword2.setRequiredIndicatorVisible(true);
        password2Binder.forField(fieldPassword2)
                .asRequired("Bitte wiederholen Sie Ihr Passwort!")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        Label counter2 = new Label();
        counter2.setValue(fieldPassword2.getValue().length() + " of " + fieldPassword2.getMaxLength());
        fieldPassword2.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                counter2.setValue(fieldPassword2.getValue().length() + " of " + fieldPassword2.getMaxLength());

            }
        });
        fieldPassword1.setValueChangeMode(ValueChangeMode.EAGER);
        fieldPassword2.setId("passwort2");

        //Checkbox
        final Binder<UserDTO> checkboxBinder = new Binder<>();
        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>("Registrieren als:");
        radioButtonGroup.setItems("Endkunde", "Vertriebler");
        radioButtonGroup.setRequiredIndicatorVisible(true);
        radioButtonGroup.isSelected("Endkunde");
        checkboxBinder.forField(radioButtonGroup)
                .asRequired("Bitte wählen Sie!")
                .bind(UserDTO::getPassword, UserDTO::setPassword);

        //Register Button
        Button registerButton = new Button("Registrieren");
        registerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    emailBinder.validate();
                    password1Binder.validate();
                    password2Binder.validate();
                    checkboxBinder.validate();
                    String vorname = fieldVorname.getValue();
                    String nachname = fieldNachname.getValue();
                    String anrede = fieldAnrede.getValue();
                    String email = fieldEmail.getValue();
                    String password1 = fieldPassword1.getValue();
                    String password2 = fieldPassword2.getValue();
                    String regAs = radioButtonGroup.getValue();
                    RegistrationControlProxy.getInstance().checkValid( email, emailBinder.isValid(), password1, password2 , password1Binder.isValid(), password2Binder.isValid(), checkboxBinder.isValid() );
                    RegistrationControlProxy.getInstance().registerUser( email, password1, regAs ,vorname, nachname, anrede);
                } catch (NoEqualPasswordException e) {
                    Notification.show("Passwort-Fehler!", e.getReason(), Notification.Type.WARNING_MESSAGE);
                } catch (DatabaseException e) {
                    Notification.show("DB-Fehler!", e.getReason(), Notification.Type.ERROR_MESSAGE);
                } catch (EmailInUseException e) {
                    Notification.show("Email-Fehler!", e.getReason(), Notification.Type.ERROR_MESSAGE);
                } catch (EmptyFieldException e) {
                    Notification.show("Es sind ein oder mehrere Eingabefehler aufgetreten!", e.getReason(), Notification.Type.ERROR_MESSAGE);
                } catch (SQLException e) {
                    Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
                }
            }
        });

        //LoginButton
        Button loginButton = new Button("Zum Login");
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
            }
        });

        //Horizontal
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(registerButton);
        horizontalLayout.addComponent(loginButton);

        //Vertical Layout
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(fieldAnrede);
        verticalLayout.addComponent(fieldVorname);
        verticalLayout.addComponent(fieldNachname);
        verticalLayout.addComponent(fieldEmail);
        verticalLayout.addComponent(fieldPassword1);
        verticalLayout.addComponent(counter1);
        verticalLayout.addComponent(fieldPassword2);
        verticalLayout.addComponent(counter2);
        verticalLayout.addComponent(radioButtonGroup);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

        //Panel
        Panel panel = new Panel( "Bitte geben Sie ihre Daten ein:");
        panel.setContent(verticalLayout);
        panel.setSizeUndefined();

        //Einfügen
        this.addComponent(panel);
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

}