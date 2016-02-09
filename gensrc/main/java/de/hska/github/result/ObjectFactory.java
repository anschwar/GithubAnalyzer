
package de.hska.github.result;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.hska.github.result package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Size_QNAME = new QName("", "size");
    private final static QName _Created_QNAME = new QName("", "created");
    private final static QName _HtmlURL_QNAME = new QName("", "htmlURL");
    private final static QName _TextMatch_QNAME = new QName("", "textMatch");
    private final static QName _FullName_QNAME = new QName("", "fullName");
    private final static QName _ClassName_QNAME = new QName("", "className");
    private final static QName _Position_QNAME = new QName("", "position");
    private final static QName _RepositoryName_QNAME = new QName("", "repositoryName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.hska.github.result
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Feature }
     * 
     */
    public Feature createFeature() {
        return new Feature();
    }

    /**
     * Create an instance of {@link Occurrences }
     * 
     */
    public Occurrences createOccurrences() {
        return new Occurrences();
    }

    /**
     * Create an instance of {@link Features }
     * 
     */
    public Features createFeatures() {
        return new Features();
    }

    /**
     * Create an instance of {@link TextMatches }
     * 
     */
    public TextMatches createTextMatches() {
        return new TextMatches();
    }

    /**
     * Create an instance of {@link RepositorySearchResult }
     * 
     */
    public RepositorySearchResult createRepositorySearchResult() {
        return new RepositorySearchResult();
    }

    /**
     * Create an instance of {@link CodeSearchResult }
     * 
     */
    public CodeSearchResult createCodeSearchResult() {
        return new CodeSearchResult();
    }

    /**
     * Create an instance of {@link AggregationResultEntry }
     * 
     */
    public AggregationResultEntry createAggregationResultEntry() {
        return new AggregationResultEntry();
    }

    /**
     * Create an instance of {@link CodeSearchResultEntry }
     * 
     */
    public CodeSearchResultEntry createCodeSearchResultEntry() {
        return new CodeSearchResultEntry();
    }

    /**
     * Create an instance of {@link RepositorySearchResultEntry }
     * 
     */
    public RepositorySearchResultEntry createRepositorySearchResultEntry() {
        return new RepositorySearchResultEntry();
    }

    /**
     * Create an instance of {@link AggregationResult }
     * 
     */
    public AggregationResult createAggregationResult() {
        return new AggregationResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "size")
    public JAXBElement<Integer> createSize(Integer value) {
        return new JAXBElement<Integer>(_Size_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "created")
    public JAXBElement<String> createCreated(String value) {
        return new JAXBElement<String>(_Created_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "htmlURL")
    public JAXBElement<String> createHtmlURL(String value) {
        return new JAXBElement<String>(_HtmlURL_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "textMatch")
    public JAXBElement<String> createTextMatch(String value) {
        return new JAXBElement<String>(_TextMatch_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fullName")
    public JAXBElement<String> createFullName(String value) {
        return new JAXBElement<String>(_FullName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "className")
    public JAXBElement<String> createClassName(String value) {
        return new JAXBElement<String>(_ClassName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "position")
    public JAXBElement<Long> createPosition(Long value) {
        return new JAXBElement<Long>(_Position_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "repositoryName")
    public JAXBElement<String> createRepositoryName(String value) {
        return new JAXBElement<String>(_RepositoryName_QNAME, String.class, null, value);
    }

}
