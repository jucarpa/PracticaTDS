package modelo;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private static Model model;

	private Model() {}
	public static Model getInstance() {
		if (model == null) {
	    model = new Model();
	    }
	    return model;
	}
	
	//Usuario
	private final StringProperty nombreU = new SimpleStringProperty();
    public StringProperty uNombreProperty() {
        return nombreU;
    }
    
    private final IntegerProperty movilU = new SimpleIntegerProperty();
    public IntegerProperty uMovilProperty() {
        return movilU;
    }
    
    private final StringProperty loginU = new SimpleStringProperty();
    public StringProperty uLoginProperty() {
        return loginU;
    }
    
    private final StringProperty contrasenyaU = new SimpleStringProperty();
    public StringProperty uContrasenyaProperty() {
        return contrasenyaU;
    }
    
    private final StringProperty imageU = new SimpleStringProperty();
    public StringProperty uImageProperty() {
        return imageU;
    }
    
    private final BooleanProperty premiumU = new SimpleBooleanProperty();
    public BooleanProperty uPremiumProperty() {
        return premiumU;
    }
    
    private final StringProperty emailU = new SimpleStringProperty();
    public StringProperty uEmailProperty() {
        return emailU;
    }
    
    private final ObjectProperty<LocalDate> fNU = new SimpleObjectProperty();
    public ObjectProperty<LocalDate> uFNproperty() {
        return fNU;
    }
    private final ObjectProperty<Estado> estadoU= new SimpleObjectProperty();
    public ObjectProperty<Estado> estadoUroperty() {
        return estadoU;
    }

    private final ListProperty<ContactoIndividual> contactosU = new SimpleListProperty();
	public ListProperty<ContactoIndividual> contactosUProperty() {
		return contactosU;
	}
	
	private final ListProperty<Grupo> gAU = new SimpleListProperty();
	public ListProperty<Grupo> gUAUProperty() {
		return gAU;
	}
	
	
}
	
