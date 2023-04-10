package org.toyota.mapper;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.toyota.operationResult.OperationResult;

import java.util.List;


public class ResponseMapper
{
    private final Logger logger = LogManager.getLogger(ResponseMapper.class);

    /**
     * @param operationResult has ErrorMessages and Result Object. It can only have one of those variables.
     * @return depending on the whether the operationResult has ErrorMessages or a Result Object, this method either returns bad or ok request respectively.
     */
    public ResponseEntity<?> MapResponse(OperationResult operationResult)
    {

        Gson gson = new Gson();
        String response = gson.toJson(operationResult);

        if (operationResult.getErrorMessages() != null)
        {
            logger.error("Operation result has error messages");
            return ResponseEntity.badRequest().body(response);
        } else
        {
            logger.info("Operation result successful");

            return ResponseEntity.ok().body(response);
        }

    }

    /**
     * @param entityList is a generic list used when the controller must return it in the body.
     * @return This method checks whether the list is empty or not and returns either noContent or ok respectively.
     */
    public ResponseEntity<?> ResponseList(List<?> entityList)
    {
        if(entityList.isEmpty())
        {
            logger.error("Empty list");
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.ok().body(entityList);
        }
    }
}
