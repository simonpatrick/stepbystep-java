package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;


/**
 * Created by kewu on 14-2-7.
 */
public class PoJoWriterForJson extends PoJoClassWriter {

    private final String KEYWORD_JACKSONIMPORT="import com.fasterxml.jackson.annotation.JsonProperty;\n";
    private final String KEYWORD_JACKSONANNOTATION="@JsonProperty(value=\"$fieldName\")\n";
    private boolean isUseJacksonAnnotation;

    public static PoJoWriterForJson newInstance(){
        return new PoJoWriterForJson(true);
    }

    public PoJoWriterForJson(boolean isUseJacksonAnnoatation){
        this.isUseJacksonAnnotation =isUseJacksonAnnoatation;
    }

    @Override
    public String generateAnnotation(String fieldName) {
        return isUseJacksonAnnotation? KEYWORD_JACKSONANNOTATION.replace("$fieldName",fieldName):null;
    }

    @Override
    public String generateImportStatement() {
        return isUseJacksonAnnotation?KEYWORD_JACKSONIMPORT:null;
    }

}
