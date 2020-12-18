package com.github.spring.expand.data.search.build;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.spring.expand.data.search.condition.Condition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author wx
 * @date 2020/12/11 17:28
 */
public class BuildFactory {

    private List<Condition> conditions;

    private static SpecificationBuild sb = new SpecificationBuild();

    public BuildFactory(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Specification toSpecification() {
        try {
            ObjectMapper om = new ObjectMapper();
            System.out.println(om.writeValueAsString(this.conditions));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sb.build(this.conditions);
    }


    public Pageable toPageable(){
        return null;
    }
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }


}
