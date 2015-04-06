package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;

public abstract class AbstractAPIResponseFilter{

    public String filterType;
    public String filterNode;


    protected AbstractAPIResponseFilter(String filterType, String filterNode) {
        this.filterType = filterType;
        this.filterNode = filterNode;
    }

    public abstract void apply(TreeNode template, TreeNode actualResult);

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterNode() {
        return filterNode;
    }

    public void setFilterNode(String filterNode) {
        this.filterNode = filterNode;
    }


    public enum DefinedFilterType{

        REPEATABLE("REPEATABLE"),IGNORABLE("IGNORABLE");

        private String filterType;
        DefinedFilterType(String filterType) {
            this.filterType=filterType;
        }

        public String getFilterType() {
            return filterType;
        }

    }

}
