package org.toyota.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public enum ERoles
{
    @JsonProperty("ADMIN") ROLE_ADMIN,
    @JsonProperty("OPERATOR") ROLE_OPERATOR,
    @JsonProperty("TEAMLEADER") ROLE_TEAMLEADER;

    private Set<ERoles> name;

    public Set<ERoles> getName()
    {
        return name;
    }

    public void setName(Set<ERoles> name)
    {
        this.name = name;
    }
}
