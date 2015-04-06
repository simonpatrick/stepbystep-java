package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;


import com.google.gson.JsonArray;

public class APIResponseRepeatableFilter extends AbstractAPIResponseFilter {


    public APIResponseRepeatableFilter(String filterNode) {

        super(DefinedFilterType.REPEATABLE.getFilterType(),filterNode);

    }

    @Override
    public void apply(TreeNode template, TreeNode actualResult) {
        /**
         * template and actual result have same node but may not have same array size
         * 1. check if the same node,if not do nothing
         * 2. check if the same size, if yes, do nothing
         *    if not, fill the template array with the first array element to make
         *    the array size same
         */
        if(template.getName().equalsIgnoreCase(super.getFilterNode())||
                actualResult.getName().equalsIgnoreCase(super.getFilterNode())){
        //filter logic

            if(template instanceof JsonNode){
                if(template.getValueType().equalsIgnoreCase(TreeNode.ARRAY_TYPE)||
                        template.getValueType().equalsIgnoreCase(TreeNode.ARRAY_TYPE)){
                    JsonArray array = (JsonArray)((JsonNode)template).getNode();
                    int size = ((JsonArray)((JsonNode)actualResult).getNode()).size();
                    if(array.size()<size){
                        for (int i = array.size(); i <size ; i++) {
                           array.add(array.get(0));
                        }
                    }

                    ((JsonNode) template).setNode(array);

                }
            }//todo how about xml node

        }
    }




}
