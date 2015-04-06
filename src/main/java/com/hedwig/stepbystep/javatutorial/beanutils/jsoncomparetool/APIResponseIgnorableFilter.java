package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class APIResponseIgnorableFilter extends AbstractAPIResponseFilter {

    protected APIResponseIgnorableFilter(String filterNode) {
        super(DefinedFilterType.IGNORABLE.getFilterType(), filterNode);
    }

    @Override
    public void apply(TreeNode template, TreeNode actualResult) {

        if(template instanceof JsonNode){
          JsonElement node = ((JsonNode) template).getNode();
          removeElement(node,this.filterNode);
          ((JsonNode) template).setNode(node);

        }//todo xml handler

        if(actualResult instanceof JsonNode){
            JsonElement node = ((JsonNode) actualResult).getNode();
            removeElement(node,this.filterNode);
            ((JsonNode) actualResult).setNode(node);
        }//todo xml handler
    }

    private void removeElement(JsonElement element,String filterNode){
        if(element.isJsonObject()){

            if(((JsonObject)element).get(filterNode)!=null){
                ((JsonObject)element).remove(filterNode);
            }

        }/*if(element.isJsonArray()){
            for (JsonElement jsonElement : element.getAsJsonArray()) {
                removeElement(jsonElement,filterNode);
            }
        }*/
    }
}
