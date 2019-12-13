package controlador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.JBIG2Image;

import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;

public class ManejadorPDF {
	private static ManejadorPDF unicaInstancia;
	
	private ManejadorPDF() {}
	
	public static ManejadorPDF getUnicaInstancia() {
		if(unicaInstancia == null)
			return unicaInstancia = new ManejadorPDF();
		return unicaInstancia;
	}
	
	public void printContactos(int movilUA) throws DocumentException, MalformedURLException, IOException {
		Usuario u = CatalogoUsuarios.getUnicaInstancia().getUsuario(movilUA);
		
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("ContactosUsuario_" + u.getMovil() + ".pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Paragraph Paragraph = new Paragraph("Contactos de : " + u.getNombre() + 
				"\n######################################################\n", font);
		if(u.getContactosIndividuales().size() != 0) {
		Paragraph ci = new Paragraph("Contactos Individuales\n");
		document.add(Paragraph);
		document.add(ci);
		
		Paragraph = new Paragraph("-----------------------------------------------------------------------");
		document.add(Paragraph);
		for(ContactoIndividual c : u.getContactosIndividuales()) {
			//Image usuario = Image.getInstance(50,50,u.getImagenUrl(), ));
			//document.add(usuario);
			Paragraph = new Paragraph("Nombre: " + c.getNombre() + "\n");
			ci = new Paragraph("Nº Telefono: " + c.getMovil()+ "\n");
			document.add(Paragraph);
			document.add(ci);
			Paragraph = new Paragraph("-----------------------------------------------------------------------\n");
			document.add(Paragraph);
		}
		
		Paragraph = new Paragraph("######################################################\n", font);}
		if(u.getGrupos().size() != 0) {
		Paragraph ci = new Paragraph("Grupos\n");
		Paragraph = new Paragraph("-----------------------------------------------------------------------");
		document.add(Paragraph);
		
		document.add(Paragraph);
		document.add(ci);
		for(Grupo g : u.getGrupos()) {
			Paragraph = new Paragraph("Nombre : " + g.getNombre()+ "\n");
			document.add(Paragraph);
			if(g.getContactos().size() != 0) {
				Paragraph = new Paragraph("**Contactos del Grupo: \n");
				document.add(Paragraph);
				for(ContactoIndividual c : g.getContactos()) {
					Paragraph = new Paragraph("**Nombre: " + c.getNombre()+ "\n");
					document.add(Paragraph);
					Paragraph = new Paragraph("**Nº Telfeono: " + c.getMovil()+ "\n");
				}
			}
			Paragraph = new Paragraph("-----------------------------------------------------------------------");
			document.add(Paragraph);
		}
		}
		Paragraph = new Paragraph("######################################################\n", font);
		document.add(Paragraph);
		document.close();
		
	}
}
