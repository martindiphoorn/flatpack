/*
 Copyright 2006 Paul Zepernick

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.apache.org/licenses/LICENSE-2.0 

 Unless required by applicable law or agreed to in writing, software distributed 
 under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 CONDITIONS OF ANY KIND, either express or implied. See the License for 
 the specific language governing permissions and limitations under the License.  
 */
package net.sf.pzfilereader.xml;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author zepernick
 * 
 * Resolves a local copy of the DTD instead of having to pull from SF
 */
public class ResolveLocalDTD implements EntityResolver {

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
     *      java.lang.String)
     */
    public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
        try {
            if (!systemId.toLowerCase().startsWith("http://")) {
                URL resource = getClass().getResource("pzfilereader.dtd");

                if (resource != null) {
                    return new InputSource(resource.openStream());
                } else {
                    throw new Exception("could not load dtd resource!!");
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        // must be pulling from the web, stick with default implementation
        return null;
    }
}
