package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;

public class SearchData {

    public List<String> districtsFilter= Lists.newArrayList();;
    public List<String> priceRangeFilter= Lists.newArrayList();;
    public List<String> areaFilter= Lists.newArrayList();
    public List<String> houseTypeFilter= Lists.newArrayList();;
    public List<String> studyDistrictType= Lists.newArrayList();;

    public List<String> getDistrictsFilter() {
        return districtsFilter;
    }

    public void setDistrictsFilter(List<String> districtsFilter) {
        this.districtsFilter = districtsFilter;
    }

    public List<String> getPriceRangeFilter() {
        return priceRangeFilter;
    }

    public void setPriceRangeFilter(List<String> priceRangeFilter) {
        this.priceRangeFilter = priceRangeFilter;
    }

    public List<String> getAreaFilter() {
        return areaFilter;
    }

    public void setAreaFilter(List<String> areaFilter) {
        this.areaFilter = areaFilter;
    }

    public List<String> getHouseTypeFilter() {
        return houseTypeFilter;
    }

    public void setHouseTypeFilter(List<String> houseTypeFilter) {
        this.houseTypeFilter = houseTypeFilter;
    }

    public List<String> getStudyDistrictType() {
        return studyDistrictType;
    }

    public void setStudyDistrictType(List<String> studyDistrictType) {
        this.studyDistrictType = studyDistrictType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("districtsFilter", districtsFilter)
                .add("priceRangeFilter", priceRangeFilter)
                .add("areaFilter", areaFilter)
                .add("houseTypeFilter", houseTypeFilter)
                .add("studyDistrictType", studyDistrictType)
                .toString();
    }
}