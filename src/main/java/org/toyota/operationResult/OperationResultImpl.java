package org.toyota.operationResult;

import jakarta.annotation.Nullable;

import java.util.List;

public class OperationResultImpl implements OperationResult
{

    @Nullable
    private List<String> ErrorMessages;

    private Object ResultObject;

    @Override
    public List<String> getErrorMessages()
    {
        return ErrorMessages;
    }

    @Override
    public void setErrorMessages(@Nullable List<String> errorMessages)
    {
        this.ErrorMessages = errorMessages;
    }

    @Override
    public Object getResultObject()
    {
        return ResultObject;
    }
}
