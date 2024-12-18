package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.model.Election;
import be.ifapme.election.model.Pays;
import be.ifapme.election.model.Personne;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.ConvactionService;
import be.ifapme.election.service.CountryService;
import be.ifapme.election.utils.CreateFile;
import com.itextpdf.text.*;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ConvocationServiceImpl implements ConvactionService {
    private final PersonRepository personRepository;
    private final CountryService countryService;
    private final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private final Font paragraphFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public ConvocationServiceImpl(PersonRepository personRepository, CountryService countryService) {
        this.personRepository = personRepository;
        this.countryService = countryService;
    }

    @Override
    public void createConvocation(Election election) throws DocumentException, IOException, NotFoundCodeCountryException {

        Pays pays = countryService.createPays(election.getCodePays());

        String urlImage = pays.getUrlImage();

        List<Personne> personnes = personRepository.findAllByAdresse_CodePaysIgnoreCase(election.getCodePays());


        for (Personne personne : personnes) {
            Document document = CreateFile.createPDF(election, personne);
            document.open();
            addEmptyLine(document, 4);
            Paragraph paragraph = new Paragraph();
            addPicture(document, urlImage, 75f, 75f,  paragraph , 450f , 750f );
            addPicture(document, "https://www.its.be/sites/default/files/solution/1/Namur.jpeg", 75f, 75f,  paragraph , 50f , 750f);
            document.add(paragraph);
            addInfoPerson(document, personne);
            addTitlePage(document, election);
            addObjectConvocation(document, "Nous vous invitons à venir voter");
            createBodyText(document, election.getNom());
            Paragraph paragraph2 = new Paragraph();
            addPicture(document, "https://t4.ftcdn.net/jpg/00/00/42/95/360_F_429547_YJTlwk2Ld5kYDAbtCUwFgzmatgUHEg.jpg", 100, 75, paragraph2 , 450f , 250f);
            document.add(paragraph2);
            document.close();
        }
    }

    private void addTitlePage(Document document, Election election) throws DocumentException {
        Paragraph title = new Paragraph("Convocation à l'élection " + election.getNom(), titleFont);
        title.setAlignment(Element.TITLE);
        Paragraph paragraph = new Paragraph();
        paragraph.add(title);
        document.add(paragraph);
        addEmptyLine(document, 1);
    }

    private void addInfoPerson(Document document, Personne personne) throws DocumentException {

        String personName = personne.getNom() + " " + personne.getPrenom();
        String personStreet = personne.getAdresse().getRue() + ", " + personne.getAdresse().getBoite();
        String personCity = personne.getAdresse().getCodePostal() + ", " + personne.getAdresse().getLocalite();
        String[] personInfo = new String[]{personName, personStreet, personCity};

        for (String info : personInfo) {
            Paragraph paragraph = new Paragraph();
            paragraph.add(info);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
        }
        addEmptyLine(document, 1);
    }

    private void addEmptyLine(Document document, int number) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
        document.add(paragraph);
    }

    private void addObjectConvocation(Document document, String object) throws DocumentException {
        Paragraph paragraph = new Paragraph("Objet : " + object, smallBold);
        paragraph.setIndentationLeft(50);
        document.add(paragraph);
        addEmptyLine(document, 2);
    }

    private void createBodyText(Document document, String electionName) throws DocumentException {
        String text = """
                Chères citoyennes, chers citoyens,
                                
                Nous avons le plaisir de vous inviter à participer aux élections """ + " " + electionName + " " + """
                qui se tiendront en Belgique. Votre voix est essentielle pour façonner l'avenir de notre nation, et chaque vote compte pour garantir que nos choix collectifs soient représentés.
                                
                Venez exercer votre droit démocratique et faire entendre votre opinion. Les bureaux de vote seront ouverts de 8h à 16h. N'oubliez pas de vous munir de votre pièce d'identité pour pouvoir voter.
                                
                Ensemble, construisons un avenir meilleur pour tous !
                                
                Bien cordialement,
                                
                Théo et Nicolas
                """;
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Paragraph paragraph = new Paragraph(doc.text() , paragraphFont);
        paragraph.setIndentationLeft(25);
        paragraph.setIndentationRight(25);

        document.add(paragraph);

    }

    private void addPicture(Document document, String picture, float sizeX, float sizeY, Paragraph paragraph , Float posX , Float posY) throws DocumentException, IOException {
        Image img = Image.getInstance(picture);
        img.scaleToFit(sizeX, sizeY);
        img.setAbsolutePosition(posX, posY);
        paragraph.add(img);
//        paragraph.setIndentationLeft(indentation);
    }
}
