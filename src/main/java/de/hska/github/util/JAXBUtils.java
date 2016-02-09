package de.hska.github.util;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import de.hska.github.result.AggregationResult;
import de.hska.github.result.CodeSearchResult;
import de.hska.github.result.RepositorySearchResult;


public class JAXBUtils {

   private Logger                     _log          = Logger.getLogger(JAXBUtils.class);

   private Class<?>[]                 _rootElements = { RepositorySearchResult.class, CodeSearchResult.class, AggregationResult.class };
   private Map<Class<?>, JAXBContext> _contextMap;

   public JAXBUtils() {
      _contextMap = new HashMap<Class<?>, JAXBContext>();
      for ( Class<?> rootElement : _rootElements ) {
         try {
            _contextMap.put(rootElement, JAXBContext.newInstance(_rootElements));
         }
         catch ( JAXBException e ) {
            _log.error("cannot create context for element: " + rootElement);
         }
      }
   }

   public Marshaller createMarshaller( Class<?> clazz ) {
      try {
         Marshaller marshaller = _contextMap.get(clazz).createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());
         return marshaller;
      }
      catch ( JAXBException ex ) {
         return null;
      }
   }

   public Unmarshaller createUnmarshaller( Class<?> clazz ) {
      try {
         Unmarshaller unmarshaller = _contextMap.get(clazz).createUnmarshaller();
         return unmarshaller;
      }
      catch ( JAXBException ex ) {
         return null;
      }
   }

}
