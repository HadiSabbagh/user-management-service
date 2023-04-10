package org.toyota.operationResult;

import org.toyota.mapper.MessageResponse;

import java.util.List;

public class DatabaseOpResult extends ExtendedOperationResult<MessageResponse>
{

    private MessageResponse Result;
    private List<String> ErrorMessages;

    public MessageResponse getResult()
    {
        return Result;
    }

    public void setResult(MessageResponse result)
    {
        Result = result;
    }

    @Override
    public List<String> getErrorMessages()
    {
        return ErrorMessages;
    }

    @Override
    public void setErrorMessages(List<String> errorMessages)
    {
        ErrorMessages = errorMessages;
    }

    public DatabaseOpResult(MessageResponse result, List<String> errorMessages)
    {
        Result = result;
        ErrorMessages = errorMessages;
    }
}
