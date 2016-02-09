
package de.hska.github.result;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}position"/>
 *         &lt;element ref="{}repositoryName"/>
 *         &lt;element ref="{}occurrences" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "position",
    "repositoryName",
    "occurrences"
})
@XmlRootElement(name = "codeSearchResultEntry")
public class CodeSearchResultEntry {

    protected long position;
    @XmlElement(required = true)
    protected String repositoryName;
    @XmlElement(required = true)
    protected List<Occurrences> occurrences;

    /**
     * Gets the value of the position property.
     * 
     */
    public long getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     */
    public void setPosition(long value) {
        this.position = value;
    }

    /**
     * Gets the value of the repositoryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Sets the value of the repositoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryName(String value) {
        this.repositoryName = value;
    }

    /**
     * Gets the value of the occurrences property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occurrences property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccurrences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Occurrences }
     * 
     * 
     */
    public List<Occurrences> getOccurrences() {
        if (occurrences == null) {
            occurrences = new ArrayList<Occurrences>();
        }
        return this.occurrences;
    }

}
