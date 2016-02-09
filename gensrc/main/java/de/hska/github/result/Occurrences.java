
package de.hska.github.result;

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
 *         &lt;element ref="{}className"/>
 *         &lt;element ref="{}textMatches"/>
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
    "className",
    "textMatches"
})
@XmlRootElement(name = "occurrences")
public class Occurrences {

    @XmlElement(required = true)
    protected String className;
    @XmlElement(required = true)
    protected TextMatches textMatches;

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * Gets the value of the textMatches property.
     * 
     * @return
     *     possible object is
     *     {@link TextMatches }
     *     
     */
    public TextMatches getTextMatches() {
        return textMatches;
    }

    /**
     * Sets the value of the textMatches property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextMatches }
     *     
     */
    public void setTextMatches(TextMatches value) {
        this.textMatches = value;
    }

}
