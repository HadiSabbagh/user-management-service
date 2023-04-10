package org.toyota.operationResult;

import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class ExtendedOperationResult<T> implements OperationResult
{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object ResultObject;


    @Override
    public Object getResultObject()
    {
        return ResultObject;
    }


}
