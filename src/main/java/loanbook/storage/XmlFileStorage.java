package loanbook.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import loanbook.commons.exceptions.DataConversionException;
import loanbook.commons.util.XmlUtil;

/**
 * Stores loanbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given loanbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableLoanBook loanBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, loanBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns loan book in the file or an empty loan book
     */
    public static XmlSerializableLoanBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableLoanBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
