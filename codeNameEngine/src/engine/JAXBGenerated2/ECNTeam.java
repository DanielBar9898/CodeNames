//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.07.12 at 03:05:42 PM IDT 
//


package engine.JAXBGenerated2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="guessers" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="definers" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="cards-count" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ECN-Team")
public class ECNTeam {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "guessers", required = true)
    protected int guessers;
    @XmlAttribute(name = "definers", required = true)
    protected int definers;
    @XmlAttribute(name = "cards-count", required = true)
    protected int cardsCount;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the guessers property.
     * 
     */
    public int getGuessers() {
        return guessers;
    }

    /**
     * Sets the value of the guessers property.
     * 
     */
    public void setGuessers(int value) {
        this.guessers = value;
    }

    /**
     * Gets the value of the definers property.
     * 
     */
    public int getDefiners() {
        return definers;
    }

    /**
     * Sets the value of the definers property.
     * 
     */
    public void setDefiners(int value) {
        this.definers = value;
    }

    /**
     * Gets the value of the cardsCount property.
     * 
     */
    public int getCardsCount() {
        return cardsCount;
    }

    /**
     * Sets the value of the cardsCount property.
     * 
     */
    public void setCardsCount(int value) {
        this.cardsCount = value;
    }

}