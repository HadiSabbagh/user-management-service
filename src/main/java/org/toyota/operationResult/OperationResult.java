package org.toyota.operationResult;

import jakarta.annotation.Nullable;

import java.util.List;

public interface OperationResult
{
    List<String> getErrorMessages();

    void setErrorMessages(@Nullable List<String> errorMessages);

    Object getResultObject();
}
