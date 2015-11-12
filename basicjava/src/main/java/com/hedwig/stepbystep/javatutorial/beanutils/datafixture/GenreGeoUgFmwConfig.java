package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import java.util.HashMap;
import java.util.Map;

public class GenreGeoUgFmwConfig extends BaseEntity<GenreGeoUgFmwConfig> {

    private static Map<String,String> rowMap=new HashMap<String,String>();
    private static String primaryKeyName ="genreGeoUgFmwConfigId";
    private static String seqName="genre_geo_ug_fmw_config";
    private String genreGeoUgFmwConfigId;
    private String genreNodeId;
    private String geographyNodeId;
    private String userGroupId;
    private String fulfillmentMethodWindowId;
    private String includeUserGroupId;
    private String excludeUserGroupId;
    private String excludeInd;
    private String active;
    private String inactive;
    static{
        rowMap.put("genreGeoUgFmwConfigId","genre_geo_ug_fmw_config_id");
        rowMap.put("genreNodeId","GENRE_NODE_ID");
        rowMap.put("geographyNodeId","GEOGRAPHY_NODE_ID");
        rowMap.put("userGroupId","USER_GROUP_ID");
        rowMap.put("fulfillmentMethodWindowId","FULFILLMENT_METHOD_WINDOW_ID");
        rowMap.put("includeUserGroupId","INCLUDE_USER_GROUP_ID");
        rowMap.put("excludeUserGroupId","EXCLUDE_USER_GROUP_ID");
        rowMap.put("excludeInd","EXCLUDE_IND");
        rowMap.put("active","active");
        rowMap.put("inactive","inactive");
    }

    public GenreGeoUgFmwConfig(){
        super(primaryKeyName, seqName, rowMap);
    }

/*    protected GenreGeoUgFmwConfig(String primaryKeyName, String seqName, Map<String, String> rowMap) {
        super(primaryKeyName, seqName, rowMap);
    }*/


    public String getGenreGeoUgFmwConfigId() {
        return genreGeoUgFmwConfigId;
    }

    public void setGenreGeoUgFmwConfigId(String genreGeoUgFmwConfigId) {
        this.genreGeoUgFmwConfigId = genreGeoUgFmwConfigId;
    }

    public String getGenreNodeId() {
        return genreNodeId;
    }

    public void setGenreNodeId(String genreNodeId) {
        this.genreNodeId = genreNodeId;
    }

    public String getGeographyNodeId() {
        return geographyNodeId;
    }

    public void setGeographyNodeId(String geographyNodeId) {
        this.geographyNodeId = geographyNodeId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getFulfillmentMethodWindowId() {
        return fulfillmentMethodWindowId;
    }

    public void setFulfillmentMethodWindowId(String fulfillmentMethodWindowId) {
        this.fulfillmentMethodWindowId = fulfillmentMethodWindowId;
    }

    public String getIncludeUserGroupId() {
        return includeUserGroupId;
    }

    public void setIncludeUserGroupId(String includeUserGroupId) {
        this.includeUserGroupId = includeUserGroupId;
    }

    public String getExcludeUserGroupId() {
        return excludeUserGroupId;
    }

    public void setExcludeUserGroupId(String excludeUserGroupId) {
        this.excludeUserGroupId = excludeUserGroupId;
    }

    public String getExcludeInd() {
        return excludeInd;
    }

    public void setExcludeInd(String excludeInd) {
        this.excludeInd = excludeInd;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }


    @Override
    public GenreGeoUgFmwConfig findByPrimaryKey(String baseId) {
        return new GenreGeoUgFmwConfig();
    }

    @Override
    public String toString() {
        return "GenreGeoUgFmwConfig{" +
                "genreGeoUgFmwConfigId='" + genreGeoUgFmwConfigId + '\'' +
                ", genreNodeId='" + genreNodeId + '\'' +
                ", geographyNodeId='" + geographyNodeId + '\'' +
                ", userGroupId='" + userGroupId + '\'' +
                ", fulfillmentMethodWindowId='" + fulfillmentMethodWindowId + '\'' +
                ", includeUserGroupId='" + includeUserGroupId + '\'' +
                ", excludeUserGroupId='" + excludeUserGroupId + '\'' +
                ", excludeInd='" + excludeInd + '\'' +
                ", active='" + active + '\'' +
                ", inactive='" + inactive + '\'' +
                '}';
    }
}
