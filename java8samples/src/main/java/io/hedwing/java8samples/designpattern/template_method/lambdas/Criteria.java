package io.hedwing.java8samples.designpattern.template_method.lambdas;


import io.hedwing.java8samples.designpattern.template_method.ApplicationDenied;

// BEGIN Criteria
public interface Criteria {

    public void check() throws ApplicationDenied;

}
// END Criteria
