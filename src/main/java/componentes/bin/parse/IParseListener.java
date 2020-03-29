package componentes.bin.parse;
import java.util.EventListener;
import java.util.EventObject;

public interface IParseListener extends EventListener {

	public void enteradoParse(EventObject e);
}
