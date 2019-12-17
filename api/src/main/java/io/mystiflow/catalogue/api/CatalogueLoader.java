package io.mystiflow.catalogue.api;

import java.io.IOException;

public interface CatalogueLoader {

    Catalogue load() throws IOException;

    void save() throws IOException;
}
