package com.servicelive.orderfulfillment.domain;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.type.ProblemType;

public class ValidationHolder {
    List<ProblemType> errors = new ArrayList<ProblemType>();
    List<ProblemType> warnings = new ArrayList<ProblemType>();

    public void addError(ProblemType problemType) {
        if (!errors.contains(problemType)) {
            errors.add(problemType);
        }
    }

    public void addWarning(ProblemType problemType) {
        if (!warnings.contains(problemType)) {
            warnings.add(problemType);
        }
    }

    public List<ProblemType> getErrors() {
        return errors;
    }

    public List<ProblemType> getWarnings() {
        return warnings;
    }

    public boolean hasWarnings(){
        return warnings.size() > 0;
    }
}
